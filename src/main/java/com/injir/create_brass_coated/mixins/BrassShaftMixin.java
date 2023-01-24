package com.injir.create_brass_coated.mixins;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.injir.create_brass_coated.blocks.girder.BrassGirderBlock;
import com.injir.create_brass_coated.blocks.girder.BrassGirderEncasedShaftBlock;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.ShaftBlock;
import com.simibubi.create.content.curiosities.girder.GirderEncasedShaftBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static com.simibubi.create.content.contraptions.base.RotatedPillarKineticBlock.AXIS;
import static com.simibubi.create.foundation.block.ProperWaterloggedBlock.WATERLOGGED;

@Mixin(ShaftBlock.class)
public class BrassShaftMixin {


	@Inject(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Player;getItemInHand(Lnet/minecraft/world/InteractionHand;)Lnet/minecraft/world/item/ItemStack;"), cancellable = true)
	private void Inject(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult ray, CallbackInfoReturnable<InteractionResult> cir) {
		ItemStack heldItem = player.getItemInHand(hand);
		if (BrassBlocks.BRASS_GIRDER.isIn(heldItem) && state.getValue(AXIS) != Direction.Axis.Y) {
			KineticTileEntity.switchToBlockState(world, pos, BrassBlocks.BRASS_GIRDER_ENCASED_SHAFT.getDefaultState()
					.setValue(WATERLOGGED, state.getValue(WATERLOGGED))
					.setValue(GirderEncasedShaftBlock.HORIZONTAL_AXIS, state.getValue(AXIS) == Direction.Axis.Z ? Direction.Axis.Z : Direction.Axis.X));
			if (!world.isClientSide && !player.isCreative()) {
				heldItem.shrink(1);
				if (heldItem.isEmpty())
					player.setItemInHand(hand, ItemStack.EMPTY);
			}
			cir.setReturnValue(InteractionResult.SUCCESS);
		}
	}
}
