package dev.istillstealsteel.originsrustic.common.apoli.power;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredBiEntityAction;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredBiEntityCondition;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import dev.istillstealsteel.originsrustic.common.apoli.configuration.ActionOnTameConfiguration;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import net.minecraft.world.entity.Entity;

public class ActionOnTamePower extends PowerFactory<ActionOnTameConfiguration> {

    public ActionOnTamePower() {
        super(ActionOnTameConfiguration.CODEC, false);
    }

    public static void apply(Entity player, Entity tameable) {
        IPowerContainer.getPowers(player, OriginsRusticPowers.ACTION_ON_TAME.get()).stream()
            .filter(cp ->
                cp.get().isActive(player) &&
                ConfiguredBiEntityCondition.check(cp.get().getConfiguration().biEntityCondition(), player, tameable)
            ).forEach(cp ->
                ConfiguredBiEntityAction.execute(cp.get().getConfiguration().biEntityAction(), player, tameable)
            );
    }

}
