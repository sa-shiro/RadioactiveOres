/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.util;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraftforge.common.ForgeConfig;
import net.minecraftforge.common.ForgeConfigSpec;

import java.io.File;

public class Config extends ForgeConfig {
    public static final ForgeConfigSpec CONFIG_ENABLED_BLOCKS;
    private static final ForgeConfigSpec.Builder builderEnabledBlocks = new ForgeConfigSpec.Builder();
    public static boolean isThoriumEnabled = false;
    public static boolean isUraniumEnabled = true;
    public static boolean isPlutoniumEnabled = false;

    static {
        createConfigEnabledBlocks();

        CONFIG_ENABLED_BLOCKS = builderEnabledBlocks.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
        isThoriumEnabled = isEnabled("isThoriumEnabled");
        isUraniumEnabled = isEnabled("isUraniumEnabled");
        isPlutoniumEnabled = isEnabled("isPlutoniumEnabled");
    }

    private static boolean isEnabled(String option) {
        ForgeConfigSpec.BooleanValue booleanValue = CONFIG_ENABLED_BLOCKS.getValues().get(option);
        return booleanValue.get();
    }

    private static void createConfigEnabledBlocks() {
        Config.builderEnabledBlocks.define("isThoriumEnabled", false);
        Config.builderEnabledBlocks.define("isUraniumEnabled", true);
        Config.builderEnabledBlocks.define("isPlutoniumEnabled", false);
    }
}