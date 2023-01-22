package net.sashiro.radioactiveores.effects;

import net.minecraft.world.damagesource.DamageSource;

public class DamageSources {
    public static final DamageSource RADIATION = (new DamageSource("radiation")).bypassMagic().bypassEnchantments().bypassArmor().setMagic();
}
