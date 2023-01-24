package com.injir.create_brass_coated.blocks.saw;

import com.jozufozu.flywheel.util.transform.TransformStack;
import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.contraptions.components.saw.SawBlock;
import com.simibubi.create.foundation.tileEntity.behaviour.ValueBoxTransform;
import com.simibubi.create.foundation.utility.VecHelper;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

public class BrassSawFilterSlot extends ValueBoxTransform {

	@Override
	protected Vec3 getLocalOffset(BlockState state) {
		if (state.getValue(BrassSawBlock.FACING) != Direction.UP)
			return null;
		Vec3 x = VecHelper.voxelSpace(8f, 12.5f, 12.25f);
		Vec3 z = VecHelper.voxelSpace(12.25f, 12.5f, 8f);
		return state.getValue(BrassSawBlock.AXIS_ALONG_FIRST_COORDINATE) ? z : x;
	}

	@Override
	protected void rotate(BlockState state, PoseStack ms) {
		int yRot = state.getValue(BrassSawBlock.AXIS_ALONG_FIRST_COORDINATE) ? 270 : 180;
		TransformStack.cast(ms)
			.rotateY(yRot)
			.rotateX(90);
	}

}
