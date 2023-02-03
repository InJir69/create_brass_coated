package com.injir.create_brass_coated;

import com.injir.create_brass_coated.blocks.depot.BrassEjectorTargetHandler;
import com.injir.create_brass_coated.blocks.girder.BrassGirderWrenchBehavior;
import com.simibubi.create.Create;
import com.simibubi.create.CreateClient;
import com.simibubi.create.content.contraptions.components.fan.AirCurrent;
import com.simibubi.create.content.contraptions.components.structureMovement.interaction.controls.ControlsHandler;
import com.simibubi.create.content.contraptions.components.turntable.TurntableHandler;
import com.simibubi.create.content.curiosities.armor.CopperBacktankArmorLayer;
import com.simibubi.create.content.curiosities.girder.GirderWrenchBehavior;
import com.simibubi.create.content.logistics.block.depot.EjectorTargetHandler;
import com.simibubi.create.content.logistics.item.LinkedControllerClientHandler;
import com.simibubi.create.content.logistics.trains.management.schedule.TrainHatArmorLayer;
import com.simibubi.create.foundation.config.ui.BaseConfigScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ConfigGuiHandler;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.client.event.RegisterClientReloadListenersEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;


@Mod.EventBusSubscriber(Dist.CLIENT)
public class BrassClientEvents {


    private static final String ITEM_PREFIX = "item." + Create_Brass_Coated.ID;
    private static final String BLOCK_PREFIX = "block." + Create_Brass_Coated.ID;

    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent event) {
        if (!isGameActive())
            return;

        Level world = Minecraft.getInstance().level;
        if (event.phase == TickEvent.Phase.START) {
            BrassGirderWrenchBehavior.tick();
            return;
        }
        BrassEjectorTargetHandler.tick();


    }
    protected static boolean isGameActive() {
        return !(Minecraft.getInstance().level == null || Minecraft.getInstance().player == null);
    }

    @SubscribeEvent
    public static void onRenderTick(TickEvent.RenderTickEvent event) {
        if (!isGameActive())
            return;
        TurntableHandler.gameRenderTick();
    }


}
