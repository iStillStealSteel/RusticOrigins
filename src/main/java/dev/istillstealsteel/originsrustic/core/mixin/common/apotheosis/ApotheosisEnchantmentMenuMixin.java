package dev.istillstealsteel.originsrustic.core.mixin.common.apotheosis;

import dev.istillstealsteel.originsrustic.util.CommonUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import shadows.apotheosis.ench.table.ApothEnchantContainer;

@Mixin(ApothEnchantContainer.class)
public class ApotheosisEnchantmentMenuMixin {

    @Shadow(remap = false) @Final protected Player player;

    @ModifyVariable(
        method = "lambda$slotsChanged$1",
        at = @At(value = "INVOKE", target = "Lshadows/apotheosis/ench/table/ApothEnchantContainer;gatherStats()V", remap = false),
        name = "toEnchant"
    )
    private ItemStack originsRustic$storeEnchanter(ItemStack stack) {
        stack.getOrCreateTagElement(CommonUtils.origins_rustic).putUUID(CommonUtils.ENCHANTER, this.player.getUUID());
        return stack;
    }

    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void originsRustic$clearEnchanter(Container inventoryIn, CallbackInfo ci) {
        ItemStack stack = inventoryIn.getItem(0);
        CompoundTag entry = stack.getOrCreateTagElement(CommonUtils.origins_rustic);
        entry.remove(CommonUtils.ENCHANTER);
        if(entry.isEmpty()) stack.removeTagKey(CommonUtils.origins_rustic);
    }

}
