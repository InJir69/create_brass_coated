package com.injir.create_brass_coated.blocks.chute;

import com.injir.create_brass_coated.blocks.chute.BrassChuteBlock.BrassShape;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.SpecialBlockStateGen;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.ModelFile;

public class BrassChuteGenerator extends SpecialBlockStateGen {

	@Override
	protected int getXRotation(BlockState state) {
		return 0;
	}

	@Override
	protected int getYRotation(BlockState state) {
		return horizontalAngle(state.getValue(BrassChuteBlock.FACING));
	}

	@Override
	public <T extends Block> ModelFile getModel(DataGenContext<Block, T> ctx, RegistrateBlockstateProvider prov,
		BlockState state) {
		boolean horizontal = state.getValue(BrassChuteBlock.FACING) != Direction.DOWN;
		BrassShape shape = state.getValue(BrassChuteBlock.SHAPE);

		if (!horizontal)
			return shape == BrassShape.NORMAL ? AssetLookup.partialBaseModel(ctx, prov)
				: shape == BrassShape.INTERSECTION ? AssetLookup.partialBaseModel(ctx, prov, "intersection")
					: AssetLookup.partialBaseModel(ctx, prov, "windowed");
		return shape == BrassShape.INTERSECTION ? AssetLookup.partialBaseModel(ctx, prov, "diagonal", "intersection")
			: AssetLookup.partialBaseModel(ctx, prov, "diagonal");
	}

}
