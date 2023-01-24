package com.injir.create_brass_coated.blocks.depot;

import com.simibubi.create.content.logistics.block.depot.EjectorTileEntity;
import com.simibubi.create.foundation.networking.TileEntityConfigurationPacket;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

public class BrassEjectorTriggerPacket extends TileEntityConfigurationPacket<BrassEjectorTileEntity> {

	public BrassEjectorTriggerPacket(BlockPos pos) {
		super(pos);
	}

	public BrassEjectorTriggerPacket(FriendlyByteBuf buffer) {
		super(buffer);
	}

	@Override
	protected void writeSettings(FriendlyByteBuf buffer) {}

	@Override
	protected void readSettings(FriendlyByteBuf buffer) {}

	@Override
	protected void applySettings(BrassEjectorTileEntity te) {
		te.activate();
	}

}
