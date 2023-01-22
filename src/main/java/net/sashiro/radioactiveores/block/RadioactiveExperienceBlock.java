package net.sashiro.radioactiveores.block;

import net.minecraft.world.level.block.DropExperienceBlock;
import net.minecraft.world.level.material.Material;

public class RadioactiveExperienceBlock extends DropExperienceBlock implements Radioactive {
    private final double amplifier;

    public RadioactiveExperienceBlock(double amplifier, Material material, float hardness, float resistance) {
        super(Properties.of(material).requiresCorrectToolForDrops().strength(hardness, resistance));
        this.amplifier = amplifier;
    }

    public double getAmplifier() {
        return amplifier;
    }
}
