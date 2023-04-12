/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.sashiro.radioactiveores.effects.MobEffects;

public class RadiationBlocker extends Item {

    public static final FoodProperties BLOCKER = (new FoodProperties.Builder()).alwaysEat().effect(new MobEffectInstance(MobEffects.RADIATION_BLOCKER_EFFECT.get(), 6000, 0), 1.0F).build();

    public RadiationBlocker() {
        super(new Item.Properties().stacksTo(8).rarity(Rarity.EPIC).food(BLOCKER));
    }

    @Override
    public boolean isEdible() {
        return true;
    }
}
