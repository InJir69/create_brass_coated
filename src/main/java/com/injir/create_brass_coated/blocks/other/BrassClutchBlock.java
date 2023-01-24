package com.injir.create_brass_coated.blocks.other;

import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.content.contraptions.relays.encased.GearshiftBlock;
import com.simibubi.create.content.contraptions.relays.encased.SplitShaftTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BrassClutchBlock extends GearshiftBlock {

	public BrassClutchBlock(Properties properties) {
		super(properties);
	}

	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos,
			boolean isMoving) {
		if (worldIn.isClientSide)
			return;

		boolean previouslyPowered = state.getValue(POWERED);
		if (previouslyPowered != worldIn.hasNeighborSignal(pos)) {
			worldIn.setBlock(pos, state.cycle(POWERED), 2 | 16);
			detachKinetics(worldIn, pos, previouslyPowered);
		}
	}
	
	@Override
	public BlockEntityType<? extends SplitShaftTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_CLUTCH.get();
	}

}
