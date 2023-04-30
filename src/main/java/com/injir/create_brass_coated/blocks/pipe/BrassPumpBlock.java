package com.injir.create_brass_coated.blocks.pipe;

import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.AllShapes;
import com.simibubi.create.content.contraptions.base.DirectionalKineticBlock;
import com.simibubi.create.content.contraptions.relays.elementary.ICogWheel;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.block.ProperWaterloggedBlock;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;

import java.util.Random;

public class BrassPumpBlock extends DirectionalKineticBlock
	implements SimpleWaterloggedBlock, ICogWheel, ITE<BrassPumpTileEntity> {

	public BrassPumpBlock(Properties p_i48415_1_) {
		super(p_i48415_1_);
		registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
		return originalState.setValue(FACING, originalState.getValue(FACING)
			.getOpposite());
	}

	@Override
	public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
		return super.updateAfterWrenched(newState, context);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(FACING)
			.getAxis();
	}

	@Override
	public VoxelShape getShape(BlockState state, BlockGetter p_220053_2_, BlockPos p_220053_3_,
		CollisionContext p_220053_4_) {
		return AllShapes.PUMP.get(state.getValue(FACING));
	}

	@Override
	public void neighborChanged(BlockState state, Level world, BlockPos pos, Block otherBlock, BlockPos neighborPos,
		boolean isMoving) {
		DebugPackets.sendNeighborsUpdatePacket(world, pos);
		Direction d = BrassFluidPropagator.validateNeighbourChange(state, world, pos, otherBlock, neighborPos, isMoving);
		if (d == null)
			return;
		if (!isOpenAt(state, d))
			return;
		world.scheduleTick(pos, this, 1, TickPriority.HIGH);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false)
			: Fluids.EMPTY.defaultFluidState();
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(BlockStateProperties.WATERLOGGED);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world,
		BlockPos pos, BlockPos neighbourPos) {
		if (state.getValue(BlockStateProperties.WATERLOGGED))
			world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		return state;
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		BlockState toPlace = super.getStateForPlacement(context);
		Level level = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Player player = context.getPlayer();
		toPlace = ProperWaterloggedBlock.withWater(level, toPlace, pos);

		if (player != null && player.isSteppingCarefully())
			return toPlace;

		for (Direction d : Iterate.directions) {
			BlockPos adjPos = pos.relative(d);
			BlockState adjState = level.getBlockState(adjPos);
			if (!BrassPipeBlock.canConnectTo(level, adjPos, adjState, d))
				continue;
			toPlace = toPlace.setValue(FACING, d);
			if (context.getClickedFace() == d.getOpposite())
				break;
		}

		return toPlace;
	}

	public static boolean isPump(BlockState state) {
		return state.getBlock() instanceof BrassPumpBlock;
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
		super.onPlace(state, world, pos, oldState, isMoving);
		if (world.isClientSide)
			return;
		if (state != oldState)
			world.scheduleTick(pos, this, 1, TickPriority.HIGH);

		if (isPump(state) && isPump(oldState) && state.getValue(FACING) == oldState.getValue(FACING)
			.getOpposite()) {
			BlockEntity tileEntity = world.getBlockEntity(pos);
			if (!(tileEntity instanceof BrassPumpTileEntity))
				return;
			BrassPumpTileEntity pump = (BrassPumpTileEntity) tileEntity;
			pump.pressureUpdate = true;
		}
	}

	public static boolean isOpenAt(BlockState state, Direction d) {
		return d.getAxis() == state.getValue(FACING)
			.getAxis();
	}

	@Override
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource r) {
		BrassFluidPropagator.propagateChangedPipe(world, pos, state);
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
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public Class<BrassPumpTileEntity> getTileEntityClass() {
		return BrassPumpTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends BrassPumpTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_MECHANICAL_PUMP.get();
	}

}