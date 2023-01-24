package com.injir.create_brass_coated.blocks.deployer;

import com.simibubi.create.AllItems;
import com.simibubi.create.content.contraptions.components.structureMovement.AbstractContraptionEntity;
import com.simibubi.create.content.contraptions.components.structureMovement.MovementContext;
import com.simibubi.create.content.contraptions.components.structureMovement.MovingInteractionBehaviour;
import com.simibubi.create.content.contraptions.components.structureMovement.mounted.MountedContraption;
import com.simibubi.create.foundation.utility.NBTHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate.StructureBlockInfo;
import org.apache.commons.lang3.tuple.MutablePair;

public class BrassDeployerMovingInteraction extends MovingInteractionBehaviour {

	@Override
	public boolean handlePlayerInteraction(Player player, InteractionHand activeHand, BlockPos localPos,
		AbstractContraptionEntity contraptionEntity) {
		StructureBlockInfo info = contraptionEntity.getContraption()
			.getBlocks()
			.get(localPos);
		if (info == null)
			return false;
		MovementContext ctx = null;
		int index = -1;
		for (MutablePair<StructureBlockInfo, MovementContext> pair : contraptionEntity.getContraption()
			.getActors()) {
			if (info.equals(pair.left)) {
				ctx = pair.right;
				index = contraptionEntity.getContraption()
					.getActors()
					.indexOf(pair);
				break;
			}
		}
		if (ctx == null)
			return false;

		ItemStack heldStack = player.getItemInHand(activeHand);
		if (heldStack.getItem()
			.equals(AllItems.WRENCH.get())) {
			BrassDeployerTileEntity.Mode mode = NBTHelper.readEnum(ctx.tileData, "Mode", BrassDeployerTileEntity.Mode.class);
			NBTHelper.writeEnum(ctx.tileData, "Mode",
				mode == BrassDeployerTileEntity.Mode.PUNCH ? BrassDeployerTileEntity.Mode.USE : BrassDeployerTileEntity.Mode.PUNCH);

		} else {
			if (ctx.world.isClientSide)
				return true; // we'll try again on the server side
			BrassDeployerFakePlayer fake = null;

			if (!(ctx.temporaryData instanceof BrassDeployerFakePlayer) && ctx.world instanceof ServerLevel) {
				BrassDeployerFakePlayer deployerFakePlayer = new BrassDeployerFakePlayer((ServerLevel) ctx.world);
				deployerFakePlayer.onMinecartContraption = ctx.contraption instanceof MountedContraption;
				deployerFakePlayer.getInventory()
					.load(ctx.tileData.getList("Inventory", Tag.TAG_COMPOUND));
				ctx.temporaryData = fake = deployerFakePlayer;
				ctx.tileData.remove("Inventory");
			} else
				fake = (BrassDeployerFakePlayer) ctx.temporaryData;

			if (fake == null)
				return false;

			ItemStack deployerItem = fake.getMainHandItem();
			player.setItemInHand(activeHand, deployerItem.copy());
			fake.setItemInHand(InteractionHand.MAIN_HAND, heldStack.copy());
			ctx.tileData.put("HeldItem", heldStack.serializeNBT());
			ctx.data.put("HeldItem", heldStack.serializeNBT());
		}
		if (index >= 0)
			setContraptionActorData(contraptionEntity, index, info, ctx);
		return true;
	}
}
