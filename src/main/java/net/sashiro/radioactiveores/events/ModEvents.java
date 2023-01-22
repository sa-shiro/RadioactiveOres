package net.sashiro.radioactiveores.events;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
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

@SuppressWarnings({"rawtypes", "unchecked", "DataFlowIssue"})
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, modid = RadioactiveOres.MOD_ID)
public class ModEvents {
    private static int tick = 0;

    @SubscribeEvent
    public void modifyAttributes(EntityAttributeModificationEvent event) {
        EntityType[] ENTITIES = new EntityType[]{EntityType.ALLAY, EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE, EntityType.CAT, EntityType.CAMEL, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.FROG, EntityType.GHAST, EntityType.GIANT, EntityType.GLOW_SQUID, EntityType.GOAT, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MULE, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.RAVAGER, EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SLIME, EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER, EntityType.TADPOLE, EntityType.TRADER_LLAMA, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VEX, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WARDEN, EntityType.WITCH, EntityType.WOLF, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN, EntityType.PLAYER};
        for (EntityType entity : ENTITIES) {
            event.add(entity, Attributes.RADIATION_ATTRIBUTE.get(), 0);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getAttributes().hasAttribute(Attributes.RADIATION_ATTRIBUTE.get()) && !entity.hasEffect(MobEffects.RADIATION_BLOCKER_EFFECT.get()) && !entity.isInvulnerable()) {
            AttributeInstance instance = entity.getAttribute(Attributes.RADIATION_ATTRIBUTE.get());
            double radiationLevel = instance.getValue();
            boolean hasRadiationEffect = entity.hasEffect(MobEffects.RADIATION_EFFECT.get());
            if (tick == 20 || tick == 40) {
                if ((hasRadiationEffect || radiationLevel >= 100) && radiationLevel < 300) {
                    if (!hasRadiationEffect)
                        entity.addEffect(new MobEffectInstance(MobEffects.RADIATION_EFFECT.get(), 6000));
                } else if (radiationLevel >= 300 && radiationLevel < 600) {
                    if (!hasRadiationEffect)
                        entity.addEffect(new MobEffectInstance(MobEffects.RADIATION_EFFECT.get(), 6000, 1));
                } else if (radiationLevel >= 600) {
                    if (!hasRadiationEffect)
                        entity.addEffect(new MobEffectInstance(MobEffects.RADIATION_EFFECT.get(), 6000, 3));
                }

                if (entity instanceof Player player) {
                    if (getInventoryItem(player) instanceof Radioactive) {
                        radiationLevel = radiationLevel + 1;
                        instance.setBaseValue(radiationLevel);

                    }
                }
            }
            if (tick == 50) {
                Block mcBlock;
                RadioactiveBlock block;

                if (entity instanceof Player) {
                    mcBlock = testFor(entity, 3);
                } else {
                    mcBlock = testFor(entity, 2);
                }
                if (mcBlock instanceof Radioactive) {
                    block = (RadioactiveBlock) mcBlock;
                    radiationLevel = radiationLevel + block.getAmplifier();
                    instance.setBaseValue(radiationLevel);
                }
            }
            if (tick == 50) tick = 0;
            else tick++;
        }
        if (entity.hasEffect(MobEffects.RADIATION_EFFECT.get()) && entity.hasEffect(MobEffects.RADIATION_BLOCKER_EFFECT.get())) {
            entity.removeEffect(MobEffects.RADIATION_EFFECT.get());
        }
    }

    private Block testFor(LivingEntity entity, int distance) {
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

    private Item getInventoryItem(Player player) {
        NonNullList<ItemStack> items = player.getInventory().items;
        for (ItemStack i : items) {
            if (i.getItem() instanceof Radioactive) {
                return i.getItem();
            }
        }
        return Items.AIR;
    }
}
