/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.attributes;

import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.RadioactiveOres;

import java.util.UUID;

public class Attributes {
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, RadioactiveOres.MOD_ID);

    public static final RegistryObject<Attribute> RADIATION_ATTRIBUTE = ATTRIBUTES.register("radiation", () -> new RadiationAttribute(0.0D, 0.0D, 25000).setSyncable(true));

    public static void register(IEventBus bus) {
        ATTRIBUTES.register(bus);
    }

    public static class Modifiers {
        public static final UUID RADIATION_MODIFIER_UUID = UUID.fromString("6a782908-ac9f-4626-abb6-feed350435eb");
        public static final AttributeModifier RADIATION_MODIFIER = new AttributeModifier(RADIATION_MODIFIER_UUID, "radiation", 20, AttributeModifier.Operation.ADDITION);
    }
}
