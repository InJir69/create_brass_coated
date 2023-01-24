package com.injir.create_brass_coated.blocks.other;

import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.content.contraptions.base.DirectionalAxisKineticBlock;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.contraptions.relays.encased.AdjustablePulleyTileEntity;
import com.simibubi.create.content.contraptions.relays.encased.EncasedBeltBlock;
import com.simibubi.create.foundation.block.ITE;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.Direction.AxisDirection;
import net.minecraft.util.StringRepresentable;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.PushReaction;

public class BrassEncasedBeltBlock extends RotatedPillarKineticBlock implements ITE<KineticTileEntity> {

	public BrassEncasedBeltBlock(Properties properties) {
		super(properties);
		registerDefaultState(defaultBlockState().setValue(PART, BrassEncasedBeltBlock.Part.NONE));
	}
	public static final Property<BrassEncasedBeltBlock.Part> PART = EnumProperty.create("part", BrassEncasedBeltBlock.Part.class);
	public static final BooleanProperty CONNECTED_ALONG_FIRST_COORDINATE =
			DirectionalAxisKineticBlock.AXIS_ALONG_FIRST_COORDINATE;

	@Override
	public boolean shouldCheckWeakPower(BlockState state, LevelReader world, BlockPos pos, Direction side) {
		return false;
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.NORMAL;
	}

	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder.add(PART, CONNECTED_ALONG_FIRST_COORDINATE));
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		Axis placedAxis = context.getNearestLookingDirection()
				.getAxis();
		Axis axis = context.getPlayer() != null && context.getPlayer()
				.isShiftKeyDown() ? placedAxis : getPreferredAxis(context);
		if (axis == null)
			axis = placedAxis;

		BlockState state = defaultBlockState().setValue(AXIS, axis);
		for (Direction facing : Iterate.directions) {
			if (facing.getAxis() == axis)
				continue;
			BlockPos pos = context.getClickedPos();
			BlockPos offset = pos.relative(facing);
			state = updateShape(state, facing, context.getLevel()
					.getBlockState(offset), context.getLevel(), pos, offset);
		}
		return state;
	}

	@Override
	public BlockState updateShape(BlockState stateIn, Direction face, BlockState neighbour, LevelAccessor worldIn,
								  BlockPos currentPos, BlockPos facingPos) {
		BrassEncasedBeltBlock.Part part = stateIn.getValue(PART);
		Axis axis = stateIn.getValue(AXIS);
		boolean connectionAlongFirst = stateIn.getValue(CONNECTED_ALONG_FIRST_COORDINATE);
		Axis connectionAxis =
				connectionAlongFirst ? (axis == Axis.X ? Axis.Y : Axis.X) : (axis == Axis.Z ? Axis.Y : Axis.Z);

		Axis faceAxis = face.getAxis();
		boolean facingAlongFirst = axis == Axis.X ? faceAxis.isVertical() : faceAxis == Axis.X;
		boolean positive = face.getAxisDirection() == AxisDirection.POSITIVE;

		if (axis == faceAxis)
			return stateIn;

		if (!(neighbour.getBlock() instanceof BrassEncasedBeltBlock)) {
			if (facingAlongFirst != connectionAlongFirst || part == BrassEncasedBeltBlock.Part.NONE)
				return stateIn;
			if (part == BrassEncasedBeltBlock.Part.MIDDLE)
				return stateIn.setValue(PART, positive ? BrassEncasedBeltBlock.Part.END : BrassEncasedBeltBlock.Part.START);
			if ((part == BrassEncasedBeltBlock.Part.START) == positive)
				return stateIn.setValue(PART, BrassEncasedBeltBlock.Part.NONE);
			return stateIn;
		}

		BrassEncasedBeltBlock.Part otherPart = neighbour.getValue(PART);
		Axis otherAxis = neighbour.getValue(AXIS);
		boolean otherConnection = neighbour.getValue(CONNECTED_ALONG_FIRST_COORDINATE);
		Axis otherConnectionAxis =
				otherConnection ? (otherAxis == Axis.X ? Axis.Y : Axis.X) : (otherAxis == Axis.Z ? Axis.Y : Axis.Z);

		if (neighbour.getValue(AXIS) == faceAxis)
			return stateIn;
		if (otherPart != BrassEncasedBeltBlock.Part.NONE && otherConnectionAxis != faceAxis)
			return stateIn;

		if (part == BrassEncasedBeltBlock.Part.NONE) {
			part = positive ? BrassEncasedBeltBlock.Part.START : BrassEncasedBeltBlock.Part.END;
			connectionAlongFirst = axis == Axis.X ? faceAxis.isVertical() : faceAxis == Axis.X;
		} else if (connectionAxis != faceAxis) {
			return stateIn;
		}

		if ((part == BrassEncasedBeltBlock.Part.START) != positive)
			part = BrassEncasedBeltBlock.Part.MIDDLE;

		return stateIn.setValue(PART, part)
				.setValue(CONNECTED_ALONG_FIRST_COORDINATE, connectionAlongFirst);
	}

	@Override
	public BlockState getRotatedBlockState(BlockState originalState, Direction targetedFace) {
		if (originalState.getValue(PART) == BrassEncasedBeltBlock.Part.NONE)
			return super.getRotatedBlockState(originalState, targetedFace);
		return super.getRotatedBlockState(originalState,
				Direction.get(AxisDirection.POSITIVE, getConnectionAxis(originalState)));
	}

	@Override
	public BlockState updateAfterWrenched(BlockState newState, UseOnContext context) {
//		Blocks.AIR.getDefaultState()
//			.updateNeighbors(context.getWorld(), context.getPos(), 1);
		Axis axis = newState.getValue(AXIS);
		newState = defaultBlockState().setValue(AXIS, axis);
		if (newState.hasProperty(BlockStateProperties.POWERED))
			newState = newState.setValue(BlockStateProperties.POWERED, context.getLevel()
					.hasNeighborSignal(context.getClickedPos()));
		for (Direction facing : Iterate.directions) {
			if (facing.getAxis() == axis)
				continue;
			BlockPos pos = context.getClickedPos();
			BlockPos offset = pos.relative(facing);
			newState = updateShape(newState, facing, context.getLevel()
					.getBlockState(offset), context.getLevel(), pos, offset);
		}
//		newState.updateNeighbors(context.getWorld(), context.getPos(), 1 | 2);
		return newState;
	}

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() == state.getValue(AXIS);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(AXIS);
	}

	public static boolean areBlocksConnected(BlockState state, BlockState other, Direction facing) {
		BrassEncasedBeltBlock.Part part = state.getValue(PART);
		Axis connectionAxis = getConnectionAxis(state);
		Axis otherConnectionAxis = getConnectionAxis(other);

		if (otherConnectionAxis != connectionAxis)
			return false;
		if (facing.getAxis() != connectionAxis)
			return false;
		if (facing.getAxisDirection() == AxisDirection.POSITIVE && (part == BrassEncasedBeltBlock.Part.MIDDLE || part == BrassEncasedBeltBlock.Part.START))
			return true;
		if (facing.getAxisDirection() == AxisDirection.NEGATIVE && (part == BrassEncasedBeltBlock.Part.MIDDLE || part == BrassEncasedBeltBlock.Part.END))
			return true;

		return false;
	}

	protected static Axis getConnectionAxis(BlockState state) {
		Axis axis = state.getValue(AXIS);
		boolean connectionAlongFirst = state.getValue(CONNECTED_ALONG_FIRST_COORDINATE);
		Axis connectionAxis =
				connectionAlongFirst ? (axis == Axis.X ? Axis.Y : Axis.X) : (axis == Axis.Z ? Axis.Y : Axis.Z);
		return connectionAxis;
	}

	public static float getRotationSpeedModifier(KineticTileEntity from, KineticTileEntity to) {
		float fromMod = 1;
		float toMod = 1;
		if (from instanceof AdjustablePulleyTileEntity)
			fromMod = ((AdjustablePulleyTileEntity) from).getModifier();
		if (to instanceof AdjustablePulleyTileEntity)
			toMod = ((AdjustablePulleyTileEntity) to).getModifier();
		return fromMod / toMod;
	}

	public enum Part implements StringRepresentable {
		START, MIDDLE, END, NONE;

		@Override
		public String getSerializedName() {
			return Lang.asId(name());
		}
	}
	@Override
	public Class<KineticTileEntity> getTileEntityClass() {
		return KineticTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends KineticTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_ENCASED_CHAIN_DRIVE.get();
	}

}
