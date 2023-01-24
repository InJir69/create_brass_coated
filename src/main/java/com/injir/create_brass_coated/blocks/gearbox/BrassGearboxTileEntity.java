package com.injir.create_brass_coated.blocks.gearbox;

import com.simibubi.create.content.contraptions.relays.encased.DirectionalShaftHalvesTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class BrassGearboxTileEntity extends DirectionalShaftHalvesTileEntity {

	public BrassGearboxTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}
	
	@Override
	protected boolean isNoisy() {
		return false;
	}
	
}
