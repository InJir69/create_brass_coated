package com.injir.create_brass_coated;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.injir.create_brass_coated.blocks.BrassPartials;
import com.injir.create_brass_coated.blocks.BrassTiles;
import com.injir.create_brass_coated.items.BrassItems;
import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.CreateItemGroup;
import com.simibubi.create.content.contraptions.TorquePropagator;
import com.simibubi.create.content.palettes.PalettesItemGroup;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.ponder.content.PonderIndex;
import com.simibubi.create.foundation.utility.CreateRegistry;
import com.simibubi.create.foundation.utility.outliner.Outliner;
import com.tterrag.registrate.util.nullness.NonNullSupplier;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import static com.simibubi.create.Create.onCtor;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("create_brass_coated")
public class Create_Brass_Coated
{
    public static final String ID = "create_brass_coated";
    private static final NonNullSupplier<CreateRegistrate> BRASS_REGISTRATE = CreateRegistrate.lazy(ID);

    public Create_Brass_Coated(){
        onCtor();
    }
    public static void onCtor() {
        ModLoadingContext modLoadingContext = ModLoadingContext.get();

        IEventBus modEventBus = FMLJavaModLoadingContext.get()
                .getModEventBus();
        IEventBus forgeEventBus = MinecraftForge.EVENT_BUS;

        BrassBlocks.register();
        BrassTiles.register();
        BrassItems.register();
        modEventBus.addListener(Create_Brass_Coated::init);}

    public static void init(final FMLCommonSetupEvent event) {
        BrassPackets.registerPackets();
        BrassPartials.init();
        BrassPonder.register();
        BrassPonder.registerTags();
    }


    public static ResourceLocation asResource(String path) {
        return new ResourceLocation(ID, path);
    }
    public static CreateRegistrate registrate() {
        return BRASS_REGISTRATE.get();
    }
}
