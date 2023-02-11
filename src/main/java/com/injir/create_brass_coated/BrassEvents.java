package com.injir.create_brass_coated;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.ModFilePackResources;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.server.packs.repository.PackSource;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forgespi.language.IModFileInfo;
import net.minecraftforge.forgespi.locating.IModFile;

@Mod.EventBusSubscriber
public class BrassEvents {
    @Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ModBusEvents {
        @SubscribeEvent
        public static void addPackFinders(AddPackFindersEvent event) {
            if (event.getPackType() == PackType.CLIENT_RESOURCES) {
                IModFileInfo modFileInfo = ModList.get().getModFileById(Create_Brass_Coated.ID);
                if (modFileInfo == null) {
                    Create.LOGGER.error("Could not find Create mod file info; built-in resource packs will be missing!");
                    return;
                }
                IModFile modFile = modFileInfo.getFile();
                event.addRepositorySource((consumer, constructor) -> {
                    consumer.accept(Pack.create(Create_Brass_Coated.asResource("brass_shaft").toString(), false, () -> new ModFilePackResources("Create Brass Shaft", modFile, "resourcepacks/brass_shaft"), constructor, Pack.Position.TOP, PackSource.DEFAULT));
                });
            }

        }
    }
}