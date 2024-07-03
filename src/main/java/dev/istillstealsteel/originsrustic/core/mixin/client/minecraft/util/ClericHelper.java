package dev.istillstealsteel.originsrustic.util;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;

public class ClericHelper {
    
    public static void setPotionBonus(CompoundTag nbt, boolean flag) {
        CommonUtils.getOrCreateOriginsRusticTag(nbt).putBoolean(CommonUtils.POTION_BONUS, flag);
    }
    
    public static boolean getPotionBonus(CompoundTag nbt) {
        CompoundTag entry = CommonUtils.getOriginsRusticTag(nbt);
        return entry.getBoolean(CommonUtils.POTION_BONUS);
    }

    public static MobEffectInstance applyPotionBonus(MobEffectInstance effect) {
        boolean instant = effect.getEffect().isInstantenous();
        return new MobEffectInstance(
            effect.getEffect(),
            instant ? effect.getDuration() : effect.getDuration() * 2,
            instant ? effect.getAmplifier() + 1 : effect.getAmplifier(),
            effect.isAmbient(),
            effect.isVisible(),
            effect.showIcon()
        );
    }
    
}
