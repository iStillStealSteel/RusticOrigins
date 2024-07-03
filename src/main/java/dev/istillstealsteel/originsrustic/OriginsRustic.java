package dev.istillstealsteel.originsrustic;

import dev.istillstealsteel.originsrustic.common.OriginsRusticCommon;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticConditions;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import dev.istillstealsteel.originsrustic.util.ClientConfig;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(OriginsRustic.MODID)
public class OriginsRustic {
    public static final String MODID = "origins_rustic";
    public static final String LEGACY_MODID = "origins-rustic";
    public static final Logger LOGGER = LogManager.getLogger();
    
    @OnlyIn(Dist.CLIENT)
    public static boolean INFINITE_TRADER;
    @OnlyIn(Dist.CLIENT)
    public static boolean MULTI_MINING;
    
    public OriginsRustic() {
        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, ClientConfig.SPEC);
        IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
        OriginsRusticConditions.BIENTITY_CONDITIONS.register(modBus);
        OriginsRusticConditions.ENTITY_CONDITIONS.register(modBus);
        OriginsRusticConditions.BLOCK_CONDITIONS.register(modBus);
        OriginsRusticConditions.ITEM_CONDITIONS.register(modBus);
        OriginsRusticPowers.POWER_FACTORIES.register(modBus);
        modBus.addListener(OriginsRusticCommon::setup);
        LOGGER.info("Origins:Classes " + ModLoadingContext.get().getActiveContainer().getModInfo().getVersion() + " has initialized. Time for work!");
    }

    public static ResourceLocation identifier(String path) {
        return new ResourceLocation(MODID, path);
    }

    public static ResourceLocation legacyIdentifier(String path) {
        return new ResourceLocation(LEGACY_MODID, path);
    }

}
