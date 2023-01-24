package com.injir.create_brass_coated.blocks.girder;

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

public class BrassGirderPlacementHelper implements IPlacementHelper {

	@Override
	public Predicate<ItemStack> getItemPredicate() {
		return BrassBlocks.BRASS_GIRDER::isIn;
	}

	@Override
	public Predicate<BlockState> getStatePredicate() {
		return Predicates.or(BrassBlocks.BRASS_GIRDER::has, BrassBlocks.BRASS_GIRDER_ENCASED_SHAFT::has);
	}

	private boolean canExtendToward(BlockState state, Direction side) {
		Axis axis = side.getAxis();
		if (state.getBlock() instanceof BrassGirderBlock) {
			boolean x = state.getValue(BrassGirderBlock.X);
			boolean z = state.getValue(BrassGirderBlock.Z);
			if (!x && !z)
				return axis == Axis.Y;
			if (x && z)
				return true;
			return axis == (x ? Axis.X : Axis.Z);
		}

		if (state.getBlock() instanceof BrassGirderEncasedShaftBlock)
			return axis != Axis.Y && axis != state.getValue(BrassGirderEncasedShaftBlock.HORIZONTAL_AXIS);

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
		if (state.getBlock() instanceof BrassGirderBlock)
			return state.setValue(BrassGirderBlock.X, axis == Axis.X)
				.setValue(BrassGirderBlock.Z, axis == Axis.Z)
				.setValue(BrassGirderBlock.AXIS, axis);
		if (state.getBlock() instanceof BrassGirderEncasedShaftBlock && axis.isHorizontal())
			return state.setValue(BrassGirderEncasedShaftBlock.HORIZONTAL_AXIS, axis == Axis.X ? Axis.Z : Axis.X);
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
