package net.sashiro.radioactiveores.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import net.sashiro.radioactiveores.block.Radioactive;
import net.sashiro.radioactiveores.block.RadioactiveBlock;

public class RadioactiveBlockItem extends BlockItem implements Radioactive {
    private final RadioactiveBlock block;

    public RadioactiveBlockItem(Block block, Properties properties) {
        super(block, properties);
        this.block = (RadioactiveBlock) block;
    }

    @Override
    public double getAmplifier() {
        return block.getAmplifier();
    }
}
