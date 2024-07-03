package dev.istillstealsteel.originsrustic.core.mixin.common.apoli;

import dev.istillstealsteel.originsrustic.OriginsRustic;
import dev.istillstealsteel.originsrustic.util.CommonUtils;
import io.github.edwinmindcraft.apoli.api.ApoliAPI;
import io.github.edwinmindcraft.apoli.api.power.configuration.ConfiguredPower;
import io.github.edwinmindcraft.apoli.common.power.ModifyFoodPower;
import io.github.edwinmindcraft.apoli.common.power.configuration.ModifyFoodConfiguration;
import net.minecraft.core.Registry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(ModifyFoodPower.class)
public class ModifyFoodPowerMixin {
    
    @SuppressWarnings("unchecked")
    @Inject(
        method = "getValidPowers(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
        at = @At("RETURN"),
        cancellable = true,
        remap = false
    )
    private static void originsRustic$bindModifyCraftedFoodPowers(Entity source, Level level, ItemStack stack, CallbackInfoReturnable<List<ConfiguredPower<ModifyFoodConfiguration, ModifyFoodPower>>> cir) {
        if(!stack.hasTag()) return;
        CompoundTag entry = CommonUtils.getOriginsRusticTag(stack.getTag());
        if(!entry.contains(CommonUtils.MODIFY_FOOD_POWERS, Tag.TAG_LIST)) return;
        ListTag powerIds;
        try {
            powerIds = entry.getList(CommonUtils.MODIFY_FOOD_POWERS, Tag.TAG_STRING);
        } catch(Throwable throwable) {
            OriginsRustic.LOGGER.error("Failed to read OriginsRustic.ModifyFoodPowers in {} due to incompatible type!", stack.getTag());
            return;
        }
        if(powerIds.isEmpty()) return;
        List<ConfiguredPower<ModifyFoodConfiguration, ModifyFoodPower>> powers = cir.getReturnValue();
        Registry<ConfiguredPower<?, ?>> powerRegistry = ApoliAPI.getPowers();
        for(int i = 0; i < powerIds.size(); ++i) {
            try {
                ResourceLocation id = new ResourceLocation(powerIds.getString(i));
                ConfiguredPower<?, ?> cp = powerRegistry.get(id);
                if(cp == null) throw new IllegalStateException(String.format("Power %s was not registered", id));
                if(cp.getConfiguration() instanceof ModifyFoodConfiguration && cp.getFactory() instanceof ModifyFoodPower) {
                    powers.add((ConfiguredPower<ModifyFoodConfiguration, ModifyFoodPower>) cp);
                }
            } catch(Exception exception) {
                OriginsRustic.LOGGER.error("Exception in applying ModifyFoodPowers from ModifyCraftedFoodPower!");
                OriginsRustic.LOGGER.error(exception);
            }
        }
        cir.setReturnValue(powers);
    }
    
}
