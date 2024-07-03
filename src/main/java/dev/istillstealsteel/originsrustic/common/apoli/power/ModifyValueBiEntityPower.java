package dev.istillstealsteel.originsrustic.common.apoli.power;

import io.github.edwinmindcraft.apoli.api.power.factory.power.ValueModifyingPowerFactory;
import dev.istillstealsteel.originsrustic.common.apoli.configuration.ModifyValueBiEntityConfiguration;

public class ModifyValueBiEntityPower extends ValueModifyingPowerFactory<ModifyValueBiEntityConfiguration> {

    public ModifyValueBiEntityPower() {
        super(ModifyValueBiEntityConfiguration.CODEC);
    }

}
