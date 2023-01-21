package net.sashiro.radioactiveores.item;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.sashiro.radioactiveores.RadioactiveOres;

public class RadiationBlocker extends Item {

    public static final FoodProperties BLOCKER = (new FoodProperties.Builder()).alwaysEat().effect(new MobEffectInstance(RadioactiveOres.RADIATION_BLOCKER_EFFECT.get(), 6000, 0), 1.0F).build();

    public RadiationBlocker() {
        super(new Item.Properties().stacksTo(16).rarity(Rarity.EPIC).food(BLOCKER));
    }

    @Override
    public boolean isEdible() {
        return true;
    }
}
