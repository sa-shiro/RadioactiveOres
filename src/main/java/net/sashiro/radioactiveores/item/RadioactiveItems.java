package net.sashiro.radioactiveores.item;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.RadioactiveOres;
import net.sashiro.radioactiveores.block.RadioactiveBlocks;

public class RadioactiveItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, RadioactiveOres.MOD_ID);

    public static void register(IEventBus bus) {
        ITEMS.register(bus);
    }

    public static final RegistryObject<Item> RADIATION_BLOCKER = ITEMS.register("radiation_blocker", RadiationBlocker::new);
    public static final RegistryObject<Item> PLUTONIUM_NUGGET = ITEMS.register("plutonium_nugget", () -> new RadioactiveItem(RadioactiveBlocks.PLUTONIUM_ORE.get(), new Item.Properties()).setAmplifier(0.75));
    public static final RegistryObject<Item> PLUTONIUM_INGOT = ITEMS.register("plutonium_ingot", () -> new RadioactiveItem(RadioactiveBlocks.PLUTONIUM_ORE.get(), new Item.Properties()).setAmplifier(3.25));
    public static final RegistryObject<Item> RAW_PLUTONIUM = ITEMS.register("raw_plutonium", () -> new RadioactiveItem(RadioactiveBlocks.PLUTONIUM_ORE.get(), new Item.Properties()).setAmplifier(3));
    public static final RegistryObject<Item> URANIUM_NUGGET = ITEMS.register("uranium_nugget", () -> new RadioactiveItem(RadioactiveBlocks.URANIUM_ORE.get(), new Item.Properties()).setAmplifier(0.5));
    public static final RegistryObject<Item> URANIUM_INGOT = ITEMS.register("uranium_ingot", () -> new RadioactiveItem(RadioactiveBlocks.URANIUM_ORE.get(), new Item.Properties()).setAmplifier(2.25));
    public static final RegistryObject<Item> RAW_URANIUM = ITEMS.register("raw_uranium", () -> new RadioactiveItem(RadioactiveBlocks.URANIUM_ORE.get(), new Item.Properties()).setAmplifier(2));
    public static final RegistryObject<Item> THORIUM_NUGGET = ITEMS.register("thorium_nugget", () -> new RadioactiveItem(RadioactiveBlocks.THORIUM_ORE.get(), new Item.Properties()).setAmplifier(0.25));
    public static final RegistryObject<Item> THORIUM_INGOT = ITEMS.register("thorium_ingot", () -> new RadioactiveItem(RadioactiveBlocks.THORIUM_ORE.get(), new Item.Properties()).setAmplifier(1.25));
    public static final RegistryObject<Item> RAW_THORIUM = ITEMS.register("raw_thorium", () -> new RadioactiveItem(RadioactiveBlocks.THORIUM_ORE.get(), new Item.Properties()).setAmplifier(1));

    public static final RegistryObject<BlockItem> THORIUM_BLOCK_ITEM = ITEMS.register("thorium_block", () -> new RadioactiveBlockItem(RadioactiveBlocks.THORIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> URANIUM_BLOCK_ITEM = ITEMS.register("uranium_block", () -> new RadioactiveBlockItem(RadioactiveBlocks.URANIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PLUTONIUM_BLOCK_ITEM = ITEMS.register("plutonium_block", () -> new RadioactiveBlockItem(RadioactiveBlocks.PLUTONIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> THORIUM_ORE_ITEM = ITEMS.register("thorium_ore", () -> new RadioactiveBlockItem(RadioactiveBlocks.THORIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_THORIUM_ORE_ITEM = ITEMS.register("deepslate_thorium_ore", () -> new RadioactiveBlockItem(RadioactiveBlocks.DEEPSLATE_THORIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> URANIUM_ORE_ITEM = ITEMS.register("uranium_ore", () -> new RadioactiveBlockItem(RadioactiveBlocks.URANIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_URANIUM_ORE_ITEM = ITEMS.register("deepslate_uranium_ore", () -> new RadioactiveBlockItem(RadioactiveBlocks.DEEPSLATE_URANIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> PLUTONIUM_ORE_ITEM = ITEMS.register("plutonium_ore", () -> new RadioactiveBlockItem(RadioactiveBlocks.PLUTONIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> DEEPSLATE_PLUTONIUM_ORE_ITEM = ITEMS.register("deepslate_plutonium_ore", () -> new RadioactiveBlockItem(RadioactiveBlocks.DEEPSLATE_PLUTONIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RAW_THORIUM_BLOCK_ITEM = ITEMS.register("raw_thorium_block", () -> new RadioactiveBlockItem(RadioactiveBlocks.RAW_THORIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RAW_URANIUM_BLOCK_ITEM = ITEMS.register("raw_uranium_block", () -> new RadioactiveBlockItem(RadioactiveBlocks.RAW_URANIUM_BLOCK.get(), new Item.Properties()));
    public static final RegistryObject<BlockItem> RAW_PLUTONIUM_BLOCK_ITEM = ITEMS.register("raw_plutonium_block", () -> new RadioactiveBlockItem(RadioactiveBlocks.RAW_PLUTONIUM_BLOCK.get(), new Item.Properties()));
}