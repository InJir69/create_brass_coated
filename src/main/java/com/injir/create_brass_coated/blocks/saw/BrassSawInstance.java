package com.injir.create_brass_coated.blocks.saw;

import com.jozufozu.flywheel.api.Instancer;
import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.base.flwdata.RotatingData;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;

public class BrassSawInstance extends ShaftInstance {

	public BrassSawInstance(MaterialManager modelManager, KineticTileEntity tile) {
		super(modelManager, tile);
	}

	@Override
	protected Instancer<RotatingData> getModel() {
		if (blockState.getValue(BlockStateProperties.FACING)
			.getAxis()
			.isHorizontal()) {
			BlockState referenceState = blockState.rotate(blockEntity.getLevel(), blockEntity.getBlockPos(), Rotation.CLOCKWISE_180);
			Direction facing = referenceState.getValue(BlockStateProperties.FACING);
			return getRotatingMaterial().getModel(AllBlockPartials.SHAFT_HALF, referenceState, facing);
		} else {
			return getRotatingMaterial().getModel(shaft());
		}
	}
}
