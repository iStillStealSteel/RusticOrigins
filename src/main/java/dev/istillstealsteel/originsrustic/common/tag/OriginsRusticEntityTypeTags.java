package dev.istillstealsteel.originsrustic.common.tag;

import dev.istillstealsteel.originsrustic.OriginsRustic;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class OriginsRusticEntityTypeTags {

    public static final TagKey<EntityType<?>> INFINITE_TRADER = tag(OriginsRustic.legacyIdentifier("infinite_trader"));

    private static TagKey<EntityType<?>> tag(ResourceLocation id) {
        return TagKey.create(Registry.ENTITY_TYPE_REGISTRY, id);
    }

}
