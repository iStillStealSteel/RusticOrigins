package dev.istillstealsteel.originsrustic.common.apoli.power;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemAction;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredItemCondition;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.api.power.factory.PowerFactory;
import dev.istillstealsteel.originsrustic.common.apoli.configuration.ModifyCraftResultConfiguration;
import dev.istillstealsteel.originsrustic.common.event.ModifyCraftResultEvent;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.apache.commons.lang3.mutable.Mutable;
import org.apache.commons.lang3.mutable.MutableObject;

public class ModifyCraftResultPower extends PowerFactory<ModifyCraftResultConfiguration> {
    
    public ModifyCraftResultPower() {
        super(ModifyCraftResultConfiguration.CODEC);
    }
    
    public static boolean check(
        ConfiguredPower<ModifyCraftResultConfiguration, ModifyCraftResultPower> cp,
        Level level, ItemStack stack, ModifyCraftResultEvent.CraftingResultType type) {
        return cp.getConfiguration().craftingResultTypes().contains(type) &&
               ConfiguredItemCondition.check(cp.getConfiguration().itemCondition(), level, stack);
    }
    
    public static ItemStack modify(Player player, ItemStack stack, ModifyCraftResultEvent.CraftingResultType type) {
        Mutable<ItemStack> mutable = new MutableObject<>(stack);
        IPowerContainer.getPowers(player, OriginsRusticPowers.MODIFY_CRAFT_RESULT.get())
            .stream()
            .filter(cp -> check(cp.get(), player.level, stack, type))
            .map(cp -> cp.get().getConfiguration().itemAction())
            .forEach(action -> ConfiguredItemAction.execute(action, player.level, mutable));
        return mutable.getValue();
    }
    
}
