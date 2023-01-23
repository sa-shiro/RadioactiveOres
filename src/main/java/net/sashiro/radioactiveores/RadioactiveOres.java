package net.sashiro.radioactiveores;

import com.mojang.logging.LogUtils;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.fml.loading.FMLPaths;
import net.sashiro.radioactiveores.attributes.Attributes;
import net.sashiro.radioactiveores.block.RadioactiveBlocks;
import net.sashiro.radioactiveores.effects.MobEffects;
import net.sashiro.radioactiveores.events.ModEvents;
import net.sashiro.radioactiveores.item.RadioactiveItems;
import net.sashiro.radioactiveores.util.Config;
import org.slf4j.Logger;

import static net.sashiro.radioactiveores.util.Config.*;

@SuppressWarnings({"unused", "DuplicatedCode"})
@Mod(RadioactiveOres.MOD_ID)
public class RadioactiveOres {
    public static final String MOD_ID = "radioactiveores";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModEvents instance = new ModEvents();
    private static final String CONFIG = "radioactive_ores.toml";

    public RadioactiveOres() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        ModLoadingContext.get().registerConfig(ModConfig.Type.SERVER, Config.CONFIG_ENABLED_BLOCKS, CONFIG);

        Config.loadConfig(Config.CONFIG_ENABLED_BLOCKS, FMLPaths.CONFIGDIR.get().resolve(CONFIG).toString());

        RadioactiveBlocks.register(modEventBus);
        RadioactiveItems.register(modEventBus);
        MobEffects.register(modEventBus);
        Attributes.register(modEventBus);


        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(instance);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(instance::modifyAttributes);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.FOOD_AND_DRINKS) {
            event.accept(RadioactiveItems.RADIATION_BLOCKER);
        }

        if (isThoriumEnabled) {
            if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                event.accept(RadioactiveBlocks.THORIUM_BLOCK);
            }
            if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
                event.accept(RadioactiveBlocks.RAW_THORIUM_BLOCK);
                event.accept(RadioactiveBlocks.THORIUM_ORE);
                event.accept(RadioactiveBlocks.DEEPSLATE_THORIUM_ORE);
            }
            if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
                event.accept(RadioactiveItems.RAW_THORIUM);
                event.accept(RadioactiveItems.THORIUM_INGOT);
                event.accept(RadioactiveItems.THORIUM_NUGGET);
            }
        }
        if (isUraniumEnabled) {
            if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                event.accept(RadioactiveBlocks.URANIUM_BLOCK);
            }
            if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
                event.accept(RadioactiveBlocks.RAW_URANIUM_BLOCK);
                event.accept(RadioactiveBlocks.URANIUM_ORE);
                event.accept(RadioactiveBlocks.DEEPSLATE_URANIUM_ORE);
            }
            if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
                event.accept(RadioactiveItems.RAW_URANIUM);
                event.accept(RadioactiveItems.URANIUM_INGOT);
                event.accept(RadioactiveItems.URANIUM_NUGGET);
            }
        }
        if (isPlutoniumEnabled) {
            if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
                event.accept(RadioactiveBlocks.PLUTONIUM_BLOCK);
            }
            if (event.getTab() == CreativeModeTabs.NATURAL_BLOCKS) {
                event.accept(RadioactiveBlocks.RAW_PLUTONIUM_BLOCK);
                event.accept(RadioactiveBlocks.PLUTONIUM_ORE);
                event.accept(RadioactiveBlocks.DEEPSLATE_PLUTONIUM_ORE);
            }
            if (event.getTab() == CreativeModeTabs.INGREDIENTS) {
                event.accept(RadioactiveItems.RAW_PLUTONIUM);
                event.accept(RadioactiveItems.PLUTONIUM_INGOT);
                event.accept(RadioactiveItems.PLUTONIUM_NUGGET);
            }
        }
    }
}
