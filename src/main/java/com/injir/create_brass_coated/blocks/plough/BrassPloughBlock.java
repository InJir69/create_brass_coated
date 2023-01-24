package com.injir.create_brass_coated.blocks.plough;

import com.mojang.authlib.GameProfile;
import com.simibubi.create.content.contraptions.components.actors.AttachedActorBlock;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.util.FakePlayer;

import java.util.UUID;

public class BrassPloughBlock extends AttachedActorBlock {

	public BrassPloughBlock(Properties p_i48377_1_) {
		super(p_i48377_1_);
	}
	
	/**
	 * The OnHoeUse event takes a player, so we better not pass null
	 */
	static class BrassPloughFakePlayer extends FakePlayer {

		public static final GameProfile PLOUGH_PROFILE =
				new GameProfile(UUID.fromString("9e2faded-eeee-4ec2-c314-dad129ae971d"), "Brass_Plough");
		
		public BrassPloughFakePlayer(ServerLevel world) {
			super(world, PLOUGH_PROFILE);
		}
		
	}
	
}
