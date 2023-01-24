package com.injir.create_brass_coated.blocks.press;

import com.injir.create_brass_coated.blocks.BrassPartials;
import com.jozufozu.flywheel.api.MaterialManager;
import com.jozufozu.flywheel.api.instance.DynamicInstance;
import com.jozufozu.flywheel.core.Materials;
import com.jozufozu.flywheel.core.materials.oriented.OrientedData;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import com.simibubi.create.AllBlockPartials;
import com.simibubi.create.content.contraptions.components.press.MechanicalPressBlock;
import com.simibubi.create.content.contraptions.components.press.MechanicalPressTileEntity;
import com.simibubi.create.content.contraptions.components.press.PressingBehaviour;
import com.simibubi.create.content.contraptions.relays.encased.ShaftInstance;
import com.simibubi.create.foundation.utility.AngleHelper;
import com.simibubi.create.foundation.utility.AnimationTickHolder;

public class BrassPressInstance extends ShaftInstance implements DynamicInstance {

	private final OrientedData pressHead;
	private final BrassMechanicalPressTileEntity press;

	public BrassPressInstance(MaterialManager dispatcher, BrassMechanicalPressTileEntity tile) {
		super(dispatcher, tile);
		press = tile;

		pressHead = dispatcher.defaultSolid()
				.material(Materials.ORIENTED)
				.getModel(BrassPartials.BRASS_MECHANICAL_PRESS_HEAD, blockState)
				.createInstance();

		Quaternion q = Vector3f.YP
			.rotationDegrees(AngleHelper.horizontalAngle(blockState.getValue(BrassMechanicalPressBlock.HORIZONTAL_FACING)));

		pressHead.setRotation(q);

		transformModels();
	}

	@Override
	public void beginFrame() {
		transformModels();
	}

	private void transformModels() {
		float renderedHeadOffset = getRenderedHeadOffset(press);

		pressHead.setPosition(getInstancePosition())
			.nudge(0, -renderedHeadOffset, 0);
	}

	private float getRenderedHeadOffset(BrassMechanicalPressTileEntity press) {
		BrassPressingBehaviour pressingBehaviour = press.getPressingBehaviour();
		return pressingBehaviour.getRenderedHeadOffset(AnimationTickHolder.getPartialTicks())
			* pressingBehaviour.mode.headOffset;
	}

	@Override
	public void updateLight() {
		super.updateLight();

		relight(pos, pressHead);
	}

	@Override
	public void remove() {
		super.remove();
		pressHead.delete();
	}
}
