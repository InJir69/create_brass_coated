package com.injir.create_brass_coated.blocks.saw;

import com.simibubi.create.content.contraptions.components.saw.SawBlock;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class BrassSawGenerator extends SpecialBlockStateGen {

	@Override
	protected int getXRotation(BlockState state) {
		return state.getValue(BrassSawBlock.FACING) == Direction.DOWN ? 180 : 0;
	}

	@Override
	protected int getYRotation(BlockState state) {
		Direction facing = state.getValue(BrassSawBlock.FACING);
		boolean axisAlongFirst = state.getValue(BrassSawBlock.AXIS_ALONG_FIRST_COORDINATE);
		if (facing.getAxis()
			.isVertical())
			return axisAlongFirst ? 90 : 0;
		return horizontalAngle(facing);
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
		BlockState state) {
		String path = "block/" + ctx.getName() + "/";
		String orientation = state.getValue(BrassSawBlock.FACING)
			.getAxis()
			.isVertical() ? "vertical" : "horizontal";

		return prov.models()
			.getExistingFile(prov.modLoc(path + orientation));
	}

}
