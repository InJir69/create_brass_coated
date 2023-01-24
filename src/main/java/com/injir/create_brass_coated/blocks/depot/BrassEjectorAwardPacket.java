package com.injir.create_brass_coated.blocks.depot;

import com.simibubi.create.content.logistics.block.depot.EjectorTileEntity;
import com.simibubi.create.foundation.advancement.AllAdvancements;
import com.simibubi.create.foundation.networking.TileEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;

public class BrassEjectorAwardPacket extends TileEntityConfigurationPacket<BrassEjectorTileEntity> {

	public BrassEjectorAwardPacket(FriendlyByteBuf buffer) {
		super(buffer);
	}

	public BrassEjectorAwardPacket(BlockPos pos) {
		super(pos);
	}

	@Override
	protected void writeSettings(FriendlyByteBuf buffer) {}

	@Override
	protected void readSettings(FriendlyByteBuf buffer) {}

	@Override
	protected void applySettings(ServerPlayer player, BrassEjectorTileEntity te) {
		AllAdvancements.EJECTOR_MAXED.awardTo(player);
	}

	@Override
	protected void applySettings(BrassEjectorTileEntity te) {}

}
