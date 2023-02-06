package com.injir.create_brass_coated.blocks.chute;

import com.mojang.blaze3d.vertex.PoseStack;
import com.simibubi.create.content.logistics.block.chute.ChuteRenderer;
import com.simibubi.create.content.logistics.block.chute.SmartChuteTileEntity;
import com.simibubi.create.foundation.tileEntity.renderer.SmartTileEntityRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class BrassSmartChuteRenderer extends SmartTileEntityRenderer<BrassSmartChuteTileEntity> {

	public BrassSmartChuteRenderer(BlockEntityRendererProvider.Context context) {
		super(context);
	}

	@Override
	protected void renderSafe(BrassSmartChuteTileEntity tileEntityIn, float partialTicks, PoseStack ms,
		MultiBufferSource buffer, int light, int overlay) {
		super.renderSafe(tileEntityIn, partialTicks, ms, buffer, light, overlay);
		if (tileEntityIn.item.isEmpty())
			return;
		if (tileEntityIn.itemPosition.getValue(partialTicks) > 0)
			return;
		BrassChuteRenderer.renderItem(tileEntityIn, partialTicks, ms, buffer, light, overlay);
	}

}
