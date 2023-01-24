package com.injir.create_brass_coated.blocks.depot;

import com.simibubi.create.content.logistics.block.depot.DepotBehaviour;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.List;

public class BrassDepotTileEntity extends SmartTileEntity {

	BrassDepotBehaviour depotBehaviour;

	public BrassDepotTileEntity(BlockEntityType<?> type, BlockPos pos, BlockState state) {
		super(type, pos, state);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
		behaviours.add(depotBehaviour = new BrassDepotBehaviour(this));
		depotBehaviour.addSubBehaviours(behaviours);
	}

	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			return depotBehaviour.getItemCapability(cap, side);
		return super.getCapability(cap, side);
	}
}
