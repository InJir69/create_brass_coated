package com.injir.create_brass_coated.items;

import com.injir.create_brass_coated.BrassTab;
import com.injir.create_brass_coated.Create_Brass_Coated;
import com.injir.create_brass_coated.blocks.gearbox.BrassVerticalGearboxItem;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.relays.gearbox.VerticalGearboxItem;
import com.simibubi.create.foundation.data.AssetLookup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.tterrag.registrate.util.entry.ItemEntry;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;

import static com.simibubi.create.Create.REGISTRATE;

public class BrassItems {

    private static final CreateRegistrate REGISTRATE = Create_Brass_Coated.registrate();

    static {
        CreateRegistrate REGISTRATE = Create_Brass_Coated.registrate().creativeModeTab(() -> BrassTab.BRASS_TAB);
    }

    public static final ItemEntry<BrassVerticalGearboxItem> VERTICAL_BRASS_GEARBOX =
            REGISTRATE.item("vertical_brass_gearbox", BrassVerticalGearboxItem::new)
                    .model(AssetLookup.customBlockItemModel("brass_gearbox", "item_vertical"))
                    .register();
    public static void register() {}
}
