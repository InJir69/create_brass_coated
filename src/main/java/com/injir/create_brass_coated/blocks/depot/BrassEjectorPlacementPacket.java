package com.injir.create_brass_coated.blocks.depot;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.logistics.block.depot.EjectorBlock;
import com.simibubi.create.content.logistics.block.depot.EjectorTileEntity;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class BrassEjectorPlacementPacket extends SimplePacketBase {

	private int h, v;
	private BlockPos pos;
	private Direction facing;

	public BrassEjectorPlacementPacket(int h, int v, BlockPos pos, Direction facing) {
		this.h = h;
		this.v = v;
		this.pos = pos;
		this.facing = facing;
	}

	public BrassEjectorPlacementPacket(FriendlyByteBuf buffer) {
		h = buffer.readInt();
		v = buffer.readInt();
		pos = buffer.readBlockPos();
		facing = Direction.from3DDataValue(buffer.readVarInt());
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeInt(h);
		buffer.writeInt(v);
		buffer.writeBlockPos(pos);
		buffer.writeVarInt(facing.get3DDataValue());
	}

	@Override
	public void handle(Supplier<Context> context) {
		context.get()
			.enqueueWork(() -> {
				ServerPlayer player = context.get()
					.getSender();
				if (player == null)
					return;
				Level world = player.level;
				if (world == null || !world.isLoaded(pos))
					return;
				BlockEntity tileEntity = world.getBlockEntity(pos);
				BlockState state = world.getBlockState(pos);
				if (tileEntity instanceof BrassEjectorTileEntity)
					((BrassEjectorTileEntity) tileEntity).setTarget(h, v);
				if (BrassBlocks.BRASS_WEIGHTED_EJECTOR.has(state))
					world.setBlockAndUpdate(pos, state.setValue(BrassEjectorBlock.HORIZONTAL_FACING, facing));
			});
		context.get()
			.setPacketHandled(true);

	}

	public static class ClientBoundRequest extends SimplePacketBase {

		BlockPos pos;

		public ClientBoundRequest(BlockPos pos) {
			this.pos = pos;
		}

		public ClientBoundRequest(FriendlyByteBuf buffer) {
			this.pos = buffer.readBlockPos();
		}

		@Override
		public void write(FriendlyByteBuf buffer) {
			buffer.writeBlockPos(pos);
		}

		@Override
		public void handle(Supplier<Context> context) {
			context.get()
				.enqueueWork(() -> DistExecutor.unsafeRunWhenOn(Dist.CLIENT,
					() -> () -> BrassEjectorTargetHandler.flushSettings(pos)));
			context.get()
				.setPacketHandled(true);
		}

	}

}
