package dev.istillstealsteel.originsrustic.common.apoli.power;

import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.PowerTypeReference;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;

public class RepairItemPowerFactory extends PowerFactory<RepairItemPower> {

    public RepairItemPowerFactory() {
        super(new ResourceLocation("originsrustic", "repair_item"),
                new SerializableData()
                        .add("item", SerializableDataTypes.ITEM),
                data -> RepairItemPower::new);
    }
}