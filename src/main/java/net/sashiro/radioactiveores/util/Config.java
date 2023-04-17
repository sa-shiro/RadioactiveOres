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
    public static final ForgeConfigSpec FORGE_CONFIG_SPEC;
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    public static boolean isThoriumEnabled = false;
    public static boolean isUraniumEnabled = true;
    public static boolean isPlutoniumEnabled = false;
    public static int playerRadius = 10;
    public static int entityRadius = 10;
    public static int ticksToCheckForRadiation = 20;

    static {
        createConfig();

        FORGE_CONFIG_SPEC = BUILDER.build();
    }

    public static void loadConfig(ForgeConfigSpec config, String path) {
        final CommentedFileConfig file = CommentedFileConfig.builder(new File(path)).sync().autosave().writingMode(WritingMode.REPLACE).build();
        file.load();
        config.setConfig(file);
        isThoriumEnabled = getBooleanValue("isThoriumEnabled");
        isUraniumEnabled = getBooleanValue("isUraniumEnabled");
        isPlutoniumEnabled = getBooleanValue("isPlutoniumEnabled");
        playerRadius = getIntValue("radiusPlayer");
        entityRadius = getIntValue("radiusEntity");
        ticksToCheckForRadiation = getIntValue("ticksToCheckForRadiation");
    }

    private static boolean getBooleanValue(String option) {
        ForgeConfigSpec.BooleanValue booleanValue = FORGE_CONFIG_SPEC.getValues().get(option);
        return booleanValue.get();
    }

    private static int getIntValue(String option) {
        ForgeConfigSpec.IntValue intValue = FORGE_CONFIG_SPEC.getValues().get(option);
        return intValue.get();
    }

    private static void createConfig() {
        BUILDER.define("isThoriumEnabled", false);
        BUILDER.define("isUraniumEnabled", true);
        BUILDER.define("isPlutoniumEnabled", false);

        BUILDER.comment("Controls the (cubic) Player distance to check for radioactive blocks.").defineInRange("radiusPlayer", 16, 5, 255);
        BUILDER.comment("Controls the (cubic) Entity distance to check for radioactive blocks.").defineInRange("radiusEntity", 10, 5, 255);

        BUILDER.comment("Controls how often to check for radioactive blocks in Ticks. (20 Ticks = 1 second)").defineInRange("ticksToCheckForRadiation", 20, 10, 200);
    }
}