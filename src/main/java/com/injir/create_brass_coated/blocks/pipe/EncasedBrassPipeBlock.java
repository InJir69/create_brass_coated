package com.injir.create_brass_coated.blocks.pipe;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.content.schematics.ISpecialBlockItemRequirement;
import com.simibubi.create.content.schematics.ItemRequirement;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.ticks.TickPriority;

import java.util.Map;
import java.util.Random;

import static net.minecraft.world.level.block.state.properties.BlockStateProperties.*;

public class EncasedBrassPipeBlock extends Block implements IWrenchable, ISpecialBlockItemRequirement, ITE<BrassPipeTileEntity> {

	public static final Map<Direction, BooleanProperty> FACING_TO_PROPERTY_MAP = PipeBlock.PROPERTY_BY_DIRECTION;

	public EncasedBrassPipeBlock(Properties p_i48339_1_) {
		super(p_i48339_1_);
		registerDefaultState(defaultBlockState().setValue(NORTH, false)
			.setValue(SOUTH, false)
			.setValue(DOWN, false)
			.setValue(UP, false)
			.setValue(WEST, false)
			.setValue(EAST, false));
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN);
		super.createBlockStateDefinition(builder);
	}
	
	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		boolean blockTypeChanged = state.getBlock() != newState.getBlock();
		if (blockTypeChanged && !world.isClientSide)
			BrassFluidPropagator.propagateChangedPipe(world, pos, state);
		if (state.hasBlockEntity() && (blockTypeChanged || !newState.hasBlockEntity()))
			world.removeBlockEntity(pos);
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (!world.isClientSide && state != oldState)
			world.scheduleTick(pos, this, 1, TickPriority.HIGH);
	}

	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos, Player player) {
		return BrassBlocks.BRASS_PIPE.asStack();
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block otherBlock, BlockPos neighborPos,
		boolean isMoving) {
		DebugPackets.sendNeighborsUpdatePacket(world, pos);
		Direction d = BrassFluidPropagator.validateNeighbourChange(state, world, pos, otherBlock, neighborPos, isMoving);
		if (d == null)
			return;
		if (!state.getValue(FACING_TO_PROPERTY_MAP.get(d)))
			return;
		world.scheduleTick(pos, this, 1, TickPriority.HIGH);
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource r) {
		BrassFluidPropagator.propagateChangedPipe(world, pos, state);
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();

		if (world.isClientSide)
			return InteractionResult.SUCCESS;

		context.getLevel()
			.levelEvent(2001, context.getClickedPos(), Block.getId(state));
		BlockState equivalentPipe = transferSixWayProperties(state, BrassBlocks.BRASS_PIPE.getDefaultState());

		Direction firstFound = Direction.UP;
		for (Direction d : Iterate.directions)
			if (state.getValue(FACING_TO_PROPERTY_MAP.get(d))) {
				firstFound = d;
				break;
			}

		BrassFluidTransportBehaviour.cacheFlows(world, pos);
		world.setBlockAndUpdate(pos, BrassBlocks.BRASS_PIPE.get()
			.updateBlockState(equivalentPipe, firstFound, null, world, pos));
		BrassFluidTransportBehaviour.loadFlows(world, pos);
		return InteractionResult.SUCCESS;
	}

	public static BlockState transferSixWayProperties(BlockState from, BlockState to) {
		for (Direction d : Iterate.directions) {
			BooleanProperty property = FACING_TO_PROPERTY_MAP.get(d);
			to = to.setValue(property, from.getValue(property));
		}
		return to;
	}

	@Override
	public ItemRequirement getRequiredItems(BlockState state, BlockEntity te) {
		return ItemRequirement.of(BrassBlocks.BRASS_PIPE.getDefaultState(), te);
	}

	@Override
	public Class<BrassPipeTileEntity> getTileEntityClass() {
		return BrassPipeTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends BrassPipeTileEntity> getTileEntityType() {
		return BrassTiles.ENCASED_BRASS_PIPE.get();
	}

}
