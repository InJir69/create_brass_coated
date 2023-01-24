package com.injir.create_brass_coated;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.simibubi.create.foundation.data.CreateRegistrate;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class BrassTab {
	public static final CreativeModeTab BRASS_TAB = new CreativeModeTab("create_brass_coated") {
		@Override
		public ItemStack makeIcon() {return BrassBlocks.BRASS_MECHANICAL_DRILL.asStack();}
	};
	private static final CreateRegistrate REGISTRATE = Create_Brass_Coated.registrate().creativeModeTab(() -> BRASS_TAB, "Create: Brass Coated");
}
