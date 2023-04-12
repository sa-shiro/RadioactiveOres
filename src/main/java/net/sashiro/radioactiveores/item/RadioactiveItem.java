/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.item;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.sashiro.radioactiveores.block.Radioactive;
import net.sashiro.radioactiveores.block.RadioactiveBlock;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RadioactiveItem extends Item implements Radioactive {
    private final RadioactiveBlock block;
    private double amplifier;


    public RadioactiveItem(Block block, Properties properties) {
        super(properties);
        this.block = (RadioactiveBlock) block;
        this.amplifier = this.block.getAmplifier();
    }

    @Override
    public double getAmplifier() {
        return amplifier;
    }

    public RadioactiveItem setAmplifier(double amplifier) {
        this.amplifier = amplifier;
        return this;
    }

    @Override
    public void appendHoverText(ItemStack itemStack, @Nullable Level level, List<Component> components, TooltipFlag tooltipFlag) {
        super.appendHoverText(itemStack, level, components, tooltipFlag);
        components.add(Component.literal("Radiation Level: " + getAmplifier()).setStyle(Style.EMPTY.withColor(ChatFormatting.YELLOW)));
    }
}
