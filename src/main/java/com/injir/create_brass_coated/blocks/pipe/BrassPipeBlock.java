package com.injir.create_brass_coated.blocks.pipe;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.fluids.pipes.VanillaFluidTargets;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedTileEntityBehaviour;
import com.simibubi.create.content.contraptions.wrench.IWrenchableWithBracket;
import com.simibubi.create.foundation.advancement.AdvancementBehaviour;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.Iterate;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.network.protocol.game.DebugPackets;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.PipeBlock;
import net.minecraft.world.level.block.SimpleWaterloggedBlock;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.ticks.TickPriority;

import javax.annotation.Nullable;
import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

public class BrassPipeBlock extends PipeBlock
	implements SimpleWaterloggedBlock, IWrenchableWithBracket, ITE<BrassPipeTileEntity> {

	private static final VoxelShape OCCLUSION_BOX = Block.box(4, 4, 4, 12, 12, 12);

	public BrassPipeBlock(Properties properties) {
		super(4 / 16f, properties);
		this.registerDefaultState(super.defaultBlockState().setValue(BlockStateProperties.WATERLOGGED, false));
	}

	@Override
	public InteractionResult onWrenched(BlockState state, UseOnContext context) {
		if (tryRemoveBracket(context))
			return InteractionResult.SUCCESS;

		Level world = context.getLevel();
		BlockPos pos = context.getClickedPos();
		Direction clickedFace = context.getClickedFace();

		Axis axis = getAxis(world, pos, state);
		if (axis == null) {
			Vec3 clickLocation = context.getClickLocation()
				.subtract(pos.getX(), pos.getY(), pos.getZ());
			double closest = Float.MAX_VALUE;
			Direction argClosest = Direction.UP;
			for (Direction direction : Iterate.directions) {
				if (clickedFace.getAxis() == direction.getAxis())
					continue;
				Vec3 centerOf = Vec3.atCenterOf(direction.getNormal());
				double distance = centerOf.distanceToSqr(clickLocation);
				if (distance < closest) {
					closest = distance;
					argClosest = direction;
				}
			}
			axis = argClosest.getAxis();
		}

		if (clickedFace.getAxis() == axis)
			return InteractionResult.PASS;
		if (!world.isClientSide) {
			withTileEntityDo(world, pos, fpte -> fpte.getBehaviour(BrassFluidTransportBehaviour.TYPE).interfaces.values()
				.stream()
				.filter(pc -> pc != null && pc.hasFlow())
				.findAny()
				.ifPresent($ -> AllAdvancements.GLASS_PIPE.awardTo(context.getPlayer())));
			
			BrassFluidTransportBehaviour.cacheFlows(world, pos);
			world.setBlockAndUpdate(pos, BrassBlocks.GLASS_BRASS_PIPE.getDefaultState()
				.setValue(GlassBrassPipeBlock.AXIS, axis)
				.setValue(BlockStateProperties.WATERLOGGED, state.getValue(BlockStateProperties.WATERLOGGED)));
			BrassFluidTransportBehaviour.loadFlows(world, pos);
		}
		return InteractionResult.SUCCESS;
	}

	@Override
	public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, LivingEntity pPlacer, ItemStack pStack) {
		super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
		AdvancementBehaviour.setPlacedBy(pLevel, pPos, pPlacer);
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
		BlockHitResult hit) {
		if (!AllBlocks.BRASS_CASING.isIn(player.getItemInHand(hand)))
			return InteractionResult.PASS;
		if (world.isClientSide)
			return InteractionResult.SUCCESS;
		
		BrassFluidTransportBehaviour.cacheFlows(world, pos);
		world.setBlockAndUpdate(pos,
			EncasedBrassPipeBlock.transferSixWayProperties(state, BrassBlocks.ENCASED_BRASS_PIPE.getDefaultState()));
		BrassFluidTransportBehaviour.loadFlows(world, pos);
		return InteractionResult.SUCCESS;

	}

	public BlockState getAxisState(Axis axis) {
		BlockState defaultState = defaultBlockState();
		for (Direction d : Iterate.directions)
			defaultState = defaultState.setValue(PROPERTY_BY_DIRECTION.get(d), d.getAxis() == axis);
		return defaultState;
	}

	@Nullable
	private Axis getAxis(BlockGetter world, BlockPos pos, BlockState state) {
		return BrassFluidPropagator.getStraightPipeAxis(state);
	}

	@Override
	public void onRemove(BlockState state, Level world, BlockPos pos, BlockState newState, boolean isMoving) {
		boolean blockTypeChanged = state.getBlock() != newState.getBlock();
		if (blockTypeChanged && !world.isClientSide)
			BrassFluidPropagator.propagateChangedPipe(world, pos, state);
		if (state != newState && !isMoving)
			removeBracket(world, pos, true).ifPresent(stack -> Block.popResource(world, pos, stack));
		if (state.hasBlockEntity() && (blockTypeChanged || !newState.hasBlockEntity()))
			world.removeBlockEntity(pos);
	}

	@Override
	public void onPlace(BlockState state, Level world, BlockPos pos, BlockState oldState, boolean isMoving) {
		if (world.isClientSide)
			return;
		if (state != oldState)
			world.scheduleTick(pos, this, 1, TickPriority.HIGH);
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
	public void tick(BlockState state, ServerLevel world, BlockPos pos, RandomSource r) {
		BrassFluidPropagator.propagateChangedPipe(world, pos, state);
	}

	public static boolean isPipe(BlockState state) {
		return state.getBlock() instanceof BrassPipeBlock;
	}

	public static boolean canConnectTo(BlockAndTintGetter world, BlockPos neighbourPos, BlockState neighbour,
		Direction direction) {
		if (BrassFluidPropagator.hasFluidCapability(world, neighbourPos, direction.getOpposite()))
			return true;
		if (VanillaFluidTargets.shouldPipesConnectTo(neighbour))
			return true;
		BrassFluidTransportBehaviour transport = TileEntityBehaviour.get(world, neighbourPos, BrassFluidTransportBehaviour.TYPE);
		BracketedTileEntityBehaviour bracket =
			TileEntityBehaviour.get(world, neighbourPos, BracketedTileEntityBehaviour.TYPE);
		if (isPipe(neighbour))
			return bracket == null || !bracket.isBracketPresent()
				|| BrassFluidPropagator.getStraightPipeAxis(neighbour) == direction.getAxis();
		if (transport == null)
			return false;
		return transport.canHaveFlowToward(neighbour, direction.getOpposite());
	}

	public static boolean shouldDrawRim(BlockAndTintGetter world, BlockPos pos, BlockState state, Direction direction) {
		BlockPos offsetPos = pos.relative(direction);
		BlockState facingState = world.getBlockState(offsetPos);
		if (facingState.getBlock() instanceof EncasedBrassPipeBlock)
			return true;
		if (!isPipe(facingState))
			return true;
		if (!canConnectTo(world, offsetPos, facingState, direction))
			return true;
		return false;
	}

	public static boolean isOpenAt(BlockState state, Direction direction) {
		return state.getValue(PROPERTY_BY_DIRECTION.get(direction));
	}

	public static boolean isCornerOrEndPipe(BlockAndTintGetter world, BlockPos pos, BlockState state) {
		return isPipe(state) && BrassFluidPropagator.getStraightPipeAxis(state) == null
			&& !shouldDrawCasing(world, pos, state);
	}

	public static boolean shouldDrawCasing(BlockAndTintGetter world, BlockPos pos, BlockState state) {
		if (!isPipe(state))
			return false;
		for (Axis axis : Iterate.axes) {
			int connections = 0;
			for (Direction direction : Iterate.directions)
				if (direction.getAxis() != axis && isOpenAt(state, direction))
					connections++;
			if (connections > 2)
				return true;
		}
		return false;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		builder.add(NORTH, EAST, SOUTH, WEST, UP, DOWN, BlockStateProperties.WATERLOGGED);
		super.createBlockStateDefinition(builder);
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		FluidState FluidState = context.getLevel()
			.getFluidState(context.getClickedPos());
		return updateBlockState(defaultBlockState(), context.getNearestLookingDirection(), null, context.getLevel(),
			context.getClickedPos()).setValue(BlockStateProperties.WATERLOGGED,
				Boolean.valueOf(FluidState.getType() == Fluids.WATER));
	}

	@Override
	public BlockState updateShape(BlockState state, Direction direction, BlockState neighbourState, LevelAccessor world,
		BlockPos pos, BlockPos neighbourPos) {
		if (state.getValue(BlockStateProperties.WATERLOGGED))
			world.scheduleTick(pos, Fluids.WATER, Fluids.WATER.getTickDelay(world));
		if (isOpenAt(state, direction) && neighbourState.hasProperty(BlockStateProperties.WATERLOGGED))
			world.scheduleTick(pos, this, 1, TickPriority.HIGH);
		return updateBlockState(state, direction, direction.getOpposite(), world, pos);
	}

	public BlockState updateBlockState(BlockState state, Direction preferredDirection, @Nullable Direction ignore,
		BlockAndTintGetter world, BlockPos pos) {

		BracketedTileEntityBehaviour bracket = TileEntityBehaviour.get(world, pos, BracketedTileEntityBehaviour.TYPE);
		if (bracket != null && bracket.isBracketPresent())
			return state;

		BlockState prevState = state;
		int prevStateSides = (int) Arrays.stream(Iterate.directions)
			.map(PROPERTY_BY_DIRECTION::get)
			.filter(prevState::getValue)
			.count();

		// Update sides that are not ignored
		for (Direction d : Iterate.directions)
			if (d != ignore) {
				boolean shouldConnect = canConnectTo(world, pos.relative(d), world.getBlockState(pos.relative(d)), d);
				state = state.setValue(PROPERTY_BY_DIRECTION.get(d), shouldConnect);
			}

		// See if it has enough connections
		Direction connectedDirection = null;
		for (Direction d : Iterate.directions) {
			if (isOpenAt(state, d)) {
				if (connectedDirection != null)
					return state;
				connectedDirection = d;
			}
		}

		// Add opposite end if only one connection
		if (connectedDirection != null)
			return state.setValue(PROPERTY_BY_DIRECTION.get(connectedDirection.getOpposite()), true);

		// If we can't connect to anything and weren't connected before, do nothing
		if (prevStateSides == 2)
			return prevState;

		// Use preferred
		return state.setValue(PROPERTY_BY_DIRECTION.get(preferredDirection), true)
			.setValue(PROPERTY_BY_DIRECTION.get(preferredDirection.getOpposite()), true);
	}

	@Override
	public FluidState getFluidState(BlockState state) {
		return state.getValue(BlockStateProperties.WATERLOGGED) ? Fluids.WATER.getSource(false)
			: Fluids.EMPTY.defaultFluidState();
	}

	@Override
	public Optional<ItemStack> removeBracket(BlockGetter world, BlockPos pos, boolean inOnReplacedContext) {
		BracketedTileEntityBehaviour behaviour =
			BracketedTileEntityBehaviour.get(world, pos, BracketedTileEntityBehaviour.TYPE);
		if (behaviour == null)
			return Optional.empty();
		BlockState bracket = behaviour.removeBracket(inOnReplacedContext);
		if (bracket == null)
			return Optional.empty();
		return Optional.of(new ItemStack(bracket.getBlock()));
	}

	@Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	}

	@Override
	public Class<BrassPipeTileEntity> getTileEntityClass() {
		return BrassPipeTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends BrassPipeTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_PIPE.get();
	}

	@Override
	public boolean supportsExternalFaceHiding(BlockState state) {
		return false;
	}

	@Override
	public VoxelShape getOcclusionShape(BlockState pState, BlockGetter pLevel, BlockPos pPos) {
		return OCCLUSION_BOX;
	}

}