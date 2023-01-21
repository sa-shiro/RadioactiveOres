package net.sashiro.radioactiveores.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;

public class RadioactiveBlock extends Block implements Radioactive {
    private final double amplifier;

    public RadioactiveBlock(double amplifier, Material material) {
        super(Properties.of(material));
        this.amplifier = amplifier;
    }

    public double getAmplifier() {
        return amplifier;
    }
}
