package com.injir.create_brass_coated.blocks.drill;

import com.injir.create_brass_coated.blocks.BrassBlockBreakingKineticTileEntity;
import com.simibubi.create.content.contraptions.components.actors.BlockBreakingKineticTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BrassDrillTileEntity extends BrassBlockBreakingKineticTileEntity {

	public BrassDrillTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected BlockPos getBreakingPos() {
		return getBlockPos().relative(getBlockState().getValue(BrassDrillBlock.FACING));
	}

}
