package com.injir.create_brass_coated.blocks.girder.brass;

import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.utility.Iterate;
import com.tterrag.registrate.providers.DataGenContext;
import com.tterrag.registrate.providers.RegistrateBlockstateProvider;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;

public class BrassGirderBlockStateGenerator {

	public static void blockStateWithShaft(DataGenContext<Block, BrassGirderEncasedShaftBlock> c,
		RegistrateBlockstateProvider p) {
		MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p))
			.rotationY(0)
			.addModel()
			.condition(BrassGirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.Z)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p))
			.rotationY(90)
			.addModel()
			.condition(BrassGirderEncasedShaftBlock.HORIZONTAL_AXIS, Axis.X)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "top"))
			.addModel()
			.condition(BrassGirderEncasedShaftBlock.TOP, true)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "bottom"))
			.addModel()
			.condition(BrassGirderEncasedShaftBlock.BOTTOM, true)
			.end();

	}

	public static void blockState(DataGenContext<Block, BrassGirderBlock> c, RegistrateBlockstateProvider p) {
		MultiPartBlockStateBuilder builder = p.getMultipartBuilder(c.get());

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "pole"))
			.addModel()
			.condition(BrassGirderBlock.X, false)
			.condition(BrassGirderBlock.Z, false)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "x"))
			.addModel()
			.condition(BrassGirderBlock.X, true)
			.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "z"))
			.addModel()
			.condition(BrassGirderBlock.Z, true)
			.end();

		for (boolean x : Iterate.trueAndFalse)
			builder.part()
				.modelFile(AssetLookup.partialBaseModel(c, p, "top"))
				.addModel()
				.condition(BrassGirderBlock.TOP, true)
				.condition(BrassGirderBlock.X, x)
				.condition(BrassGirderBlock.Z, !x)
				.end()
				.part()
				.modelFile(AssetLookup.partialBaseModel(c, p, "bottom"))
				.addModel()
				.condition(BrassGirderBlock.BOTTOM, true)
				.condition(BrassGirderBlock.X, x)
				.condition(BrassGirderBlock.Z, !x)
				.end();

		builder.part()
			.modelFile(AssetLookup.partialBaseModel(c, p, "cross"))
			.addModel()
			.condition(BrassGirderBlock.X, true)
			.condition(BrassGirderBlock.Z, true)
			.end();

	}

}
