package com.injir.create_brass_coated.blocks.other;

import com.injir.create_brass_coated.blocks.BrassBlocks;
import com.injir.create_brass_coated.blocks.chute.BrassAbstractChuteBlock;
import com.injir.create_brass_coated.blocks.deployer.BrassDeployerBlock;
import com.injir.create_brass_coated.blocks.saw.BrassSawBlock;
import com.simibubi.create.Create;
import com.simibubi.create.content.contraptions.base.KineticTileEntity;
import com.simibubi.create.content.contraptions.components.saw.SawBlock;
import com.simibubi.create.content.logistics.block.chute.AbstractChuteBlock;
import com.simibubi.create.content.logistics.block.mechanicalArm.AllArmInteractionPointTypes;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPoint;
import com.simibubi.create.content.logistics.block.mechanicalArm.ArmInteractionPointType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;

import java.util.function.Function;

public class BrassArmInteractionPointTypes {

	public static final DeployerType DEPLOYER = register("brass_deployer", DeployerType::new);
	public static final DepotType DEPOT = register("brass_depot", DepotType::new);
	public static final SawType SAW = register("brass_saw", SawType::new);
	public static final ChuteType CHUTE = register("brass_chute", ChuteType::new);


	private static <T extends ArmInteractionPointType> T register(String id, Function<ResourceLocation, T> factory) {
		T type = factory.apply(Create.asResource(id));
		ArmInteractionPointType.register(type);
		return type;
	}

	public static void register() {}

	public static class ChuteType extends ArmInteractionPointType {
		public ChuteType(ResourceLocation id) {
			super(id);
		}

		@Override
		public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
			return BrassAbstractChuteBlock.isChute(state);
		}

		@Override
		public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
			return new TopFaceArmInteractionPoint(this, level, pos, state);
		}
	}
	public static class DeployerType extends ArmInteractionPointType {
		public DeployerType(ResourceLocation id) {
			super(id);
		}

		@Override
		public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
			return BrassBlocks.BRASS_DEPLOYER.has(state);
		}

		@Override
		public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
			return new DeployerPoint(this, level, pos, state);
		}
	}

	public static class DepotType extends ArmInteractionPointType {
		public DepotType(ResourceLocation id) {
			super(id);
		}

		@Override
		public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
			return BrassBlocks.BRASS_DEPOT.has(state) || BrassBlocks.BRASS_WEIGHTED_EJECTOR.has(state);
		}

		@Override
		public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
			return new DepotPoint(this, level, pos, state);
		}
	}

	public static class SawType extends ArmInteractionPointType {
		public SawType(ResourceLocation id) {
			super(id);
		}

		@Override
		public boolean canCreatePoint(Level level, BlockPos pos, BlockState state) {
			return BrassBlocks.BRASS_MECHANICAL_SAW.has(state) && state.getValue(BrassSawBlock.FACING) == Direction.UP
				&& ((KineticTileEntity) level.getBlockEntity(pos)).getSpeed() != 0;
		}

		@Override
		public ArmInteractionPoint createPoint(Level level, BlockPos pos, BlockState state) {
			return new DepotPoint(this, level, pos, state);
		}
	}

	public static class DeployerPoint extends ArmInteractionPoint {
		public DeployerPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
			super(type, level, pos, state);
		}

		@Override
		protected Direction getInteractionDirection() {
			return cachedState.getOptionalValue(BrassDeployerBlock.FACING)
				.orElse(Direction.UP)
				.getOpposite();
		}

		@Override
		protected Vec3 getInteractionPositionVector() {
			return super.getInteractionPositionVector().add(Vec3.atLowerCornerOf(getInteractionDirection().getNormal())
				.scale(.65f));
		}

		@Override
		public void updateCachedState() {
			BlockState oldState = cachedState;
			super.updateCachedState();
			if (oldState != cachedState)
				cachedAngles = null;
		}
	}

	public static class DepotPoint extends ArmInteractionPoint {
		public DepotPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
			super(type, level, pos, state);
		}

		@Override
		protected Vec3 getInteractionPositionVector() {
			return Vec3.atLowerCornerOf(pos)
				.add(.5f, 14 / 16f, .5f);
		}
	}

	public static class TopFaceArmInteractionPoint extends ArmInteractionPoint {
		public TopFaceArmInteractionPoint(ArmInteractionPointType type, Level level, BlockPos pos, BlockState state) {
			super(type, level, pos, state);
		}

		@Override
		protected Vec3 getInteractionPositionVector() {
			return Vec3.atLowerCornerOf(pos)
					.add(.5f, 1, .5f);
		}
	}

}
