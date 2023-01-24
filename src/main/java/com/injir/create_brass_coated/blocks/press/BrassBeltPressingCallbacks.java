package com.injir.create_brass_coated.blocks.press;

import com.simibubi.create.Create;
import com.injir.create_brass_coated.blocks.press.BrassPressingBehaviour.Mode;
import com.simibubi.create.content.contraptions.relays.belt.BeltHelper;
import com.simibubi.create.content.contraptions.relays.belt.transport.TransportedItemStack;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour.ProcessingResult;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.belt.TransportedItemStackHandlerBehaviour.TransportedResult;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static com.simibubi.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour.ProcessingResult.HOLD;
import static com.simibubi.create.foundation.tileEntity.behaviour.belt.BeltProcessingBehaviour.ProcessingResult.PASS;

public class BrassBeltPressingCallbacks {

	static ProcessingResult onItemReceived(TransportedItemStack transported,
		TransportedItemStackHandlerBehaviour handler, BrassPressingBehaviour behaviour) {
		if (behaviour.specifics.getKineticSpeed() == 0)
			return PASS;
		if (behaviour.running)
			return HOLD;
		if (!behaviour.specifics.tryProcessOnBelt(transported, null, true))
			return PASS;

		behaviour.start(Mode.BELT);
		return HOLD;
	}

	static ProcessingResult whenItemHeld(TransportedItemStack transported, TransportedItemStackHandlerBehaviour handler,
		BrassPressingBehaviour behaviour) {

		if (behaviour.specifics.getKineticSpeed() == 0)
			return PASS;
		if (!behaviour.running)
			return PASS;
		if (behaviour.runningTicks != BrassPressingBehaviour.CYCLE / 2)
			return HOLD;

		behaviour.particleItems.clear();
		ArrayList<ItemStack> results = new ArrayList<>();
		if (!behaviour.specifics.tryProcessOnBelt(transported, results, false))
			return PASS;

		boolean bulk = behaviour.specifics.canProcessInBulk() || transported.stack.getCount() == 1;

		List<TransportedItemStack> collect = results.stream()
			.map(stack -> {
				TransportedItemStack copy = transported.copy();
				boolean centered = BeltHelper.isItemUpright(stack);
				copy.stack = stack;
				copy.locked = true;
				copy.angle = centered ? 180 : Create.RANDOM.nextInt(360);
				return copy;
			})
			.collect(Collectors.toList());

		if (bulk) {
			if (collect.isEmpty())
				handler.handleProcessingOnItem(transported, TransportedResult.removeItem());
			else
				handler.handleProcessingOnItem(transported, TransportedResult.convertTo(collect));

		} else {
			TransportedItemStack left = transported.copy();
			left.stack.shrink(1);

			if (collect.isEmpty())
				handler.handleProcessingOnItem(transported, TransportedResult.convertTo(left));
			else
				handler.handleProcessingOnItem(transported, TransportedResult.convertToAndLeaveHeld(collect, left));
		}

		behaviour.tileEntity.sendData();
		return HOLD;
	}

}
