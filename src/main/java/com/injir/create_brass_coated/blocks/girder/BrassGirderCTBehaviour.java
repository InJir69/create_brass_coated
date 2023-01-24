package com.injir.create_brass_coated.blocks.girder;

import com.injir.create_brass_coated.BrassSpriteShifts;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.ConnectedTextureBehaviour;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public class BrassGirderCTBehaviour extends ConnectedTextureBehaviour.Base {

	@Override
	public CTSpriteShiftEntry getShift(BlockState state, Direction direction, @Nullable TextureAtlasSprite sprite) {
		if (!state.hasProperty(BrassGirderBlock.X))
			return null;
		return !state.getValue(BrassGirderBlock.X) && !state.getValue(BrassGirderBlock.Z) && direction.getAxis() != Axis.Y
			? BrassSpriteShifts.BRASS_GIRDER_POLE
			: null;
	}


	@Override
	public boolean connectsTo(BlockState state, BlockState other, BlockAndTintGetter reader, BlockPos pos,
		BlockPos otherPos, Direction face) {
		if (other.getBlock() != state.getBlock())
			return false;
		return !other.getValue(BrassGirderBlock.X) && !other.getValue(BrassGirderBlock.Z);
	}

}
