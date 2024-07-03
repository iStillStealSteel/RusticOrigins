package dev.istillstealsteel.originsrustic.common.apoli.power;

import dev.istillstealsteel.originsrustic.util.CommonUtils;
import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemCondition;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import dev.istillstealsteel.originsrustic.common.apoli.configuration.ModifyCraftedFoodConfiguration;
import dev.istillstealsteel.originsrustic.common.event.ModifyCraftResultEvent;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class ModifyCraftedFoodPower extends PowerFactory<ModifyCraftedFoodConfiguration> {

    public ModifyCraftedFoodPower() {
        super(ModifyCraftedFoodConfiguration.CODEC);
    }
    
    public static boolean check(ConfiguredPower<ModifyCraftedFoodConfiguration, ModifyCraftedFoodPower> cp, Level level, ItemStack stack, ModifyCraftResultEvent.CraftingResultType type) {
        return cp.getConfiguration().craftingResultTypes().contains(type) && ConfiguredItemCondition.check(cp.getConfiguration().itemCondition(), level, stack);
    }
    
    public static ItemStack modify(Player player, ItemStack stack, ModifyCraftResultEvent.CraftingResultType type) {
        ListTag tag = new ListTag();
        IPowerContainer.getPowers(player, OriginsRusticPowers.MODIFY_CRAFTED_FOOD.get()).stream()
            .filter(cp -> check(cp.get(), player.level, stack, type))
            .forEach(cp -> tag.add(StringTag.valueOf(cp.get().getConfiguration().modifyFoodPower().power().toString())));
        if(!tag.isEmpty()) stack.getOrCreateTagElement(CommonUtils.origins_rustic).put(CommonUtils.MODIFY_FOOD_POWERS, tag);
        return stack;
    }

}
