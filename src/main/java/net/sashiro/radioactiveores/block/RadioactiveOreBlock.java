package net.sashiro.radioactiveores.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class RadioactiveOreBlock extends Block implements Radioactive {
    private final double amplifier;

    public RadioactiveOreBlock(double amplifier) {
        super(Properties.of(Material.STONE));
        this.amplifier = amplifier;
    }

    public double getAmplifier() {
        return amplifier;
    }
}
