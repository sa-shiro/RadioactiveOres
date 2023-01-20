package net.sashiro.radioactiveores.attributes;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class RadiationAttribute extends RangedAttribute {

    public RadiationAttribute(double defaultValue, double minValue, double maxValue) {
        super("radiation", defaultValue, minValue, maxValue);
    }
}
