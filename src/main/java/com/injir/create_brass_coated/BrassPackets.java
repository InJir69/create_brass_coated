package com.injir.create_brass_coated;

import com.injir.create_brass_coated.blocks.depot.BrassEjectorAwardPacket;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorElytraPacket;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorPlacementPacket;
import com.injir.create_brass_coated.blocks.depot.BrassEjectorTriggerPacket;
import com.simibubi.create.Create;
import com.simibubi.create.content.logistics.block.depot.EjectorAwardPacket;
import com.simibubi.create.content.logistics.block.depot.EjectorElytraPacket;
import com.simibubi.create.content.logistics.block.depot.EjectorPlacementPacket;
import com.simibubi.create.content.logistics.block.depot.EjectorTriggerPacket;
import com.simibubi.create.foundation.networking.AllPackets;
import com.simibubi.create.foundation.networking.SimplePacketBase;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import static net.minecraftforge.network.NetworkDirection.PLAY_TO_CLIENT;
import static net.minecraftforge.network.NetworkDirection.PLAY_TO_SERVER;

public enum BrassPackets {
    PLACE_EJECTOR(BrassEjectorPlacementPacket .class, BrassEjectorPlacementPacket::new, PLAY_TO_SERVER),
    TRIGGER_EJECTOR(BrassEjectorTriggerPacket .class, BrassEjectorTriggerPacket::new, PLAY_TO_SERVER),
    EJECTOR_ELYTRA(BrassEjectorElytraPacket .class, BrassEjectorElytraPacket::new, PLAY_TO_SERVER),
    EJECTOR_AWARD(BrassEjectorAwardPacket.class, BrassEjectorAwardPacket::new, PLAY_TO_SERVER),
        ;
    public static final ResourceLocation CHANNEL_NAME = Create_Brass_Coated.asResource("main");
    public static final int NETWORK_VERSION = 1;
    public static final String NETWORK_VERSION_STR = String.valueOf(NETWORK_VERSION);
    public static SimpleChannel channel;

    private BrassPackets.LoadedPacket<?> packet;

    <T extends SimplePacketBase> BrassPackets(Class<T> type, Function<FriendlyByteBuf, T> factory,
                                            NetworkDirection direction) {
        packet = new BrassPackets.LoadedPacket<>(type, factory, direction);
    }

    public static void registerPackets() {
        channel = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .serverAcceptedVersions(NETWORK_VERSION_STR::equals)
                .clientAcceptedVersions(NETWORK_VERSION_STR::equals)
                .networkProtocolVersion(() -> NETWORK_VERSION_STR)
                .simpleChannel();
        for (BrassPackets packet : values())
            packet.packet.register();
    }

    public static void sendToNear(Level world, BlockPos pos, int range, Object message) {
        channel.send(
                PacketDistributor.NEAR.with(PacketDistributor.TargetPoint.p(pos.getX(), pos.getY(), pos.getZ(), range, world.dimension())),
                message);
    }

    private static class LoadedPacket<T extends SimplePacketBase> {
        private static int index = 0;

        private BiConsumer<T, FriendlyByteBuf> encoder;
        private Function<FriendlyByteBuf, T> decoder;
        private BiConsumer<T, Supplier<NetworkEvent.Context>> handler;
        private Class<T> type;
        private NetworkDirection direction;

        private LoadedPacket(Class<T> type, Function<FriendlyByteBuf, T> factory, NetworkDirection direction) {
            encoder = T::write;
            decoder = factory;
            handler = T::handle;
            this.type = type;
            this.direction = direction;
        }

        private void register() {
            channel.messageBuilder(type, index++, direction)
                    .encoder(encoder)
                    .decoder(decoder)
                    .consumer(handler)
                    .add();
        }
    }
}
