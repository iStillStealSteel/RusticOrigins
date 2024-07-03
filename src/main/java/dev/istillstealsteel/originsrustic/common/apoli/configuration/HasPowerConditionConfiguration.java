package dev.istillstealsteel.originsrustic.common.apoli.configuration;

import java.util.EnumSet;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;

import dev.istillstealsteel.originsrustic.common.calio.OriginsRusticDataTypes;
import io.github.edwinmindcraft.apoli.api.IDynamicFeatureConfiguration;
import io.github.edwinmindcraft.apoli.api.configuration.PowerReference;
import io.github.edwinmindcraft.calio.api.network.CalioCodecHelper;
import net.minecraft.world.entity.EquipmentSlot;

public record HasPowerConditionConfiguration(PowerReference powerType, EnumSet<EquipmentSlot> equipment) implements IDynamicFeatureConfiguration {

	public static final Codec<HasPowerConditionConfiguration> CODEC = RecordCodecBuilder.create(instance -> instance.group(
			PowerReference.mapCodec("power")
					.forGetter(HasPowerConditionConfiguration::powerType),
			CalioCodecHelper.optionalField(OriginsRusticDataTypes.EQUIPMENT_SLOT, "equipment", EnumSet.allOf(EquipmentSlot.class))
					.forGetter(HasPowerConditionConfiguration::equipment))
			.apply(instance, HasPowerConditionConfiguration::new));

}
