package dev.istillstealsteel.originsrustic.core.mixin.common.minecraft;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.Projectile;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Projectile.class)
public class ProjectileMixin {

    private Entity originsRustic$cachedShooter;

    @Inject(method = "shootFromRotation", at = @At("HEAD"))
    private void originsRustic$cacheShooter(Entity entity, float p, float y, float r, float s, float d, CallbackInfo ci) {
        this.originsRustic$cachedShooter = entity;
    }

    @ModifyVariable(method = "shoot", at = @At("HEAD"), ordinal = 1, argsOnly = true)
    private float originsRustic$modifyDivergence(float oldDivergence) {
        if(originsRustic$cachedShooter != null) {
            float newDivergence = IPowerContainer.modify(
                originsRustic$cachedShooter,
                OriginsRusticPowers.MODIFY_PROJECTILE_DIVERGENCE.get(),
                oldDivergence,
                cp -> cp.get().isActive(originsRustic$cachedShooter)
            );
            originsRustic$cachedShooter = null;
            return newDivergence;
        }
        return oldDivergence;
    }

}