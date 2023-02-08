package com.injir.create_brass_coated.blocks.girder.copper;

import com.google.common.base.Predicates;
import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.simibubi.create.content.curiosities.tools.ExtendoGripItem;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.utility.placement.IPlacementHelper;
import com.simibubi.create.foundation.utility.placement.PlacementOffset;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeMod;

import java.util.List;
import java.util.function.Predicate;

public class CopperGirderPlacementHelper implements IPlacementHelper {

	@Override
	public Predicate<ItemStack> getItemPredicate() {
		return BrassBlocks.COPPER_GIRDER::isIn;
	}

	@Override
	public Predicate<BlockState> getStatePredicate() {
		return Predicates.or(BrassBlocks.COPPER_GIRDER::has, BrassBlocks.COPPER_GIRDER_ENCASED_SHAFT::has);
	}

	private boolean canExtendToward(BlockState state, Direction side) {
		Axis axis = side.getAxis();
		if (state.getBlock() instanceof CopperGirderBlock) {
			boolean x = state.getValue(CopperGirderBlock.X);
			boolean z = state.getValue(CopperGirderBlock.Z);
			if (!x && !z)
				return axis == Axis.Y;
			if (x && z)
				return true;
			return axis == (x ? Axis.X : Axis.Z);
		}

		if (state.getBlock() instanceof CopperGirderEncasedShaftBlock)
			return axis != Axis.Y && axis != state.getValue(CopperGirderEncasedShaftBlock.HORIZONTAL_AXIS);

		return false;
	}

	private int attachedPoles(Level world, BlockPos pos, Direction direction) {
		BlockPos checkPos = pos.relative(direction);
		BlockState state = world.getBlockState(checkPos);
		int count = 0;
		while (canExtendToward(state, direction)) {
			count++;
			checkPos = checkPos.relative(direction);
			state = world.getBlockState(checkPos);
		}
		return count;
	}

	private BlockState withAxis(BlockState state, Axis axis) {
		if (state.getBlock() instanceof CopperGirderBlock)
			return state.setValue(CopperGirderBlock.X, axis == Axis.X)
				.setValue(CopperGirderBlock.Z, axis == Axis.Z)
				.setValue(CopperGirderBlock.AXIS, axis);
		if (state.getBlock() instanceof CopperGirderEncasedShaftBlock && axis.isHorizontal())
			return state.setValue(CopperGirderEncasedShaftBlock.HORIZONTAL_AXIS, axis == Axis.X ? Axis.Z : Axis.X);
		return state;
	}

	@Override
	public PlacementOffset getOffset(Player player, Level world, BlockState state, BlockPos pos, BlockHitResult ray) {
		List<Direction> directions =
			IPlacementHelper.orderedByDistance(pos, ray.getLocation(), dir -> canExtendToward(state, dir));
		for (Direction dir : directions) {
			int range = AllConfigs.SERVER.curiosities.placementAssistRange.get();
			if (player != null) {
				AttributeInstance reach = player.getAttribute(ForgeMod.REACH_DISTANCE.get());
				if (reach != null && reach.hasModifier(ExtendoGripItem.singleRangeAttributeModifier))
					range += 4;
			}
			int poles = attachedPoles(world, pos, dir);
			if (poles >= range)
				continue;

			BlockPos newPos = pos.relative(dir, poles + 1);
			BlockState newState = world.getBlockState(newPos);

			if (!newState.getMaterial()
				.isReplaceable())
				continue;

			return PlacementOffset.success(newPos,
				bState -> Block.updateFromNeighbourShapes(withAxis(bState, dir.getAxis()), world, newPos));
		}

		return PlacementOffset.fail();
	}

}
