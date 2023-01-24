package com.injir.create_brass_coated.blocks.other;

import com.jozufozu.flywheel.api.MaterialManager;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.SingleRotatingInstance;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import net.minecraft.world.level.block.state.BlockState;

public class BrassShaftInstance extends SingleRotatingInstance {

	public BrassShaftInstance(MaterialManager dispatcher, KineticTileEntity tile) {
		super(dispatcher, tile);
	}

	@Override
	protected BlockState getRenderedBlockState() {
		return shaft();
	}

}
