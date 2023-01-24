package com.injir.create_brass_coated.blocks.harvester;

import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.components.actors.AttachedActorBlock;
import com.simibubi.create.content.contraptions.components.actors.HarvesterTileEntity;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class BrassHarvesterBlock extends AttachedActorBlock implements ITE<BrassHarvesterTileEntity> {

	public BrassHarvesterBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}

	@Override
	public Class<BrassHarvesterTileEntity> getTileEntityClass() {
		return BrassHarvesterTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends BrassHarvesterTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_HARVESTER.get();
	}
	
}
