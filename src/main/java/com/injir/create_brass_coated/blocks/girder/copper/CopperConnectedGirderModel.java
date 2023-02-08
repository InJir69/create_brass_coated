package com.injir.create_brass_coated.blocks.girder.copper;

import com.injir.create_brass_coated.blocks.BrassPartials;
import com.simibubi.create.foundation.block.connected.CTModel;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import net.minecraftforge.client.model.data.ModelData.Builder;
import net.minecraftforge.client.model.data.ModelProperty;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CopperConnectedGirderModel extends CTModel {

	protected static ModelProperty<ConnectionData> COPPER_CONNECTION_PROPERTY = new ModelProperty<>();

	public CopperConnectedGirderModel(BakedModel originalModel) {
		super(originalModel, new CopperGirderCTBehaviour());
	}

	@Override
	protected Builder gatherModelData(Builder builder, BlockAndTintGetter world, BlockPos pos, BlockState state) {
		ConnectionData connectionData = new ConnectionData();
		for (Direction d : Iterate.horizontalDirections)
			connectionData.setConnected(d, CopperGirderBlock.isConnected(world, pos, state, d));
		return super.gatherModelData(builder, world, pos, state).with(COPPER_CONNECTION_PROPERTY, connectionData);
	}

	@Override
	public List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand, ModelData extraData, RenderType renderType) {
		List<BakedQuad> superQuads = super.getQuads(state, side, rand, extraData, renderType);
		if (side != null || !extraData.has(COPPER_CONNECTION_PROPERTY))
			return superQuads;
		List<BakedQuad> quads = new ArrayList<>(superQuads);
		ConnectionData data = extraData.get(COPPER_CONNECTION_PROPERTY);
		for (Direction d : Iterate.horizontalDirections)
			if (data.isConnected(d))
				quads.addAll(BrassPartials.COPPER_GIRDER_BRACKETS.get(d)
					.get()
					.getQuads(state, side, rand, extraData, renderType));
		return quads;
	}

	private class ConnectionData {
		boolean[] connectedFaces;

		public ConnectionData() {
			connectedFaces = new boolean[4];
			Arrays.fill(connectedFaces, false);
		}

		void setConnected(Direction face, boolean connected) {
			connectedFaces[face.get2DDataValue()] = connected;
		}

		boolean isConnected(Direction face) {
			return connectedFaces[face.get2DDataValue()];
		}
	}

}
