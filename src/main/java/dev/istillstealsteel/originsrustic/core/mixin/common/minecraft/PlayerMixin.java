package dev.istillstealsteel.originsrustic.core.mixin.common.minecraft;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity {

    protected PlayerMixin(EntityType<? extends LivingEntity> type, Level world) {
        super(type, world);
    }

    @ModifyConstant(method = "checkMovementStatistics", constant = @Constant(floatValue = 0.1F, ordinal = 0))
    private float originsRustic$removeSprintExaustion(float orginal) {
        if(IPowerContainer.hasPower(this, OriginsRusticPowers.NO_SPRINT_EXHAUSTION.get())) {
            return 0;
        }
        return orginal;
    }

}
