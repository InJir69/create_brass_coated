package com.injir.create_brass_coated.blocks;

import com.injir.create_brass_coated.BrassTab;
import com.injir.create_brass_coated.Create_Brass_Coated;
import com.injir.create_brass_coated.blocks.bracket.BrassBracketBlock;
import com.injir.create_brass_coated.blocks.bracket.BrassBracketBlockItem;
import com.injir.create_brass_coated.blocks.bracket.BrassBracketGenerator;
import com.injir.create_brass_coated.blocks.chute.BrassChuteBlock;
import com.injir.create_brass_coated.blocks.chute.BrassChuteGenerator;
import com.injir.create_brass_coated.blocks.chute.BrassChuteItem;
import com.injir.create_brass_coated.blocks.chute.BrassSmartChuteBlock;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerBlock;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerMovementBehaviour;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerMovingInteraction;
import com.injir.create_brass_coated.blocks.depot.BrassDepotBlock;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorBlock;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorItem;
import com.injir.create_brass_coated.blocks.fan.BrassEncasedFanBlock;
import com.injir.create_brass_coated.blocks.fan.BrassNozzleBlock;
import com.injir.create_brass_coated.blocks.girder.brass.BrassConnectedGirderModel;
import com.injir.create_brass_coated.blocks.girder.brass.BrassGirderBlock;
import com.injir.create_brass_coated.blocks.girder.brass.BrassGirderBlockStateGenerator;
import com.injir.create_brass_coated.blocks.girder.brass.BrassGirderEncasedShaftBlock;
import com.injir.create_brass_coated.blocks.girder.copper.CopperConnectedGirderModel;
import com.injir.create_brass_coated.blocks.girder.copper.CopperGirderBlock;
import com.injir.create_brass_coated.blocks.girder.copper.CopperGirderBlockStateGenerator;
import com.injir.create_brass_coated.blocks.girder.copper.CopperGirderEncasedShaftBlock;
import com.injir.create_brass_coated.blocks.mixer.BrassMechanicalMixerBlock;
import com.injir.create_brass_coated.blocks.other.*;
import com.injir.create_brass_coated.blocks.drill.BrassDrillBlock;
import com.injir.create_brass_coated.blocks.drill.BrassDrillMovementBehaviour;
import com.injir.create_brass_coated.blocks.gearbox.BrassGearboxBlock;
import com.injir.create_brass_coated.blocks.harvester.BrassHarvesterBlock;
import com.injir.create_brass_coated.blocks.harvester.BrassHarvesterMovementBehaviour;
import com.injir.create_brass_coated.blocks.pipe.*;
import com.injir.create_brass_coated.blocks.plough.BrassPloughBlock;
import com.injir.create_brass_coated.blocks.plough.BrassPloughMovementBehaviour;
import com.injir.create_brass_coated.blocks.portable_storage.BrassPortableStorageInterfaceBlock;
import com.injir.create_brass_coated.blocks.portable_storage.BrassPortableStorageInterfaceMovement;
import com.injir.create_brass_coated.blocks.press.BrassMechanicalPressBlock;
import com.injir.create_brass_coated.blocks.saw.BrassSawBlock;
import com.injir.create_brass_coated.blocks.saw.BrassSawGenerator;
import com.injir.create_brass_coated.blocks.saw.BrassSawMovementBehaviour;
import com.simibubi.create.AllBlocks;
import com.simibubi.create.AllSpriteShifts;
import com.simibubi.create.AllTags;
import com.simibubi.create.content.contraptions.components.AssemblyOperatorBlockItem;
import com.simibubi.create.content.contraptions.relays.encased.*;
import com.simibubi.create.content.logistics.block.display.source.ItemNameDisplaySource;
import com.simibubi.create.foundation.block.BlockStressDefaults;
import com.simibubi.create.foundation.data.*;
import com.tterrag.registrate.providers.loot.RegistrateBlockLootTables;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraftforge.client.model.generators.ConfiguredModel;

import static com.simibubi.create.AllInteractionBehaviours.interactionBehaviour;
import static com.simibubi.create.AllMovementBehaviours.movementBehaviour;
import static com.simibubi.create.AllTags.axeOrPickaxe;
import static com.simibubi.create.content.logistics.block.display.AllDisplayBehaviours.assignDataBehaviour;
import static com.simibubi.create.foundation.data.BlockStateGen.axisBlock;
import static com.simibubi.create.foundation.data.ModelGen.customItemModel;
import static com.simibubi.create.foundation.data.TagGen.pickaxeOnly;

public class BrassBlocks {
	private static final CreateRegistrate BRASS_REGISTRATE = Create_Brass_Coated.registrate();

	static {
		CreateRegistrate BRASS_REGISTRATE = Create_Brass_Coated.registrate().creativeModeTab(() -> BrassTab.BRASS_TAB);
	}

	public static final BlockEntry<BrassGearboxBlock> BRASS_GEARBOX = BRASS_REGISTRATE.block("brass_gearbox", BrassGearboxBlock::new)
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

	public static final BlockEntry<BrassClutchBlock> BRASS_CLUTCH = BRASS_REGISTRATE.block("brass_clutch", BrassClutchBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(BlockBehaviour.Properties::noOcclusion)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.transform(BlockStressDefaults.setNoImpact())
			.transform(TagGen.axeOrPickaxe())
			.blockstate((c, p) -> BlockStateGen.axisBlock(c, p, AssetLookup.forPowered(c, p)))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassGearshiftBlock> BRASS_GEARSHIFT = BRASS_REGISTRATE.block("brass_gearshift", BrassGearshiftBlock::new)
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
			BRASS_REGISTRATE.block("brass_encased_chain_drive", BrassEncasedBeltBlock::new)
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

	public static final BlockEntry<BrassAdjustablePulleyBlock> BRASS_ADJUSTABLE_CHAIN_GEARSHIFT =
			BRASS_REGISTRATE.block("brass_adjustable_chain_gearshift", BrassAdjustablePulleyBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(BlockBehaviour.Properties::noOcclusion)
					.properties(p -> p.color(MaterialColor.NETHER))
					.transform(BlockStressDefaults.setNoImpact())
					.transform(TagGen.axeOrPickaxe())
					.blockstate((c, p) -> new BrassEncasedBeltGenerator((state, suffix) -> {
						String powered = state.getValue(BrassAdjustablePulleyBlock.POWERED) ? "_powered" : "";
						return p.models()
								.withExistingParent(c.getName() + "_" + suffix + powered,
										p.modLoc("block/brass_encased_chain_drive/" + suffix))
								.texture("side", p.modLoc("block/" + c.getName() + powered));
					}).generate(c, p))
					.item()
					.model((c, p) -> p.withExistingParent(c.getName(), p.modLoc("block/brass_encased_chain_drive/item"))
							.texture("side", p.modLoc("block/" + c.getName())))
					.build()
					.register();

	public static final BlockEntry<BrassEncasedFanBlock> BRASS_ENCASED_FAN = BRASS_REGISTRATE.block("brass_encased_fan", BrassEncasedFanBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.PODZOL))
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.addLayer(() -> RenderType::cutoutMipped)
			.transform(TagGen.axeOrPickaxe())
			.transform(BlockStressDefaults.setImpact(2.0))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassNozzleBlock> BRASS_NOZZLE = BRASS_REGISTRATE.block("brass_nozzle", BrassNozzleBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.COLOR_LIGHT_GRAY))
			.tag(AllTags.AllBlockTags.BRITTLE.tag)
			.transform(TagGen.axeOrPickaxe())
			.blockstate(BlockStateGen.directionalBlockProvider(true))
			.addLayer(() -> RenderType::cutoutMipped)
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassDepotBlock> BRASS_DEPOT = BRASS_REGISTRATE.block("brass_depot", BrassDepotBlock::new)
			.initialProperties(SharedProperties::stone)
			.properties(p -> p.color(MaterialColor.COLOR_GRAY))
			.transform(TagGen.axeOrPickaxe())
			.blockstate((c, p) -> p.simpleBlock(c.getEntry(), AssetLookup.partialBaseModel(c, p)))
			.onRegister(assignDataBehaviour(new ItemNameDisplaySource(), "combine_item_names"))
			.item()
			.transform(customItemModel("_", "block"))
			.register();

	public static final BlockEntry<BrassEjectorBlock> BRASS_WEIGHTED_EJECTOR =
			BRASS_REGISTRATE.block("brass_weighted_ejector", BrassEjectorBlock::new)
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

	public static final BlockEntry<BrassChuteBlock> BRASS_CHUTE = BRASS_REGISTRATE.block("brass_chute", BrassChuteBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.addLayer(() -> RenderType::cutoutMipped)
			.blockstate(new BrassChuteGenerator()::generate)
			.item(BrassChuteItem::new)
			.transform(customItemModel("_", "block"))
			.register();

	public static final BlockEntry<BrassSmartChuteBlock> SMART_BRASS_CHUTE = BRASS_REGISTRATE.block("smart_brass_chute", BrassSmartChuteBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.blockstate((c, p) -> BlockStateGen.simpleBlock(c, p, AssetLookup.forPowered(c, p)))
			.item()
			.transform(customItemModel("_", "block"))
			.register();

	public static final BlockEntry<BrassMechanicalPressBlock> BRASS_MECHANICAL_PRESS =
			BRASS_REGISTRATE.block("brass_mechanical_press", BrassMechanicalPressBlock::new)
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
			BRASS_REGISTRATE.block("brass_mechanical_mixer", BrassMechanicalMixerBlock::new)
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

	//public static final BlockEntry<BrassBasinBlock> BRASS_BASIN = BRASS_REGISTRATE.block("brass_basin", BrassBasinBlock::new)
			//.initialProperties(SharedProperties::stone)
			//.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
			//.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			//.transform(pickaxeOnly())
			//.blockstate(new BrassBasinGenerator()::generate)
			//.onRegister(movementBehaviour(new BrassBasinMovementBehaviour()))
			//.item()
			//.transform(customItemModel("_", "block"))
			//.register();

	public static final BlockEntry<BrassBracketBlock> BRASS_BRACKET = BRASS_REGISTRATE.block("brass_bracket", BrassBracketBlock::new)
			.blockstate(new BrassBracketGenerator("brass")::generate)
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(AllTags.pickaxeOnly())
			.item(BrassBracketBlockItem::new)
			.transform(BrassBracketGenerator.itemModel("brass"))
			.register();

	public static final BlockEntry<BrassBracketBlock> COPPER_BRACKET = BRASS_REGISTRATE.block("copper_bracket", BrassBracketBlock::new)
			.blockstate(new BrassBracketGenerator("copper")::generate)
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(AllTags.pickaxeOnly())
			.item(BrassBracketBlockItem::new)
			.transform(BrassBracketGenerator.itemModel("copper"))
			.register();


	public static final BlockEntry<BrassGirderBlock> BRASS_GIRDER = BRASS_REGISTRATE.block("brass_girder", BrassGirderBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.blockstate(BrassGirderBlockStateGenerator::blockState)
			.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.onRegister(CreateRegistrate.blockModel(() -> BrassConnectedGirderModel::new))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<CopperGirderBlock> COPPER_GIRDER = BRASS_REGISTRATE.block("copper_girder", CopperGirderBlock::new)
			.initialProperties(SharedProperties::softMetal)
			.blockstate(CopperGirderBlockStateGenerator::blockState)
			.properties(p -> p.color(MaterialColor.COLOR_PINK))
			.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
			.transform(pickaxeOnly())
			.onRegister(CreateRegistrate.blockModel(() -> CopperConnectedGirderModel::new))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<BrassGirderEncasedShaftBlock> BRASS_GIRDER_ENCASED_SHAFT =
			BRASS_REGISTRATE.block("brass_girder_encased_shaft", BrassGirderEncasedShaftBlock::new)
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

	public static final BlockEntry<CopperGirderEncasedShaftBlock> COPPER_GIRDER_ENCASED_SHAFT =
			BRASS_REGISTRATE.block("copper_girder_encased_shaft", CopperGirderEncasedShaftBlock::new)
					.initialProperties(SharedProperties::softMetal)
					.blockstate(CopperGirderBlockStateGenerator::blockStateWithShaft)
					.properties(p -> p.color(MaterialColor.COLOR_PINK))
					.properties(p -> p.sound(SoundType.NETHERITE_BLOCK))
					.transform(pickaxeOnly())
					.loot((p, b) -> p.add(b, RegistrateBlockLootTables.createSingleItemTable(COPPER_GIRDER.get())
							.withPool(RegistrateBlockLootTables.applyExplosionCondition(AllBlocks.SHAFT.get(), LootPool.lootPool()
									.setRolls(ConstantValue.exactly(1.0F))
									.add(LootItem.lootTableItem(AllBlocks.SHAFT.get()))))))
					.onRegister(CreateRegistrate.blockModel(() -> CopperConnectedGirderModel::new))
					.register();

	public static final BlockEntry<BrassPipeBlock> BRASS_PIPE = BRASS_REGISTRATE.block("brass_pipe", BrassPipeBlock::new)
			.initialProperties(SharedProperties::copperMetal)
			.transform(pickaxeOnly())
			.blockstate(BrassPipeGen.pipe())
			.onRegister(CreateRegistrate.blockModel(() -> BrassPipeAttachmentModel::new))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<EncasedBrassPipeBlock> ENCASED_BRASS_PIPE =
			BRASS_REGISTRATE.block("encased_brass_pipe", EncasedBrassPipeBlock::new)
					.initialProperties(SharedProperties::copperMetal)
					.properties(p -> p.color(MaterialColor.TERRACOTTA_LIGHT_GRAY))
					.properties(BlockBehaviour.Properties::noOcclusion)
					.transform(TagGen.axeOrPickaxe())
					.blockstate(BrassPipeGen.encasedPipe())
					.onRegister(CreateRegistrate.connectedTextures(() -> new EncasedCTBehaviour(AllSpriteShifts.BRASS_CASING)))
					.onRegister(CreateRegistrate.casingConnectivity((block, cc) -> cc.make(block, AllSpriteShifts.BRASS_CASING,
							(s, f) -> !s.getValue(EncasedBrassPipeBlock.FACING_TO_PROPERTY_MAP.get(f)))))
					.onRegister(CreateRegistrate.blockModel(() -> BrassPipeAttachmentModel::new))
					.loot((p, b) -> p.dropOther(b, BRASS_PIPE.get()))
					.register();

	public static final BlockEntry<GlassBrassPipeBlock> GLASS_BRASS_PIPE =
			BRASS_REGISTRATE.block("glass_brass_pipe", GlassBrassPipeBlock::new)
					.initialProperties(SharedProperties::copperMetal)
					.addLayer(() -> RenderType::cutoutMipped)
					.transform(pickaxeOnly())
					.blockstate((c, p) -> {
						p.getVariantBuilder(c.getEntry())
								.forAllStatesExcept(state -> {
									Direction.Axis axis = state.getValue(BlockStateProperties.AXIS);
									return ConfiguredModel.builder()
											.modelFile(p.models()
													.getExistingFile(p.modLoc("block/brass_pipe/window")))
											.uvLock(false)
											.rotationX(axis == Direction.Axis.Y ? 0 : 90)
											.rotationY(axis == Direction.Axis.X ? 90 : 0)
											.build();
								}, BlockStateProperties.WATERLOGGED);
					})
					.onRegister(CreateRegistrate.blockModel(() -> BrassPipeAttachmentModel::new))
					.loot((p, b) -> p.dropOther(b, BRASS_PIPE.get()))
					.register();

	public static final BlockEntry<BrassPumpBlock> BRASS_MECHANICAL_PUMP = BRASS_REGISTRATE.block("brass_mechanical_pump", BrassPumpBlock::new)
			.initialProperties(SharedProperties::copperMetal)
			.properties(p -> p.color(MaterialColor.STONE))
			.transform(pickaxeOnly())
			.blockstate(BlockStateGen.directionalBlockProviderIgnoresWaterlogged(true))
			.onRegister(CreateRegistrate.blockModel(() -> BrassPipeAttachmentModel::new))
			.transform(BlockStressDefaults.setImpact(4.0))
			.item()
			.transform(customItemModel())
			.register();

	public static final BlockEntry<SmartBrassPipeBlock> SMART_BRASS_PIPE =
			BRASS_REGISTRATE.block("smart_brass_pipe", SmartBrassPipeBlock::new)
					.initialProperties(SharedProperties::copperMetal)
					.properties(p -> p.color(MaterialColor.TERRACOTTA_YELLOW))
					.transform(pickaxeOnly())
					.blockstate(new SmartBrassPipeGenerator()::generate)
					.onRegister(CreateRegistrate.blockModel(() -> BrassPipeAttachmentModel::new))
					.item()
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassValveBlock> BRASS_VALVE = BRASS_REGISTRATE.block("brass_valve", BrassValveBlock::new)
			.initialProperties(SharedProperties::copperMetal)
			.transform(pickaxeOnly())
			.blockstate((c, p) -> BlockStateGen.directionalAxisBlock(c, p,
					(state, vertical) -> AssetLookup.partialBaseModel(c, p, vertical ? "vertical" : "horizontal",
							state.getValue(BrassValveBlock.ENABLED) ? "open" : "closed")))
			.onRegister(CreateRegistrate.blockModel(() -> BrassPipeAttachmentModel::new))
			.item()
			.transform(customItemModel())
			.register();


	public static final BlockEntry<BrassDrillBlock> BRASS_MECHANICAL_DRILL = BRASS_REGISTRATE.block("brass_mechanical_drill", BrassDrillBlock::new)
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
			BRASS_REGISTRATE.block("brass_mechanical_harvester", BrassHarvesterBlock::new)
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
			BRASS_REGISTRATE.block("brass_mechanical_plough", BrassPloughBlock::new)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.COLOR_ORANGE))
					.transform(TagGen.axeOrPickaxe())
					.onRegister(movementBehaviour(new BrassPloughMovementBehaviour()))
					.blockstate(BlockStateGen.horizontalBlockProvider(false))
					.simpleItem()
					.register();

	public static final BlockEntry<BrassDeployerBlock> BRASS_DEPLOYER = BRASS_REGISTRATE.block("brass_deployer", BrassDeployerBlock::new)
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

	public static final BlockEntry<BrassPortableStorageInterfaceBlock> BRASS_PORTABLE_STORAGE_INTERFACE =
			BRASS_REGISTRATE.block("brass_portable_storage_interface", BrassPortableStorageInterfaceBlock::forItems)
					.initialProperties(SharedProperties::stone)
					.properties(p -> p.color(MaterialColor.PODZOL))
					.transform(TagGen.axeOrPickaxe())
					.blockstate((c, p) -> p.directionalBlock(c.get(), AssetLookup.partialBaseModel(c, p)))
					.onRegister(movementBehaviour(new BrassPortableStorageInterfaceMovement()))
					.item()
					.transform(customItemModel())
					.register();

	public static final BlockEntry<BrassSawBlock> BRASS_MECHANICAL_SAW = BRASS_REGISTRATE.block("brass_mechanical_saw", BrassSawBlock::new)
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
