package net.sashiro.radioactiveores.block;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;

public class RadioactiveOreBlockItem extends BlockItem implements Radioactive {
    final RadioactiveOreBlock block;

    public RadioactiveOreBlockItem(Block block, Properties properties) {
        super(block, properties);
        this.block = (RadioactiveOreBlock) block;
    }

    @Override
    public double getAmplifier() {
        return block.getAmplifier();
    }
}
