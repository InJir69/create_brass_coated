package com.injir.create_brass_coated.blocks;

import com.injir.create_brass_coated.Create_Brass_Coated;
import com.jozufozu.flywheel.core.PartialModel;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.fluids.FluidTransportBehaviour;
import com.simibubi.create.foundation.utility.Iterate;
import com.simibubi.create.foundation.utility.Lang;
import net.minecraft.core.Direction;
import net.minecraft.world.item.DyeColor;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public class BrassPartials {

	public static final PartialModel

	BRASS_DRILL_HEAD = block("brass_mechanical_drill/head"),
	BRASS_HARVESTER_BLADE = block("brass_mechanical_harvester/blade"),
	BRASS_DEPLOYER_POLE = block("brass_deployer/pole"),
	BRASS_MECHANICAL_PRESS_HEAD = block("brass_mechanical_press/head"),
	BRASS_MECHANICAL_MIXER_POLE = block("brass_mechanical_mixer/pole"),
	BRASS_EJECTOR_TOP = block("brass_weighted_ejector/top"),
	BRASS_GIRDER_SEGMENT_TOP = block("brass_girder/segment_top"),
	BRASS_GIRDER_SEGMENT_MIDDLE = block("brass_girder/segment_middle"),
	BRASS_GIRDER_SEGMENT_BOTTOM = block("brass_girder/segment_bottom")
	;

	public static final Map<Direction, PartialModel> BRASS_GIRDER_BRACKETS = new EnumMap<>(Direction.class);

	static {
		for (Direction d : Iterate.horizontalDirections)
			BRASS_GIRDER_BRACKETS.put(d, block("brass_girder/bracket_" + Lang.asId(d.name())));
	}
	private static PartialModel block(String path) {
		return new PartialModel(Create_Brass_Coated.asResource("block/" + path));
	}

	private static PartialModel entity(String path) {
		return new PartialModel(Create_Brass_Coated.asResource("entity/" + path));
	}

	public static void init() {
	}

}
