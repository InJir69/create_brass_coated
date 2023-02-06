package com.injir.create_brass_coated.blocks.chute;

import com.simibubi.create.content.logistics.block.chute.AbstractChuteBlock;
import com.simibubi.create.content.logistics.block.chute.ChuteTileEntity;
import com.simibubi.create.content.logistics.block.chute.SmartChuteBlock;
import com.simibubi.create.content.logistics.block.chute.SmartChuteFilterSlotPositioning;
import com.simibubi.create.foundation.item.ItemHelper.ExtractionCountMode;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.tileEntity.behaviour.filtering.FilteringBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;

public class BrassSmartChuteTileEntity extends BrassChuteTileEntity {

	FilteringBehaviour filtering;

	public BrassSmartChuteTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	protected boolean canAcceptItem(ItemStack stack) {
		return super.canAcceptItem(stack) && canCollectItemsFromBelow() && filtering.test(stack);
	}

	@Override
	protected int getExtractionAmount() {
		return filtering.isCountVisible() && !filtering.anyAmount() ? filtering.getAmount() : 64;
	}

	@Override
	protected ExtractionCountMode getExtractionMode() {
		return filtering.isCountVisible() && !filtering.anyAmount() ? ExtractionCountMode.EXACTLY
			: ExtractionCountMode.UPTO;
	}

	@Override
	protected boolean canCollectItemsFromBelow() {
		BlockState blockState = getBlockState();
		return blockState.hasProperty(BrassSmartChuteBlock.POWERED) && !blockState.getValue(BrassSmartChuteBlock.POWERED);
	}
	
	@Override
	protected boolean canOutputItems() {
		BlockState blockState = getBlockState();
		return blockState.hasProperty(BrassSmartChuteBlock.POWERED) && !blockState.getValue(BrassSmartChuteBlock.POWERED);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
		behaviours.add(filtering =
			new FilteringBehaviour(this, new BrassSmartChuteFilterSlotPositioning()).showCountWhen(this::isExtracting));
		super.addBehaviours(behaviours);
	}

	private boolean isExtracting() {
		boolean up = getItemMotion() < 0;
		BlockPos chutePos = worldPosition.relative(up ? Direction.UP : Direction.DOWN);
		BlockState blockState = level.getBlockState(chutePos);
		return !BrassAbstractChuteBlock.isChute(blockState) && !blockState.getMaterial()
			.isReplaceable();
	}

}
