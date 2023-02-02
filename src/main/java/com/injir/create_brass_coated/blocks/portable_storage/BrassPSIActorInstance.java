package com.injir.create_brass_coated.blocks.portable_storage;

import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.core.virtual.VirtualRenderWorld;
import com.simibubi.create.content.contraptions.components.actors.PortableStorageInterfaceMovement;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.content.contraptions.components.structureMovement.render.ActorInstance;
import com.simibubi.create.foundation.utility.AnimationTickHolder;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;

public class BrassPSIActorInstance extends ActorInstance {

	private final BrassPIInstance instance;

	public BrassPSIActorInstance(MaterialManager materialManager, VirtualRenderWorld world, MovementContext context) {
		super(materialManager, world, context);

		instance = new BrassPIInstance(materialManager, context.state, context.localPos);

		instance.init(false);
		instance.middle.setBlockLight(localBlockLight());
		instance.top.setBlockLight(localBlockLight());
	}

	@Override
	public void beginFrame() {
		LerpedFloat lf = PortableStorageInterfaceMovement.getAnimation(context);
		instance.tick(lf.settled());
		instance.beginFrame(lf.getValue(AnimationTickHolder.getPartialTicks()));
	}

}
