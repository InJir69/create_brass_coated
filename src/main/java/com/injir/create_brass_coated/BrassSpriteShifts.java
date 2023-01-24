package com.injir.create_brass_coated;

import com.simibubi.create.Create;
import com.simibubi.create.foundation.block.connected.AllCTTypes;
import com.simibubi.create.foundation.block.connected.CTSpriteShiftEntry;
import com.simibubi.create.foundation.block.connected.CTSpriteShifter;
import com.simibubi.create.foundation.block.connected.CTType;
import com.simibubi.create.foundation.block.render.SpriteShiftEntry;
import com.simibubi.create.foundation.block.render.SpriteShifter;
import com.simibubi.create.foundation.utility.Couple;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Arrays;
import java.util.EnumMap;
import java.util.IdentityHashMap;
import java.util.Map;

public class BrassSpriteShifts {

	public static final CTSpriteShiftEntry
		BRASS_GIRDER_POLE = vertical("brass_girder_pole_side");

	private static CTSpriteShiftEntry omni(String name) {
		return getCT(AllCTTypes.OMNIDIRECTIONAL, name);
	}

	private static CTSpriteShiftEntry horizontal(String name) {
		return getCT(AllCTTypes.HORIZONTAL, name);
	}

	private static CTSpriteShiftEntry vertical(String name) {
		return getCT(AllCTTypes.VERTICAL, name);
	}

	private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName, String connectedTextureName) {
		return CTSpriteShifter.getCT(type, Create_Brass_Coated.asResource("block/" + blockTextureName), Create_Brass_Coated.asResource("block/" + connectedTextureName + "_connected"));
	}

	private static CTSpriteShiftEntry getCT(CTType type, String blockTextureName) {
		return getCT(type, blockTextureName, blockTextureName);
	}
}
