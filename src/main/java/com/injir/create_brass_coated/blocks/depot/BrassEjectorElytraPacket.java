package com.injir.create_brass_coated.blocks.depot;

import com.simibubi.create.content.logistics.block.depot.EjectorTileEntity;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.network.NetworkEvent.Context;

import java.util.function.Supplier;

public class BrassEjectorElytraPacket extends SimplePacketBase {

	private BlockPos pos;

	public BrassEjectorElytraPacket(BlockPos pos) {
		this.pos = pos;
	}

	public BrassEjectorElytraPacket(FriendlyByteBuf buffer) {
		pos = buffer.readBlockPos();
	}

	@Override
	public void write(FriendlyByteBuf buffer) {
		buffer.writeBlockPos(pos);
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
				if (tileEntity instanceof BrassEjectorTileEntity)
					((BrassEjectorTileEntity) tileEntity).deployElytra(player);
			});
		context.get()
			.setPacketHandled(true);

	}

}
