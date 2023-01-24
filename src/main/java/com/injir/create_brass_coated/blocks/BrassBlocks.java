package com.injir.create_brass_coated.blocks;

import com.injir.create_brass_coated.BrassTab;
import com.injir.create_brass_coated.Create_Brass_Coated;
import com.injir.create_brass_coated.blocks.basin.BrassBasinBlock;
import com.injir.create_brass_coated.blocks.basin.BrassBasinGenerator;
import com.injir.create_brass_coated.blocks.basin.BrassBasinMovementBehaviour;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerBlock;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerMovementBehaviour;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerMovingInteraction;
import com.injir.create_brass_coated.blocks.depot.BrassDepotBlock;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorBlock;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorItem;
import com.injir.create_brass_coated.blocks.fan.BrassEncasedFanBlock;
import com.injir.create_brass_coated.blocks.fan.BrassNozzleBlock;
import com.injir.create_brass_coated.blocks.girder.BrassConnectedGirderModel;
import com.injir.create_brass_coated.blocks.girder.BrassGirderBlock;
import com.injir.create_brass_coated.blocks.girder.BrassGirderBlockStateGenerator;
import com.injir.create_brass_coated.blocks.girder.BrassGirderEncasedShaftBlock;
import com.injir.create_brass_coated.blocks.mixer.BrassMechanicalMixerBlock;
import com.injir.create_brass_coated.blocks.other.BrassClutchBlock;
import com.injir.create_brass_coated.blocks.drill.BrassDrillBlock;
import com.injir.create_brass_coated.blocks.drill.BrassDrillMovementBehaviour;
import com.injir.create_brass_coated.blocks.gearbox.BrassGearboxBlock;
import com.injir.create_brass_coated.blocks.harvester.BrassHarvesterBlock;
import com.injir.create_brass_coated.blocks.harvester.BrassHarvesterMovementBehaviour;
import com.injir.create_brass_coated.blocks.other.BrassEncasedBeltBlock;
import com.injir.create_brass_coated.blocks.other.BrassEncasedBeltGenerator;
import com.injir.create_brass_coated.blocks.other.BrassGearshiftBlock;
import com.injir.create_brass_coated.blocks.plough.BrassPloughBlock;
import com.injir.create_brass_coated.blocks.plough.BrassPloughMovementBehaviour;
import com.injir.create_brass_coated.blocks.press.BrassMechanicalPressBlock;
import com.injir.create_brass_coated.blocks.saw.BrassSawBlock;
import com.injir.create_brass_coated.blocks.saw.BrassSawGenerator;
import com.injir.create_brass_coated.blocks.saw.BrassSawMovementBehaviour;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.Create;
import com.simibubi.create.content.AllSections;
import com.simibubi.create.content.contraptions.components.AssemblyOperatorBlockItem;
import com.simibubi.create.content.contraptions.components.actors.PloughBlock;
import com.simibubi.create.content.contraptions.components.actors.PloughMovementBehaviour;
import com.simibubi.create.content.contraptions.components.deployer.DeployerBlock;
import com.simibubi.create.content.contraptions.components.deployer.DeployerMovementBehaviour;
import com.simibubi.create.content.contraptions.components.deployer.DeployerMovingInteraction;
import com.simibubi.create.content.contraptions.components.fan.EncasedFanBlock;
import com.simibubi.create.content.contraptions.components.fan.NozzleBlock;
import com.simibubi.create.content.contraptions.components.mixer.MechanicalMixerBlock;
import com.simibubi.create.content.contraptions.components.press.MechanicalPressBlock;
import com.simibubi.create.content.contraptions.components.structureMovement.piston.MechanicalPistonBlock;
import com.simibubi.create.content.contraptions.fluids.pipes.BracketBlock;
import com.simibubi.create.content.contraptions.fluids.pipes.BracketBlockItem;
import com.simibubi.create.content.contraptions.fluids.pipes.BracketGenerator;
import com.simibubi.create.content.contraptions.processing.BasinBlock;
import com.simibubi.create.content.contraptions.processing.BasinGenerator;
import com.simibubi.create.content.contraptions.processing.BasinMovementBehaviour;
import com.simibubi.create.content.contraptions.relays.encased.EncasedBeltBlock;
import com.simibubi.create.content.contraptions.relays.encased.EncasedBeltGenerator;
import com.simibubi.create.content.contraptions.relays.encased.EncasedCTBehaviour;
import com.simibubi.create.content.contraptions.relays.encased.GearshiftBlock;
import com.simibubi.create.content.curiosities.girder.ConnectedGirderModel;
import com.simibubi.create.content.curiosities.girder.GirderBlock;
import com.simibubi.create.content.curiosities.girder.GirderBlockStateGenerator;
import com.simibubi.create.content.curiosities.girder.GirderEncasedShaftBlock;
import com.simibubi.create.content.logistics.block.depot.DepotBlock;
import com.simibubi.create.content.logistics.block.depot.EjectorBlock;
import com.simibubi.create.content.logistics.block.depot.EjectorItem;
import com.simibubi.create.content.logistics.block.display.source.ItemNameDisplaySource;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.PistonType;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.AllTags.axeOrPickaxe;
import static com.simibubi.create.Create.REGISTRATE;
import static com.simibubi.create.content.logistics.block.display.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class BrassBlocks {
	private static final CreateRegistrate REGISTRATE = Create_Brass_Coated.registrate();

	static {
		CreateRegistrate REGISTRATE = Create_Brass_Coated.registrate().creativeModeTab(() -> BrassTab.BRASS_TAB);
	}

	public static final BlockEntry<BrassGearboxBlock> BRASS_GEARBOX = REGISTRATE.block("brass_gearbox", BrassGearboxBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(BlockStressDefaults.setNoImpact())
			.transform(TagGen.axeOrPickaxe())
			.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.BRASS_CASING)))
			.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, AllSpriteShifts.BRASS_CASING,
					(s, f) -> f.getAxis() == s.getValue(BrassGearboxBlock.AXIS))))
			.blockstate((c, p) -> axisBlock(c, p, $ -> AssetLookup.partialBaseModel(c, p), true))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassClutchBlock> BRASS_CLUTCH = REGISTRATE.block("brass_clutch", BrassClutchBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(BlockStressDefaults.setNoImpact())
			.transform(TagGen.axeOrPickaxe())
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassGearshiftBlock> BRASS_GEARSHIFT = REGISTRATE.block("brass_gearshift", BrassGearshiftBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(BlockStressDefaults.setNoImpact())
			.transform(TagGen.axeOrPickaxe())
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassEncasedBeltBlock> BRASS_ENCASED_CHAIN_DRIVE =
			REGISTRATE.block("brass_encased_chain_drive", BrassEncasedBeltBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(BlockBehaviour.Properties::noOcclusion)
					.properties(p -> p.color(MaterialColor.PODZOL))
					.transform(BlockStressDefaults.setNoImpact())
					.transform(TagGen.axeOrPickaxe())
					.blockstate((c, p) -> new BrassEncasedBeltGenerator((state, suffix) -> p.models()
							.getExistingFile(p.modLoc("block/" + c.getName() + "/" + suffix))).generate(c, p))
					.item()
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassEncasedFanBlock> BRASS_ENCASED_FAN = REGISTRATE.block("brass_encased_fan", BrassEncasedFanBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(TagGen.axeOrPickaxe())
			.transform(BlockStressDefaults.setImpact(2.0))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassNozzleBlock> BRASS_NOZZLE = REGISTRATE.block("brass_nozzle", BrassNozzleBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.COLOR_LIGHT_GRAY))
			.tag(AllTags.AllBlockTags.BRITTLE.tag)
			.transform(TagGen.axeOrPickaxe())
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.addLayer(() -> RenderType::cutoutMipped)
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassDepotBlock> BRASS_DEPOT = REGISTRATE.block("brass_depot", BrassDepotBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.COLOR_GRAY))
			.transform(TagGen.axeOrPickaxe())
			.blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
			.onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
			.item()
			.transform(customItemModel("_", "block"))
			.register();

	public static final BlockEntry<BrassEjectorBlock> BRASS_WEIGHTED_EJECTOR =
			REGISTRATE.block("brass_weighted_ejector", BrassEjectorBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.COLOR_GRAY))
					.properties(BlockBehaviour.Properties::noOcclusion)
					.transform(TagGen.axeOrPickaxe())
					.blockstate((c, p) -> p.horizontalBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p), 180))
					.transform(BlockStressDefaults.setImpact(2.0))
					.onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
					.item(BrassEjectorItem::new)
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassMechanicalPressBlock> BRASS_MECHANICAL_PRESS =
			REGISTRATE.block("brass_mechanical_press", BrassMechanicalPressBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.PODZOL))
					.properties(BlockBehaviour.Properties::noOcclusion)
					.transform(TagGen.axeOrPickaxe())
					.blockstate(BlockStateGen.horizontalBlockProvider(true))
					.transform(BlockStressDefaults.setImpact(8.0))
					.item(AssemblyOperatorBlockItem::new)
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassMechanicalMixerBlock> BRASS_MECHANICAL_MIXER =
			REGISTRATE.block("brass_mechanical_mixer", BrassMechanicalMixerBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.STONE))
					.properties(BlockBehaviour.Properties::noOcclusion)
					.transform(TagGen.axeOrPickaxe())
					.blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
					.addLayer(() -> RenderType::cutoutMipped)
					.transform(BlockStressDefaults.setImpact(4.0))
					.item(AssemblyOperatorBlockItem::new)
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassBasinBlock> BRASS_BASIN = REGISTRATE.block("brass_basin", BrassBasinBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.blockstate(new BrassBasinGenerator()::generate)
			.onRegister(movementBehaviour(new BrassBasinMovementBehaviour()))
			.item()
			.transform(customItemModel("_", "block"))
			.register();

	public static final BlockEntry<BrassGirderBlock> BRASS_GIRDER = REGISTRATE.block("brass_girder", BrassGirderBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.blockstate(BrassGirderBlockStateGenerator::blockState)
			.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.onRegister(CreateRegistrate.blockModel(() -> BrassConnectedGirderModel::new))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassGirderEncasedShaftBlock> BRASS_GIRDER_ENCASED_SHAFT =
			REGISTRATE.block("brass_girder_encased_shaft", BrassGirderEncasedShaftBlock::new)
					.initialProperties(SharedProperties::softMetal)
					.blockstate(BrassGirderBlockStateGenerator::blockStateWithShaft)
					.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
					.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
					.transform(pickaxeOnly())
					.loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(BRASS_GIRDER.get())
							.withPool(RegistrateBlockLootTables.applyExplosionCondition(AllBlocks.SHAFT.get(), LootPool.lootPool()
									.setRolls(ConstantValue.exactly(1.0F))
									.add(LootItem.lootTableItem(AllBlocks.SHAFT.get()))))))
					.onRegister(CreateRegistrate.blockModel(() -> BrassConnectedGirderModel::new))
					.register();

	public static final BlockEntry<BrassDrillBlock> BRASS_MECHANICAL_DRILL = REGISTRATE.block("brass_mechanical_drill", BrassDrillBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(axeOrPickaxe())
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.transform(BlockStressDefaults.setImpact(4.0))
			.onRegister(movementBehaviour(new BrassDrillMovementBehaviour()))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassHarvesterBlock> BRASS_MECHANICAL_HARVESTER =
			REGISTRATE.block("brass_mechanical_harvester", BrassHarvesterBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.METAL))
					.transform(TagGen.axeOrPickaxe())
					.onRegister(movementBehaviour(new BrassHarvesterMovementBehaviour()))
					.blockstate(BlockStateGen.horizontalBlockProvider(true))
					.addLayer(() -> RenderType::cutoutMipped)
					.item()
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassPloughBlock> BRASS_MECHANICAL_PLOUGH =
			REGISTRATE.block("brass_mechanical_plough", BrassPloughBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
					.transform(TagGen.axeOrPickaxe())
					.onRegister(movementBehaviour(new BrassPloughMovementBehaviour()))
					.blockstate(BlockStateGen.horizontalBlockProvider(false))
					.simpleItem()
					.register();

	public static final BlockEntry<BrassDeployerBlock> BRASS_DEPLOYER = REGISTRATE.block("brass_deployer", BrassDeployerBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(TagGen.axeOrPickaxe())
			.blockstate(BlockStateGen.directionalAxisBlockProvider())
			.transform(BlockStressDefaults.setImpact(4.0))
			.onRegister(movementBehaviour(new BrassDeployerMovementBehaviour()))
			.onRegister(interactionBehaviour(new BrassDeployerMovingInteraction()))
			.item(AssemblyOperatorBlockItem::new)
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassSawBlock> BRASS_MECHANICAL_SAW = REGISTRATE.block("brass_mechanical_saw", BrassSawBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(TagGen.axeOrPickaxe())
			.blockstate(new BrassSawGenerator()::generate)
			.transform(BlockStressDefaults.setImpact(4.0))
			.onRegister(movementBehaviour(new BrassSawMovementBehaviour()))
			.addLayer(() -> RenderType::cutoutMipped)
			.item()
			.transform(customItemModel())
			.register();
	public static void register() {}
}
