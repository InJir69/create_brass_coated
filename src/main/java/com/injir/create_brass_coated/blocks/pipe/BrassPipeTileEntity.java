package com.injir.create_brass_coated.blocks.pipe;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.simibubi.create.content.contraptions.components.structureMovement.ITransformableTE;
import com.simibubi.create.content.contraptions.components.structureMovement.StructureTransform;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedTileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BrassPipeTileEntity extends SmartTileEntity implements ITransformableTE {

	public BrassPipeTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
		behaviours.add(new StandardPipeFluidTransportBehaviour(this));
		behaviours.add(new BracketedTileEntityBehaviour(this, this::canHaveBracket));
		registerAwardables(behaviours, BrassFluidPropagator.getSharedTriggers());
	}

	@Override
	public void transform(StructureTransform transform) {
		BracketedTileEntityBehaviour bracketBehaviour = getBehaviour(BracketedTileEntityBehaviour.TYPE);
		if (bracketBehaviour != null) {
			bracketBehaviour.transformBracket(transform);
		}
	}

	private boolean canHaveBracket(BlockState state) {
		return !(state.getBlock() instanceof EncasedBrassPipeBlock);
	}

	class StandardPipeFluidTransportBehaviour extends BrassFluidTransportBehaviour {

		public StandardPipeFluidTransportBehaviour(SmartTileEntity te) {
			super(te);
		}

		@Override
		public boolean canHaveFlowToward(BlockState state, Direction direction) {
			return (BrassPipeBlock.isPipe(state) || state.getBlock() instanceof EncasedBrassPipeBlock)
				&& state.getValue(BrassPipeBlock.PROPERTY_BY_DIRECTION.get(direction));
		}

		@Override
		public AttachmentTypes getRenderedRimAttachment(BlockAndTintGetter world, BlockPos pos, BlockState state,
			Direction direction) {
			AttachmentTypes attachment = super.getRenderedRimAttachment(world, pos, state, direction);

			BlockPos offsetPos = pos.relative(direction);
			BlockState otherState = world.getBlockState(offsetPos);

			if (attachment == AttachmentTypes.RIM && !BrassPipeBlock.isPipe(otherState)
				&& !BrassBlocks.BRASS_MECHANICAL_PUMP.has(otherState) && !BrassBlocks.ENCASED_BRASS_PIPE.has(otherState)) {
				BrassFluidTransportBehaviour pipeBehaviour =
					TileEntityBehaviour.get(world, offsetPos, BrassFluidTransportBehaviour.TYPE);
				if (pipeBehaviour != null)
					if (pipeBehaviour.canHaveFlowToward(otherState, direction.getOpposite()))
						return AttachmentTypes.CONNECTION;
			}

			if (attachment == AttachmentTypes.RIM && !BrassPipeBlock.shouldDrawRim(world, pos, state, direction))
				return AttachmentTypes.CONNECTION;
			if (attachment == AttachmentTypes.NONE && state.getValue(BrassPipeBlock.PROPERTY_BY_DIRECTION.get(direction)))
				return AttachmentTypes.CONNECTION;
			return attachment;
		}

	}

}
