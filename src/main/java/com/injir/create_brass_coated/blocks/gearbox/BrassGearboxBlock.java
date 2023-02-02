package com.injir.create_brass_coated.blocks.gearbox;

import com.injir.create_brass_coated.blocks.BrassTiles;
import com.injir.create_brass_coated.items.BrassItems;
import com.simibubi.create.AllItems;
import com.simibubi.create.AllTileEntities;
import com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxTileEntity;
import com.simibubi.create.foundation.block.ITE;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Direction.Axis;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.storage.loot.LootContext.Builder;
import net.minecraft.world.phys.HitResult;

import java.util.Arrays;
import java.util.List;

public class BrassGearboxBlock extends RotatedPillarKineticBlock implements ITE<GearboxTileEntity> {

	public BrassGearboxBlock(Properties properties) {
		super(properties);
	}

	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.PUSH_ONLY;
	}

	@Override
	public void fillItemCategory(CreativeModeTab group, NonNullList<ItemStack> items) {
		super.fillItemCategory(group, items);
		items.add(BrassItems.VERTICAL_BRASS_GEARBOX.asStack());
	}

	@SuppressWarnings("deprecation")
	@Override
	public List<ItemStack> getDrops(BlockState state, Builder builder) {
		if (state.getValue(AXIS).isVertical())
			return super.getDrops(state, builder);
		return Arrays.asList(new ItemStack(BrassItems.VERTICAL_BRASS_GEARBOX.get()));
	}
	
	@Override
	public ItemStack getCloneItemStack(BlockState state, HitResult target, BlockGetter world, BlockPos pos,
			Player player) {
		if (state.getValue(AXIS).isVertical())
			return super.getCloneItemStack(state, target, world, pos, player);
		return new ItemStack(BrassItems.VERTICAL_BRASS_GEARBOX.get());
	}

	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(AXIS, Axis.Y);
	}

	// IRotate:

	@Override
	public boolean hasShaftTowards(LevelReader world, BlockPos pos, BlockState state, Direction face) {
		return face.getAxis() != state.getValue(AXIS);
	}

	@Override
	public Axis getRotationAxis(BlockState state) {
		return state.getValue(AXIS);
	}

	@Override
	public Class<GearboxTileEntity> getTileEntityClass() {
		return GearboxTileEntity.class;
	}

	@Override
	public BlockEntityType<? extends GearboxTileEntity> getTileEntityType() {
		return BrassTiles.BRASS_GEARBOX.get();
	}
}
