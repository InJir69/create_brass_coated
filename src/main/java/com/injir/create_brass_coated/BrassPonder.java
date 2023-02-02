package com.injir.create_brass_coated;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.injir.create_brass_coated.items.BrassItems;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllItems;
import com.simibubi.create.Create;
import com.simibubi.create.foundation.config.AllConfigs;
import com.simibubi.create.foundation.ponder.PonderRegistrationHelper;
import com.simibubi.create.foundation.ponder.PonderRegistry;
import com.simibubi.create.foundation.ponder.PonderTag;
import com.simibubi.create.foundation.ponder.content.*;
import com.simibubi.create.foundation.ponder.content.fluid.*;
import com.simibubi.create.foundation.ponder.content.trains.*;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.Blocks;

public class BrassPonder extends PonderIndex {

	static final PonderRegistrationHelper HELPER = new PonderRegistrationHelper(Create_Brass_Coated.ID);

	public static final boolean REGISTER_DEBUG_SCENES = false;

	public static void register() {

		HELPER.forComponents(BrassBlocks.BRASS_GEARBOX, BrassItems.VERTICAL_BRASS_GEARBOX)
			.addStoryBoard("gearbox", KineticsScenes::gearbox, PonderTag.KINETIC_RELAYS);

		HELPER.addStoryBoard(BrassBlocks.BRASS_CLUTCH, "clutch", KineticsScenes::clutch, PonderTag.KINETIC_RELAYS);
		HELPER.addStoryBoard(BrassBlocks.BRASS_GEARSHIFT, "gearshift", KineticsScenes::gearshift, PonderTag.KINETIC_RELAYS);

		HELPER.forComponents(BrassBlocks.BRASS_ENCASED_FAN)
			.addStoryBoard("fan/direction", FanScenes::direction, PonderTag.KINETIC_APPLIANCES)
			.addStoryBoard("fan/processing", FanScenes::processing);

		HELPER.addStoryBoard(BrassBlocks.BRASS_ENCASED_CHAIN_DRIVE, "chain_drive/relay", ChainDriveScenes::chainDriveAsRelay,
			PonderTag.KINETIC_RELAYS);
		HELPER.forComponents(BrassBlocks.BRASS_ENCASED_CHAIN_DRIVE, BrassBlocks.BRASS_ADJUSTABLE_CHAIN_GEARSHIFT)
			.addStoryBoard("chain_drive/gearshift", ChainDriveScenes::adjustableChainGearshift);

		HELPER.addStoryBoard(BrassBlocks.BRASS_MECHANICAL_MIXER, "mechanical_mixer/mixing", ProcessingScenes::mixing);
		HELPER.forComponents(BrassBlocks.BRASS_MECHANICAL_PRESS)
			.addStoryBoard("mechanical_press/pressing", ProcessingScenes::pressing)
			.addStoryBoard("mechanical_press/compacting", ProcessingScenes::compacting);
		//HELPER.forComponents(BrassBlocks.BRASS_BASIN)
			//.addStoryBoard("basin", ProcessingScenes::basin)
			//.addStoryBoard("mechanical_mixer/mixing", ProcessingScenes::mixing)
			//.addStoryBoard("mechanical_press/compacting", ProcessingScenes::compacting);
		HELPER.addStoryBoard(BrassBlocks.BRASS_DEPOT, "depot", BeltScenes::depot);
		HELPER.forComponents(BrassBlocks.BRASS_WEIGHTED_EJECTOR)
			.addStoryBoard("weighted_ejector/eject", EjectorScenes::ejector)
			.addStoryBoard("weighted_ejector/split", EjectorScenes::splitY)
			.addStoryBoard("weighted_ejector/redstone", EjectorScenes::redstone);

		HELPER.forComponents(BrassBlocks.BRASS_MECHANICAL_SAW)
			.addStoryBoard("mechanical_saw/processing", MechanicalSawScenes::processing, PonderTag.KINETIC_APPLIANCES)
			.addStoryBoard("mechanical_saw/breaker", MechanicalSawScenes::treeCutting)
			.addStoryBoard("mechanical_saw/contraption", MechanicalSawScenes::contraption, PonderTag.CONTRAPTION_ACTOR);
		HELPER.forComponents(BrassBlocks.BRASS_MECHANICAL_DRILL)
			.addStoryBoard("mechanical_drill/breaker", MechanicalDrillScenes::breaker, PonderTag.KINETIC_APPLIANCES)
			.addStoryBoard("mechanical_drill/contraption", MechanicalDrillScenes::contraption,
				PonderTag.CONTRAPTION_ACTOR);
		HELPER.forComponents(BrassBlocks.BRASS_DEPLOYER)
			.addStoryBoard("deployer/filter", DeployerScenes::filter, PonderTag.KINETIC_APPLIANCES)
			.addStoryBoard("deployer/modes", DeployerScenes::modes)
			.addStoryBoard("deployer/processing", DeployerScenes::processing)
			.addStoryBoard("deployer/redstone", DeployerScenes::redstone)
			.addStoryBoard("deployer/contraption", DeployerScenes::contraption, PonderTag.CONTRAPTION_ACTOR);
		HELPER.forComponents(BrassBlocks.BRASS_MECHANICAL_HARVESTER)
			.addStoryBoard("harvester", MovementActorScenes::harvester);
		HELPER.forComponents(BrassBlocks.BRASS_MECHANICAL_PLOUGH)
			.addStoryBoard("plough", MovementActorScenes::plough);
		HELPER.forComponents(BrassBlocks.BRASS_PORTABLE_STORAGE_INTERFACE)
				.addStoryBoard("portable_interface/transfer", MovementActorScenes::psiTransfer, PonderTag.CONTRAPTION_ACTOR)
				.addStoryBoard("portable_interface/redstone", MovementActorScenes::psiRedstone);


		// Debug scenes, can be found in game via the Brass Hand
		if (REGISTER_DEBUG_SCENES)
			DebugScenes.registerAll();
	}

	public static boolean editingModeActive() {
		return AllConfigs.CLIENT.editingMode.get();
	}

	public static void registerTags() {

		PonderRegistry.TAGS.forTag(PonderTag.KINETIC_RELAYS)
			.add(BrassBlocks.BRASS_GEARBOX)
			.add(BrassBlocks.BRASS_CLUTCH)
			.add(BrassBlocks.BRASS_GEARSHIFT)
			.add(BrassBlocks.BRASS_ENCASED_CHAIN_DRIVE)
			.add(BrassBlocks.BRASS_ADJUSTABLE_CHAIN_GEARSHIFT);

		PonderRegistry.TAGS.forTag(PonderTag.KINETIC_APPLIANCES)
			.add(BrassBlocks.BRASS_ENCASED_FAN)
			.add(BrassBlocks.BRASS_MECHANICAL_PRESS)
			.add(BrassBlocks.BRASS_MECHANICAL_MIXER)
			.add(BrassBlocks.BRASS_MECHANICAL_DRILL)
			.add(BrassBlocks.BRASS_MECHANICAL_SAW)
			.add(BrassBlocks.BRASS_DEPLOYER);

		PonderRegistry.TAGS.forTag(PonderTag.ARM_TARGETS)
			.add(BrassBlocks.BRASS_DEPOT)
			.add(BrassBlocks.BRASS_WEIGHTED_EJECTOR)
			//.add(BrassBlocks.BRASS_BASIN)
			.add(BrassBlocks.BRASS_DEPLOYER)
			.add(BrassBlocks.BRASS_MECHANICAL_SAW)
			.add(AllBlocks.BLAZE_BURNER);

		PonderRegistry.TAGS.forTag(PonderTag.LOGISTICS)
			.add(BrassBlocks.BRASS_DEPOT)
			.add(BrassBlocks.BRASS_WEIGHTED_EJECTOR)
			.add(AllBlocks.PORTABLE_STORAGE_INTERFACE);

		PonderRegistry.TAGS.forTag(PonderTag.DECORATION)
			.add(BrassBlocks.BRASS_GIRDER);

		PonderRegistry.TAGS.forTag(PonderTag.CONTRAPTION_ACTOR)
			.add(BrassBlocks.BRASS_MECHANICAL_HARVESTER)
			.add(BrassBlocks.BRASS_MECHANICAL_PLOUGH)
			.add(BrassBlocks.BRASS_MECHANICAL_DRILL)
			.add(BrassBlocks.BRASS_MECHANICAL_SAW)
			.add(AllBlocks.PORTABLE_STORAGE_INTERFACE);

	}

}
