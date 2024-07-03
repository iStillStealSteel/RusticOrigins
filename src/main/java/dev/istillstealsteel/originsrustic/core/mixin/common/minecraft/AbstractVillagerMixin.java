package dev.istillstealsteel.originsrustic.core.mixin.common.minecraft;

import io.github.edwinmindcraft.apoli.api.component.IPowerContainer;
import dev.istillstealsteel.originsrustic.common.registry.OriginsRusticPowers;
import dev.istillstealsteel.originsrustic.util.MerchantHelper;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.npc.AbstractVillager;
import net.minecraft.world.entity.npc.WanderingTrader;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.item.trading.MerchantOffers;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractVillager.class)
public abstract class AbstractVillagerMixin extends AgeableMob {

    @Shadow protected MerchantOffers offers;
    @Shadow private Player tradingPlayer;

    @Unique private int offerCountWithoutAdditional;
    @Unique private MerchantOffers additionalOffers;

    protected AbstractVillagerMixin(EntityType<? extends AgeableMob> type, Level world) {
        super(type, world);
    }

    @Inject(method = "notifyTrade", at = @At("TAIL"))
    private void originsRustic$infiniteTrade(MerchantOffer offer, CallbackInfo ci) {
        if(IPowerContainer.hasPower(tradingPlayer, OriginsRusticPowers.INFINITE_TRADE.get())) --offer.uses;
    }

    @Inject(method = "setTradingPlayer", at = @At("HEAD"))
    private void originsRustic$addAdditionalOffers(Player customer, CallbackInfo ci) {
        if((Object)this instanceof WanderingTrader) {
            if (IPowerContainer.hasPower(customer, OriginsRusticPowers.RARE_WANDERING_LOOT.get())) {
                if(additionalOffers == null) {
                    offerCountWithoutAdditional = offers.size();
                    additionalOffers = buildAdditionalOffers();
                }
                this.offers.addAll(additionalOffers);
            } else if(additionalOffers != null) {
                while(this.offers.size() > offerCountWithoutAdditional) {
                    this.offers.remove(this.offers.size() - 1);
                }
            }
        }
    }

    @Inject(method = "addAdditionalSaveData", at = @At("TAIL"))
    private void originsRustic$writeAdditionalOffersToTag(CompoundTag tag, CallbackInfo ci) {
        if(additionalOffers != null) {
            tag.put("AdditionalOffers", additionalOffers.createTag());
            tag.putInt("OfferCountNoAdditional", offerCountWithoutAdditional);
        }
    }

    @Inject(method = "readAdditionalSaveData", at = @At("TAIL"))
    private void originsRustic$readAdditionalOffersFromTag(CompoundTag tag, CallbackInfo ci) {
        if(tag.contains("AdditionalOffers")) {
            additionalOffers = new MerchantOffers(tag.getCompound("AdditionalOffers"));
            offerCountWithoutAdditional = tag.getInt("OfferCountNoAdditional");
        }
    }

    @Unique
    private MerchantOffers buildAdditionalOffers() {
        MerchantOffers offers = new MerchantOffers();
        MerchantHelper helper = MerchantHelper.instance();
        RandomSource random = RandomSource.create();
        Item desireditem = helper.randomObtainableItem(random, helper.blacklistItems);
        
        offers.add(new MerchantOffer(
            new ItemStack(Items.EMERALD, random.nextInt(12) + 6),
            MerchantHelper.randomEnchantedItemStack(
                helper.randomObtainableItem(random, helper.blacklistItems),
                random, 0.5f, 30
            ),
            1,
            5,
            0.05F)
        );
        
        offers.add(new MerchantOffer(
            new ItemStack(desireditem, 1 + random.nextInt(Math.min(16, desireditem.getDefaultInstance().getMaxStackSize()))),
            MerchantHelper.randomEnchantedItemStack(helper.randomObtainableItem(random, helper.blacklistItems)
                , random, 0.5F, 30
            ),
            1,
            5,
            0.05F)
        );
        
        return offers;
    }

}
