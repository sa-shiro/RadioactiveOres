package net.sashiro.radioactiveores.attributes;

import net.minecraft.world.entity.ai.attributes.RangedAttribute;

public class RadiationAttribute  extends RangedAttribute {
    private double radiationLevel;
    private double maxValue;
    private double decayRate;

    public RadiationAttribute(double maxValue, double decayRate){
        super("radiation", 0.0D, 0.0D, maxValue);
        this.radiationLevel = 0.0F;
        this.maxValue = maxValue;
        this.decayRate = decayRate;
    }

    public double getRadiationLevel() {
        return radiationLevel;
    }

    public void setRadiationLevel(double radiationLevel) {
        this.radiationLevel = radiationLevel;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public double getDecayRate() {
        return decayRate;
    }

    public void decay() {
        radiationLevel = radiationLevel - decayRate;
    }

    public boolean isDeadly() {
        return radiationLevel >= maxValue;
    }
}
