package com.injir.create_brass_coated.blocks.chute;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandler;

public class BrassChuteItemHandler implements IItemHandler {

	private BrassChuteTileEntity te;

	public BrassChuteItemHandler(BrassChuteTileEntity te) {
		this.te = te;
	}
	
	@Override
	public int getSlots() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int slot) {
		return te.item;
	}

	@Override
	public ItemStack insertItem(int slot, ItemStack stack, boolean simulate) {
		if (!te.canAcceptItem(stack))
			return stack;
		if (!simulate) 
			te.setItem(stack);
		return ItemStack.EMPTY;
	}

	@Override
	public ItemStack extractItem(int slot, int amount, boolean simulate) {
		ItemStack remainder = te.item.copy();
		ItemStack split = remainder.split(amount);
		if (!simulate) 
			te.setItem(remainder);
		return split;
	}

	@Override
	public int getSlotLimit(int slot) {
		return Math.min(64, getStackInSlot(slot).getMaxStackSize());
	}

	@Override
	public boolean isItemValid(int slot, ItemStack stack) {
		return true;
	}

}
