package net.sashiro.radioactiveores.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.sashiro.radioactiveores.block.Radioactive;
import net.sashiro.radioactiveores.block.RadioactiveBlock;

public class RadioactiveItem extends Item implements Radioactive {
    private final RadioactiveBlock block;

    public RadioactiveItem(Block block, Properties properties) {
        super(properties);
        this.block = (RadioactiveBlock) block;
    }

    @Override
    public double getAmplifier() {
        return block.getAmplifier();
    }
}
