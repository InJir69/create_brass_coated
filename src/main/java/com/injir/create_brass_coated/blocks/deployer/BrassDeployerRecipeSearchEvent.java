package com.injir.create_brass_coated.blocks.deployer;

import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import net.minecraft.world.Container;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.items.wrapper.RecipeWrapper;

import javax.annotation.Nullable;
import java.util.Optional;
import java.util.function.Supplier;

public class BrassDeployerRecipeSearchEvent extends Event {
	private final BrassDeployerTileEntity tileEntity;
	private final RecipeWrapper inventory;
	@Nullable
	Recipe<? extends Container> recipe = null;
	private int maxPriority = 0;

	public BrassDeployerRecipeSearchEvent(BrassDeployerTileEntity tileEntity, RecipeWrapper inventory) {
		this.tileEntity = tileEntity;
		this.inventory = inventory;
	}

	@Override
	public boolean isCancelable() {
		return true;
	}

	public BrassDeployerTileEntity getTileEntity() {
		return tileEntity;
	}

	public RecipeWrapper getInventory() {
		return inventory;
	}

	// lazyness to not scan for recipes that aren't selected
	public boolean shouldAddRecipeWithPriority(int priority) {
		return !isCanceled() && priority > maxPriority;
	}

	@Nullable
	public Recipe<? extends Container> getRecipe() {
		if (isCanceled())
			return null;
		return recipe;
	}

	public void addRecipe(Supplier<Optional<? extends Recipe<? extends Container>>> recipeSupplier, int priority) {
		if (!shouldAddRecipeWithPriority(priority))
			return;
		recipeSupplier.get().ifPresent(newRecipe -> {
			this.recipe = newRecipe;
			maxPriority = priority;
		});
	}
}
