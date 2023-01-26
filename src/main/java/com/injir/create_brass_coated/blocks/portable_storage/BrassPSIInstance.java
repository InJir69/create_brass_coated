package com.injir.create_brass_coated.blocks.portable_storage;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.api.instance.TickableInstance;
import com.jozufozu.flywheel.backend.instancing.blockentity.BlockEntityInstance;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

public class BrassPSIInstance extends BlockEntityInstance<BrassPortableStorageInterfaceTileEntity> implements DynamicInstance, TickableInstance {

	private final BrassPIInstance instance;

	public BrassPSIInstance(MaterialManager materialManager, BrassPortableStorageInterfaceTileEntity tile) {
		super(materialManager, tile);

		instance = new BrassPIInstance(materialManager, blockState, getInstancePosition());
	}

	@Override
	public void init() {
		instance.init(isLit());
	}

	@Override
	public void tick() {
		instance.tick(isLit());
	}

	@Override
	public void beginFrame() {
		instance.beginFrame(blockEntity.getExtensionDistance(AnimationTickHolder.getPartialTicks()));
	}

	@Override
	public void updateLight() {
		relight(pos, instance.middle, instance.top);
	}

	@Override
	public void remove() {
		instance.remove();
	}

	private boolean isLit() {
		return blockEntity.isConnected();
	}

}
