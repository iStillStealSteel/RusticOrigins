package dev.istillstealsteel.originsrustic.common.apoli.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.apoli.power.Power;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;

public class RepairItemPower extends Power implements IDynamicFeatureConfiguration {

    public RepairItemPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public void repairItem(ItemStack stack) {
        if (stack.isDamageableItem()) {  // Ensure the method matches your Minecraft version
            stack.setDamageValue(0);  // This method might vary based on your version
        }
    }
}