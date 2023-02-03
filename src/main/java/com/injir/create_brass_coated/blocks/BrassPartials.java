package com.injir.create_brass_coated.blocks;

import com.injir.create_brass_coated.Create_Brass_Coated;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.fluids.FluidTransportBehaviour;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BrassPartials {
	public static final PartialModel BRASS_DRILL_HEAD = block("brass_mechanical_drill/head");
	public static final PartialModel BRASS_HARVESTER_BLADE = block("brass_mechanical_harvester/blade");
	public static final PartialModel BRASS_DEPLOYER_POLE = block("brass_deployer/pole");
	public static final PartialModel BRASS_MECHANICAL_PRESS_HEAD = block("brass_mechanical_press/head");
	public static final PartialModel BRASS_MECHANICAL_MIXER_POLE = block("brass_mechanical_mixer/pole");
	public static final PartialModel BRASS_EJECTOR_TOP = block("brass_weighted_ejector/top");
	public static final PartialModel BRASS_GIRDER_SEGMENT_TOP = block("brass_girder/segment_top");
	public static final PartialModel BRASS_GIRDER_SEGMENT_MIDDLE = block("brass_girder/segment_middle");
	public static final PartialModel BRASS_GIRDER_SEGMENT_BOTTOM = block("brass_girder/segment_bottom");
	public static final PartialModel BRASS_PORTABLE_STORAGE_INTERFACE_MIDDLE = block("brass_portable_storage_interface/block_middle");
	public static final PartialModel BRASS_PORTABLE_STORAGE_INTERFACE_MIDDLE_POWERED = block("brass_portable_storage_interface/block_middle_powered");
	public static final PartialModel BRASS_PORTABLE_STORAGE_INTERFACE_TOP = block("brass_portable_storage_interface/block_top")
			;

	public static final Map<Direction, PartialModel> BRASS_GIRDER_BRACKETS = new EnumMap<>(Direction.class);

	static {
		for (Direction d : Iterate.horizontalDirections)
			BRASS_GIRDER_BRACKETS.put(d, block("brass_girder/bracket_" + Lang.asId(d.name())));
	}
	private static PartialModel block(String path) {
		return new PartialModel(new ResourceLocation(Create_Brass_Coated.ID, "block/" + path));
	}

	public static void init() {
	}

}
