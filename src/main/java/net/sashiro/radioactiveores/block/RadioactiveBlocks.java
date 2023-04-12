/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.RadioactiveOres;

import static net.sashiro.radioactiveores.util.Config.*;

public class RadioactiveBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, RadioactiveOres.MOD_ID);

    public static RegistryObject<Block> THORIUM_ORE = null;
    public static RegistryObject<Block> DEEPSLATE_THORIUM_ORE = null;
    public static RegistryObject<Block> RAW_THORIUM_BLOCK = null;
    public static RegistryObject<Block> THORIUM_BLOCK = null;
    public static RegistryObject<Block> URANIUM_ORE = null;
    public static RegistryObject<Block> DEEPSLATE_URANIUM_ORE = null;
    public static RegistryObject<Block> RAW_URANIUM_BLOCK = null;
    public static RegistryObject<Block> URANIUM_BLOCK = null;
    public static RegistryObject<Block> PLUTONIUM_ORE = null;
    public static RegistryObject<Block> DEEPSLATE_PLUTONIUM_ORE = null;
    public static RegistryObject<Block> RAW_PLUTONIUM_BLOCK = null;
    public static RegistryObject<Block> PLUTONIUM_BLOCK = null;


    public static void register(IEventBus bus) {
        BLOCKS.register(bus);
        if (isThoriumEnabled) {
            THORIUM_ORE = BLOCKS.register("thorium_ore", () -> new RadioactiveBlock(1.5, Material.STONE, 3.5F, 3.5F));
            DEEPSLATE_THORIUM_ORE = BLOCKS.register("deepslate_thorium_ore", () -> new RadioactiveBlock(1.75, Material.STONE, 5.0F, 3.5F));
            RAW_THORIUM_BLOCK = BLOCKS.register("raw_thorium_block", () -> new RadioactiveBlock(3, Material.STONE, 5.5F, 6.5F));
            THORIUM_BLOCK = BLOCKS.register("thorium_block", () -> new RadioactiveBlock(4, Material.METAL, 5.5F, 6.5F));
        }
        if (isUraniumEnabled) {
            URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new RadioactiveBlock(2.5, Material.STONE, 3.5F, 3.5F));
            DEEPSLATE_URANIUM_ORE = BLOCKS.register("deepslate_uranium_ore", () -> new RadioactiveBlock(2.75, Material.STONE, 5.0F, 3.5F));
            RAW_URANIUM_BLOCK = BLOCKS.register("raw_uranium_block", () -> new RadioactiveBlock(4, Material.STONE, 6.0F, 7.0F));
            URANIUM_BLOCK = BLOCKS.register("uranium_block", () -> new RadioactiveBlock(5, Material.METAL, 6.0F, 7.0F));
        }
        if (isPlutoniumEnabled) {
            PLUTONIUM_ORE = BLOCKS.register("plutonium_ore", () -> new RadioactiveBlock(3.5, Material.STONE, 3.5F, 3.5F));
            DEEPSLATE_PLUTONIUM_ORE = BLOCKS.register("deepslate_plutonium_ore", () -> new RadioactiveBlock(3.75, Material.STONE, 5.0F, 3.5F));
            RAW_PLUTONIUM_BLOCK = BLOCKS.register("raw_plutonium_block", () -> new RadioactiveBlock(5, Material.STONE, 6.5F, 7.5F));
            PLUTONIUM_BLOCK = BLOCKS.register("plutonium_block", () -> new RadioactiveBlock(6, Material.METAL, 6.5F, 7.5F));
        }
    }
}
