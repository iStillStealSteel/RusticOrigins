package dev.istillstealsteel.originsrustic.core.mixin.accessor.minecraft;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(LivingEntity.class)
public interface LivingEntityAccessor {

    @Invoker("dropFromLootTable")
    void invokeDropFromLootTable(DamageSource source, boolean causedByPlayer);

}
