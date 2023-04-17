/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.sashiro.radioactiveores.RadioactiveOres;
import net.sashiro.radioactiveores.attributes.Attributes;
import net.sashiro.radioactiveores.block.Radioactive;
import net.sashiro.radioactiveores.block.RadioactiveBlock;
import net.sashiro.radioactiveores.effects.MobEffects;
import net.sashiro.radioactiveores.item.RadioactiveBlockItem;
import net.sashiro.radioactiveores.item.RadioactiveItem;
import net.sashiro.radioactiveores.util.Config;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

@SuppressWarnings({"rawtypes", "unchecked", "DataFlowIssue"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = RadioactiveOres.MOD_ID)
public class ModEvents {
    private static int tick = 0;
    private static int tick2 = 0;

    @NotNull
    private static MobEffectInstance addRadiation(int amplifier) {
        return new MobEffectInstance(MobEffects.RADIATION_EFFECT.get(), 6000, amplifier);
    }

    private static void applyRadiationEffect(LivingEntity entity, double radiationLevel, boolean hasRadiationEffect, int amplifier) {
        if (entity.hasEffect(MobEffects.RADIATION_BLOCKER_EFFECT.get())) {
            if (hasRadiationEffect) entity.removeEffect(MobEffects.RADIATION_EFFECT.get());
            return;
        }
        if (hasRadiationEffect) {
            if (amplifier == 0 && radiationLevel >= 300) {
                entity.removeEffect(MobEffects.RADIATION_EFFECT.get());
                entity.addEffect(addRadiation(1));
            } else if (amplifier == 1 && radiationLevel >= 600) {
                entity.removeEffect(MobEffects.RADIATION_EFFECT.get());
                entity.addEffect(addRadiation(2));
            }
        } else {
            if (radiationLevel >= 100 && radiationLevel < 300) entity.addEffect(addRadiation(0));
            if (radiationLevel >= 300 && radiationLevel < 600) entity.addEffect(addRadiation(1));
            if (radiationLevel >= 600) entity.addEffect(addRadiation(2));
        }
    }

    @SubscribeEvent
    public void modifyAttributes(EntityAttributeModificationEvent event) {
        EntityType[] ENTITIES = new EntityType[]{EntityType.ALLAY, EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE, EntityType.CAT, EntityType.CAMEL, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.FROG, EntityType.GHAST, EntityType.GIANT, EntityType.GLOW_SQUID, EntityType.GOAT, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MULE, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.RAVAGER, EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SLIME, EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER, EntityType.TADPOLE, EntityType.TRADER_LLAMA, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VEX, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WARDEN, EntityType.WITCH, EntityType.WOLF, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN, EntityType.PLAYER};
        for (EntityType entity : ENTITIES) {
            event.add(entity, Attributes.RADIATION_ATTRIBUTE.get(), 0);
        }
    }

    @SubscribeEvent
    public void onLivingTick(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();

        if (!entity.getAttributes().hasAttribute(Attributes.RADIATION_ATTRIBUTE.get())) return;
        if (entity instanceof Player player && player.isCreative()) return;
        if (entity.isInvulnerable()) return;

        AttributeInstance instance = entity.getAttribute(Attributes.RADIATION_ATTRIBUTE.get());
        int amplifier = -1;
        double radiationLevel = instance.getValue();
        boolean hasRadiationEffect = entity.hasEffect(MobEffects.RADIATION_EFFECT.get());

        if (hasRadiationEffect) amplifier = entity.getEffect(MobEffects.RADIATION_EFFECT.get()).getAmplifier();

        applyRadiationEffect(entity, radiationLevel, hasRadiationEffect, amplifier);

        if (tick2 >= 20) {
            if (entity instanceof Player player) {
                ArrayList<RadioactiveItem> radioactiveItems = getRadioactiveItems(player);
                ArrayList<RadioactiveBlockItem> radioactiveBlockItems = getRadioactiveBlockItems(player);
                for (RadioactiveItem item : radioactiveItems) {
                    int count;
                    if (radioactiveItems.size() == 0) return;
                    ItemStack stack = new ItemStack(item);
                    count = stack.getCount();
                    radiationLevel = radiationLevel + (count * item.getAmplifier());
                }
                for (RadioactiveBlockItem item : radioactiveBlockItems) {
                    int count;
                    if (radioactiveBlockItems.size() == 0) return;
                    ItemStack stack = new ItemStack(item);
                    count = stack.getCount();
                    radiationLevel = radiationLevel + (count * item.getAmplifier());
                }
                instance.setBaseValue(radiationLevel);

            }
        }

        if (tick >= Config.ticksToCheckForRadiation) {
            Block mcBlock;
            RadioactiveBlock block;

            if (entity instanceof Player) {
                mcBlock = testForBlock(entity, Config.playerRadius);
            } else {
                mcBlock = testForBlock(entity, Config.entityRadius);
            }
            if (mcBlock instanceof Radioactive) {
                block = (RadioactiveBlock) mcBlock;
                radiationLevel = radiationLevel + block.getAmplifier();
                instance.setBaseValue(radiationLevel);
            }
        }

        if (tick >= Config.ticksToCheckForRadiation + 1) tick = 0;
        else tick++;

        if (tick2 >= 21) tick2 = 0;
        else tick2++;
    }

    private Block testForBlock(LivingEntity entity, int distance) {
        int x = (int) entity.getX();
        int y = (int) entity.getY();
        int z = (int) entity.getZ();

        for (int dx = x - distance; dx <= x + distance; dx++) {
            for (int dy = y - distance; dy <= y + distance; dy++) {
                for (int dz = z - distance; dz <= z + distance; dz++) {
                    Block block = entity.level.getBlockState(new BlockPos(dx, dy, dz)).getBlock();
                    if (block instanceof Radioactive) {
                        return block;
                    }
                }
            }
        }
        return Blocks.AIR;
    }

    private ArrayList<RadioactiveItem> getRadioactiveItems(Player player) {
        ArrayList<RadioactiveItem> radioactiveItems = new ArrayList<>();
        NonNullList<ItemStack> items = player.getInventory().items;
        for (ItemStack inventoryItem : items) {
            if (inventoryItem.getItem() instanceof Radioactive) {
                if (inventoryItem.getItem() instanceof RadioactiveItem)
                    radioactiveItems.add((RadioactiveItem) inventoryItem.getItem());
            }
        }
        return radioactiveItems;
    }

    private ArrayList<RadioactiveBlockItem> getRadioactiveBlockItems(Player player) {
        ArrayList<RadioactiveBlockItem> radioactiveItems = new ArrayList<>();
        NonNullList<ItemStack> items = player.getInventory().items;
        for (ItemStack inventoryItem : items) {
            if (inventoryItem.getItem() instanceof Radioactive) {
                if (inventoryItem.getItem() instanceof RadioactiveBlockItem)
                    radioactiveItems.add((RadioactiveBlockItem) inventoryItem.getItem());
            }
        }
        return radioactiveItems;
    }
}
