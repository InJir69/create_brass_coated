package com.injir.create_brass_coated.blocks;

import com.injir.create_brass_coated.Create_Brass_Coated;
import com.injir.create_brass_coated.blocks.chute.BrassChuteRenderer;
import com.injir.create_brass_coated.blocks.chute.BrassChuteTileEntity;
import com.injir.create_brass_coated.blocks.chute.BrassSmartChuteRenderer;
import com.injir.create_brass_coated.blocks.chute.BrassSmartChuteTileEntity;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerInstance;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerRenderer;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerTileEntity;
import com.injir.create_brass_coated.blocks.depot.*;
import com.injir.create_brass_coated.blocks.fan.BrassEncasedFanRenderer;
import com.injir.create_brass_coated.blocks.fan.BrassEncasedFanTileEntity;
import com.injir.create_brass_coated.blocks.fan.BrassFanInstance;
import com.injir.create_brass_coated.blocks.fan.BrassNozzleTileEntity;
import com.injir.create_brass_coated.blocks.mixer.BrassMechanicalMixerRenderer;
import com.injir.create_brass_coated.blocks.mixer.BrassMechanicalMixerTileEntity;
import com.injir.create_brass_coated.blocks.mixer.BrassMixerInstance;
import com.injir.create_brass_coated.blocks.other.*;
import com.injir.create_brass_coated.blocks.drill.BrassDrillInstance;
import com.injir.create_brass_coated.blocks.drill.BrassDrillRenderer;
import com.injir.create_brass_coated.blocks.drill.BrassDrillTileEntity;
import com.injir.create_brass_coated.blocks.harvester.BrassHarvesterRenderer;
import com.injir.create_brass_coated.blocks.harvester.BrassHarvesterTileEntity;
import com.injir.create_brass_coated.blocks.portable_storage.BrassPSIInstance;
import com.injir.create_brass_coated.blocks.portable_storage.BrassPortableItemInterfaceTileEntity;
import com.injir.create_brass_coated.blocks.portable_storage.BrassPortableStorageInterfaceRenderer;
import com.injir.create_brass_coated.blocks.press.BrassMechanicalPressRenderer;
import com.injir.create_brass_coated.blocks.press.BrassMechanicalPressTileEntity;
import com.injir.create_brass_coated.blocks.press.BrassPressInstance;
import com.injir.create_brass_coated.blocks.saw.BrassSawInstance;
import com.injir.create_brass_coated.blocks.saw.BrassSawRenderer;
import com.injir.create_brass_coated.blocks.saw.BrassSawTileEntity;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.actors.PSIInstance;
import com.simibubi.create.content.contraptions.components.actors.PortableItemInterfaceTileEntity;
import com.simibubi.create.content.contraptions.components.actors.PortableStorageInterfaceRenderer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerInstance;
import com.simibubi.create.content.contraptions.components.deployer.DeployerRenderer;
import com.simibubi.create.content.contraptions.components.deployer.DeployerTileEntity;
import com.simibubi.create.content.contraptions.components.fan.EncasedFanRenderer;
import com.simibubi.create.content.contraptions.components.fan.EncasedFanTileEntity;
import com.simibubi.create.content.contraptions.components.fan.FanInstance;
import com.simibubi.create.content.contraptions.components.fan.NozzleTileEntity;
import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerRenderer;
import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerTileEntity;
import com.simibubi.create.content.contraptions.components.mixer.MixerInstance;
import com.simibubi.create.content.contraptions.components.press.MechanicalPressRenderer;
import com.simibubi.create.content.contraptions.components.press.MechanicalPressTileEntity;
import com.simibubi.create.content.contraptions.components.press.PressInstance;
import com.simibubi.create.content.contraptions.processing.BasinRenderer;
import com.simibubi.create.content.contraptions.processing.BasinTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileEntity;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileInstance;
import com.simibubi.create.content.contraptions.relays.elementary.BracketedKineticTileRenderer;
import com.simibubi.create.content.contraptions.relays.encased.*;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxInstance;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxRenderer;
import com.simibubi.create.content.contraptions.relays.gearbox.GearboxTileEntity;
import com.simibubi.create.content.contraptions.relays.gearbox.GearshiftTileEntity;
import com.simibubi.create.content.logistics.block.depot.*;
import com.tterrag.registrate.util.entry.BlockEntityEntry;

import static com.simibubi.create.Create.REGISTRATE;

public class BrassTiles {
    public static final BlockEntityEntry<BrassDrillTileEntity> BRASS_DRILL = REGISTRATE
            .tileEntity("brass_drill", BrassDrillTileEntity::new)
            .instance(() -> BrassDrillInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_MECHANICAL_DRILL)
            .renderer(() -> BrassDrillRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassHarvesterTileEntity> BRASS_HARVESTER = REGISTRATE
            .tileEntity("brass_harvester", BrassHarvesterTileEntity::new)
            .validBlocks(BrassBlocks.BRASS_MECHANICAL_HARVESTER)
            .renderer(() -> BrassHarvesterRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassDeployerTileEntity> BRASS_DEPLOYER = REGISTRATE
            .tileEntity("brass_deployer", BrassDeployerTileEntity::new)
            .instance(() -> BrassDeployerInstance::new)
            .validBlocks(BrassBlocks.BRASS_DEPLOYER)
            .renderer(() -> BrassDeployerRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassSawTileEntity> BRASS_SAW = REGISTRATE
            .tileEntity("brass_saw", BrassSawTileEntity::new)
            .instance(() -> BrassSawInstance::new)
            .validBlocks(BrassBlocks.BRASS_MECHANICAL_SAW)
            .renderer(() -> BrassSawRenderer::new)
            .register();

    public static final BlockEntityEntry<GearboxTileEntity> BRASS_GEARBOX = REGISTRATE
            .tileEntity("brass_gearbox", GearboxTileEntity::new)
            .instance(() -> GearboxInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_GEARBOX)
            .renderer(() -> GearboxRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassClutchTileEntity> BRASS_CLUTCH = REGISTRATE
            .tileEntity("brass_clutch", BrassClutchTileEntity::new)
            .instance(() -> BrassSplitShaftInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_CLUTCH)
            .renderer(() -> BrassSplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassGearshiftTileEntity> BRASS_GEARSHIFT = REGISTRATE
            .tileEntity("brass_gearshift", BrassGearshiftTileEntity::new)
            .instance(() -> BrassSplitShaftInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_GEARSHIFT)
            .renderer(() -> BrassSplitShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<KineticTileEntity> BRASS_ENCASED_CHAIN_DRIVE = REGISTRATE
            .tileEntity("brass_encased_chain_drive", KineticTileEntity::new)
            .instance(() -> BrassShaftInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_ENCASED_CHAIN_DRIVE, BrassBlocks.BRASS_GIRDER_ENCASED_SHAFT, BrassBlocks.COPPER_GIRDER_ENCASED_SHAFT)
            .renderer(() -> BrassShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassEncasedFanTileEntity> BRASS_ENCASED_FAN = REGISTRATE
            .tileEntity("brass_encased_fan", BrassEncasedFanTileEntity::new)
            .instance(() -> BrassFanInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_ENCASED_FAN)
            .renderer(() -> BrassEncasedFanRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassNozzleTileEntity> BRASS_NOZZLE = REGISTRATE
            .tileEntity("brass_nozzle", BrassNozzleTileEntity::new)
            .validBlocks(BrassBlocks.BRASS_NOZZLE)
            // .renderer(() -> renderer)
            .register();

    public static final BlockEntityEntry<BrassMechanicalPressTileEntity> BRASS_MECHANICAL_PRESS = REGISTRATE
            .tileEntity("brass_mechanical_press", BrassMechanicalPressTileEntity::new)
            .instance(() -> BrassPressInstance::new)
            .validBlocks(BrassBlocks.BRASS_MECHANICAL_PRESS)
            .renderer(() -> BrassMechanicalPressRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassMechanicalMixerTileEntity> BRASS_MECHANICAL_MIXER = REGISTRATE
            .tileEntity("brass_mechanical_mixer", BrassMechanicalMixerTileEntity::new)
            .instance(() -> BrassMixerInstance::new)
            .validBlocks(BrassBlocks.BRASS_MECHANICAL_MIXER)
            .renderer(() -> BrassMechanicalMixerRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassDepotTileEntity> BRASS_DEPOT = REGISTRATE
            .tileEntity("brass_depot", BrassDepotTileEntity::new)
            .validBlocks(BrassBlocks.BRASS_DEPOT)
            .renderer(() -> BrassDepotRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassEjectorTileEntity> BRASS_WEIGHTED_EJECTOR = REGISTRATE
            .tileEntity("brass_weighted_ejector", BrassEjectorTileEntity::new)
            .instance(() -> BrassEjectorInstance::new)
            .validBlocks(BrassBlocks.BRASS_WEIGHTED_EJECTOR)
            .renderer(() -> BrassEjectorRenderer::new)
            .register();

    //public static final BlockEntityEntry<BrassBasinTileEntity> BRASS_BASIN = REGISTRATE
            //.tileEntity("brass_basin", BrassBasinTileEntity::new)
            //.validBlocks(BrassBlocks.BRASS_BASIN)
            //.renderer(() -> BrassBasinRenderer::new)
            //.register();

    public static final BlockEntityEntry<BrassAdjustablePulleyTileEntity> ADJUSTABLE_PULLEY = REGISTRATE
            .tileEntity("brass_adjustable_pulley", BrassAdjustablePulleyTileEntity::new)
            .instance(() -> BrassShaftInstance::new, false)
            .validBlocks(BrassBlocks.BRASS_ADJUSTABLE_CHAIN_GEARSHIFT)
            .renderer(() -> BrassShaftRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassPortableItemInterfaceTileEntity> PORTABLE_STORAGE_INTERFACE =
            REGISTRATE
                    .tileEntity("brass_portable_storage_interface", BrassPortableItemInterfaceTileEntity::new)
                    .instance(() -> BrassPSIInstance::new)
                    .validBlocks(BrassBlocks.BRASS_PORTABLE_STORAGE_INTERFACE)
                    .renderer(() -> BrassPortableStorageInterfaceRenderer::new)
                    .register();

    public static final BlockEntityEntry<BrassChuteTileEntity> BRASS_CHUTE = REGISTRATE
            .tileEntity("brass_chute", BrassChuteTileEntity::new)
            .validBlocks(BrassBlocks.BRASS_CHUTE)
            .renderer(() -> BrassChuteRenderer::new)
            .register();

    public static final BlockEntityEntry<BrassSmartChuteTileEntity> SMART_BRASS_CHUTE = REGISTRATE
            .tileEntity("smart_brass_chute", BrassSmartChuteTileEntity::new)
            .validBlocks(BrassBlocks.SMART_BRASS_CHUTE)
            .renderer(() -> BrassSmartChuteRenderer::new)
            .register();




    public static void register() {}
}
