package com.injir.create_brass_coated.blocks.harvester;

import com.simibubi.create.foundation.tileEntity.CachedRenderBBTileEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;

public class BrassHarvesterTileEntity extends CachedRenderBBTileEntity {

	// For simulations such as Ponder
	private float manuallyAnimatedSpeed;

	public BrassHarvesterTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected AABB createRenderBoundingBox() {
		return new AABB(worldPosition);
	}

	public float getAnimatedSpeed() {
		return manuallyAnimatedSpeed;
	}

	public void setAnimatedSpeed(float speed) {
		manuallyAnimatedSpeed = speed;
	}

}
