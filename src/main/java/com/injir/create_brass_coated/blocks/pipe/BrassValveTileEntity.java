package com.injir.create_brass_coated.blocks.pipe;

import com.injir.create_brass_coated.blocks.pipe.StraightBrassPipeTileEntity.StraightPipeFluidTransportBehaviour;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.foundation.tileEntity.SmartTileEntity;
import com.simibubi.create.foundation.tileEntity.TileEntityBehaviour;
import com.simibubi.create.foundation.utility.animation.LerpedFloat;
import com.simibubi.create.foundation.utility.animation.LerpedFloat.Chaser;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fluids.FluidStack;

import java.util.List;

public class BrassValveTileEntity extends KineticTileEntity {

	LerpedFloat pointer;

	public BrassValveTileEntity(BlockEntityType<?> typeIn, BlockPos pos, BlockState state) {
		super(typeIn, pos, state);
		pointer = LerpedFloat.linear()
			.startWithValue(0)
			.chase(0, 0, Chaser.LINEAR);
	}

	@Override
	public void onSpeedChanged(float previousSpeed) {
		super.onSpeedChanged(previousSpeed);
		float speed = getSpeed();
		pointer.chase(speed > 0 ? 1 : 0, getChaseSpeed(), Chaser.LINEAR);
		sendData();
	}

	@Override
	public void tick() {
		super.tick();
		pointer.tickChaser();

		if (level.isClientSide)
			return;

		BlockState blockState = getBlockState();
		if (!(blockState.getBlock() instanceof BrassValveBlock))
			return;
		boolean stateOpen = blockState.getValue(BrassValveBlock.ENABLED);

		if (stateOpen && pointer.getValue() == 0) {
			switchToBlockState(level, worldPosition, blockState.setValue(BrassValveBlock.ENABLED, false));
			return;
		}
		if (!stateOpen && pointer.getValue() == 1) {
			switchToBlockState(level, worldPosition, blockState.setValue(BrassValveBlock.ENABLED, true));
			return;
		}
	}

	private float getChaseSpeed() {
		return Mth.clamp(Math.abs(getSpeed()) / 16 / 20, 0, 1);
	}

	@Override
	protected void write(CompoundTag compound, boolean clientPacket) {
		super.write(compound, clientPacket);
		compound.put("Pointer", pointer.writeNBT());
	}

	@Override
	protected void read(CompoundTag compound, boolean clientPacket) {
		super.read(compound, clientPacket);
		pointer.readNBT(compound.getCompound("Pointer"), clientPacket);
	}

	@Override
	public void addBehaviours(List<TileEntityBehaviour> behaviours) {
		behaviours.add(new ValvePipeBehaviour(this));
		registerAwardables(behaviours, BrassFluidPropagator.getSharedTriggers());
	}

	class ValvePipeBehaviour extends StraightPipeFluidTransportBehaviour {

		public ValvePipeBehaviour(SmartTileEntity te) {
			super(te);
		}

		@Override
		public boolean canHaveFlowToward(BlockState state, Direction direction) {
			return BrassValveBlock.getPipeAxis(state) == direction.getAxis();
		}

		@Override
		public boolean canPullFluidFrom(FluidStack fluid, BlockState state, Direction direction) {
			if (state.hasProperty(BrassValveBlock.ENABLED) && state.getValue(BrassValveBlock.ENABLED))
				return super.canPullFluidFrom(fluid, state, direction);
			return false;
		}

	}

}
