package dev.istillstealsteel.originsrustic.common;

import dev.istillstealsteel.originsrustic.OriginsRustic;
import dev.istillstealsteel.originsrustic.common.network.S2CInfiniteTrader;
import dev.istillstealsteel.originsrustic.compat.AppleSkinCompat;
import dev.istillstealsteel.originsrustic.common.network.S2CMultiMining;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class OriginsRusticCommon {
    public static final String NETWORK_VERSION = "1.0";
    public static final SimpleChannel CHANNEL = NetworkRegistry.ChannelBuilder
        .named(OriginsRustic.identifier("channel"))
        .networkProtocolVersion(() -> NETWORK_VERSION)
        .clientAcceptedVersions(NETWORK_VERSION::equals)
        .serverAcceptedVersions(NETWORK_VERSION::equals)
        .simpleChannel();

    public static void setup(final FMLCommonSetupEvent event) {
        event.enqueueWork(OriginsRusticCommon::initNetwork);
        if(ModList.get().isLoaded("appleskin")) {
            MinecraftForge.EVENT_BUS.register(AppleSkinCompat.class);
        }
    }

    private static void initNetwork() {
        int messageId = 0;
        CHANNEL.messageBuilder(S2CInfiniteTrader.class, ++messageId, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(S2CInfiniteTrader::encode)
            .decoder(S2CInfiniteTrader::decode)
            .consumerNetworkThread(S2CInfiniteTrader::handle)
            .add();
        CHANNEL.messageBuilder(S2CMultiMining.class, ++messageId, NetworkDirection.PLAY_TO_CLIENT)
            .encoder(S2CMultiMining::encode)
            .decoder(S2CMultiMining::decode)
            .consumerNetworkThread(S2CMultiMining::handle)
            .add();
        OriginsRustic.LOGGER.debug("Registered {} newtork messages.", messageId);
    }

}
