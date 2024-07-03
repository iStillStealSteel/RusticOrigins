package dev.istillstealsteel.originsrustic.core.mixin.common.minecraft;

import dev.istillstealsteel.originsrustic.util.CommonUtils;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.EnchantmentMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nullable;

@Mixin(EnchantmentMenu.class)
public class EnchantmentMenuMixin {

    @Nullable
    private Player originsRustic$enchanter;

    @Inject(method = "<init>(ILnet/minecraft/world/entity/player/Inventory;Lnet/minecraft/world/inventory/ContainerLevelAccess;)V", at = @At("TAIL"))
    private void originsRustic$saveEnchanter(int syncId, Inventory playerInventory, ContainerLevelAccess context, CallbackInfo ci) {
        this.originsRustic$enchanter = playerInventory.player;
    }

    @ModifyVariable(method = "slotsChanged", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/inventory/ContainerLevelAccess;execute(Ljava/util/function/BiConsumer;)V"))
    private ItemStack originsRustic$storeEnchanter(ItemStack stack) {
        if(this.originsRustic$enchanter != null) {
            stack.getOrCreateTagElement(CommonUtils.origins_rustic).putUUID(CommonUtils.ENCHANTER, this.originsRustic$enchanter.getUUID());
        }
        return stack;
    }

    @Inject(method = "slotsChanged", at = @At("TAIL"))
    private void originsRustic$clearEnchanter(Container inventory, CallbackInfo ci) {
        ItemStack stack = inventory.getItem(0);
        CompoundTag entry = stack.getOrCreateTagElement(CommonUtils.origins_rustic);
        entry.remove(CommonUtils.ENCHANTER);
        if(entry.isEmpty()) stack.removeTagKey(CommonUtils.origins_rustic);
    }

}
