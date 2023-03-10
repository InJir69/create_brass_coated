package com.injir.create_brass_coated.blocks.depot;

import com.injir.create_brass_coated.blocks.BrassTiles;
import com.simibubi.create.AllShapes;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.wrench.IWrenchable;
import com.simibubi.create.content.logistics.block.depot.DepotTileEntity;
import com.simibubi.create.content.logistics.block.depot.SharedDepotBlockMethods;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class BrassDepotBlock extends Block implements ITE<BrassDepotTileEntity>, IWrenchable {

	public BrassDepotBlock(Properties p_i48440_1_) {
		super(p_i48440_1_);
	}

	@Override
	public VoxelShape getShape(BlockState p_220053_1_, BlockGetter p_220053_2_, BlockPos p_220053_3_,
		CollisionContext p_220053_4_) {
		return AllShapes.DEPOT;
	}

	@Override
	public Class<BrassDepotTileEntity> getTileEntityClass() {
		return BrassDepotTileEntity.class;
	}
	
	@Override
	public BlockEntityType<? extends BrassDepotTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_DEPOT.get();
	}

	@Override
	public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand,
		BlockHitResult ray) {
		return BrassSharedDepotBlockMethods.onUse(state, world, pos, player, hand, ray);
	}

	@Override
	public void onRemove(BlockState state, Level worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
		BrassSharedDepotBlockMethods.onReplaced(state, worldIn, pos, newState, isMoving);
	}

	@Override
	public void updateEntityAfterFallOn(BlockGetter worldIn, Entity entityIn) {
		super.updateEntityAfterFallOn(worldIn, entityIn);
		BrassSharedDepotBlockMethods.onLanded(worldIn, entityIn);
	}

	@Override
	public boolean hasAnalogOutputSignal(BlockState state) {
		return true;
	}

	@Override
	public int getAnalogOutputSignal(BlockState blockState, Level worldIn, BlockPos pos) {
		return BrassSharedDepotBlockMethods.getComparatorInputOverride(blockState, worldIn, pos);
	}
	
	@Override
	public boolean isPathfindable(BlockState state, BlockGetter reader, BlockPos pos, PathComputationType type) {
		return false;
	}

}
