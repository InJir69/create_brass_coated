package com.injir.create_brass_coated.blocks.girder.copper;

import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.utility.Iterate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

public class CopperGirderBlockStateGenerator {

	public static void blockStateWithShaft(DataGenContext<Block, CopperGirderEncasedShaftBlock> c,
		RegistrateBlockstateProvider p) {
		MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p))
			.rotationY(0)
			.addModel()
			.condition(CopperGirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.Z)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p))
			.rotationY(90)
			.addModel()
			.condition(CopperGirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.X)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "top"))
			.addModel()
			.condition(CopperGirderEncasedShaftBlock.TOP, true)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "bottom"))
			.addModel()
			.condition(CopperGirderEncasedShaftBlock.BOTTOM, true)
			.end();

	}

	public static void blockState(DataGenContext<Block, CopperGirderBlock> c, RegistrateBlockstateProvider p) {
		MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "pole"))
			.addModel()
			.condition(CopperGirderBlock.X, false)
			.condition(CopperGirderBlock.Z, false)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "x"))
			.addModel()
			.condition(CopperGirderBlock.X, true)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "z"))
			.addModel()
			.condition(CopperGirderBlock.Z, true)
			.end();

		for (boolean x : Iterate.trueAndFalse)
			builder.part()
				.modelFile(AssetLookup.partialBaseModel(c, p, "top"))
				.addModel()
				.condition(CopperGirderBlock.TOP, true)
				.condition(CopperGirderBlock.X, x)
				.condition(CopperGirderBlock.Z, !x)
				.end()
				.part()
				.modelFile(AssetLookup.partialBaseModel(c, p, "bottom"))
				.addModel()
				.condition(CopperGirderBlock.BOTTOM, true)
				.condition(CopperGirderBlock.X, x)
				.condition(CopperGirderBlock.Z, !x)
				.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "cross"))
			.addModel()
			.condition(CopperGirderBlock.X, true)
			.condition(CopperGirderBlock.Z, true)
			.end();

	}

}
