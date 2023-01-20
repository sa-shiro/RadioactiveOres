package net.sashiro.radioactiveores;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.attributes.RadiationAttribute;
import net.sashiro.radioactiveores.blocks.Radioactive;
import net.sashiro.radioactiveores.blocks.RadioactiveOreBlock;
import net.sashiro.radioactiveores.blocks.RadioactiveOreBlockItem;
import net.sashiro.radioactiveores.effects.RadiationEffect;
import org.slf4j.Logger;

import java.util.UUID;

@SuppressWarnings({"rawtypes", "unchecked", "DataFlowIssue"})
@Mod(RadioactiveOres.MOD_ID)
public class RadioactiveOres {

    public static final String MOD_ID = "radioactiveores";
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final RegistryObject<RadioactiveOreBlock> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new RadioactiveOreBlock(2));
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final RegistryObject<RadioactiveOreBlockItem> URANIUM_ORE_ITEM = ITEMS.register("uranium_ore", () -> new RadioactiveOreBlockItem(URANIUM_ORE.get(), new Item.Properties()));
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final RegistryObject<MobEffect> RADIATION_EFFECT = MOB_EFFECTS.register("radiation", () -> new RadiationEffect(MobEffectCategory.HARMFUL, 1965873)
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, AttributeModifier.Operation.ADDITION)
    );
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);
    public static final RegistryObject<Attribute> RADIATION_ATTRIBUTE = ATTRIBUTES.register("radiation", () -> new RadiationAttribute(0.0D, 0.0D, 25000).setSyncable(true));
    public static final UUID RADIATION_MODIFIER_UUID = UUID.fromString("6a782908-ac9f-4626-abb6-feed350435eb");
    public static final AttributeModifier RADIATION_MODIFIER = new AttributeModifier(RADIATION_MODIFIER_UUID, "radiation", 20, AttributeModifier.Operation.ADDITION);
    public static final DamageSource RADIATION = (new DamageSource("radiation")).bypassEnchantments().bypassInvul().bypassArmor().setMagic();
    private static final Logger LOGGER = LogUtils.getLogger();
    static int tick = 0;

    public RadioactiveOres() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        ATTRIBUTES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::modifyAttributes);
    }

    @SubscribeEvent
    public void modifyAttributes(EntityAttributeModificationEvent event) {
        EntityType[] ENTITIES = new EntityType[]{EntityType.ALLAY, EntityType.AXOLOTL, EntityType.BAT, EntityType.BEE, EntityType.BLAZE, EntityType.CAT, EntityType.CAMEL, EntityType.CAVE_SPIDER, EntityType.CHICKEN, EntityType.COD, EntityType.COW, EntityType.CREEPER, EntityType.DOLPHIN, EntityType.DONKEY, EntityType.DROWNED, EntityType.ELDER_GUARDIAN, EntityType.ENDER_DRAGON, EntityType.ENDERMAN, EntityType.ENDERMITE, EntityType.EVOKER, EntityType.FOX, EntityType.FROG, EntityType.GHAST, EntityType.GIANT, EntityType.GLOW_SQUID, EntityType.GOAT, EntityType.GUARDIAN, EntityType.HOGLIN, EntityType.HORSE, EntityType.ILLUSIONER, EntityType.IRON_GOLEM, EntityType.LLAMA, EntityType.MULE, EntityType.MOOSHROOM, EntityType.OCELOT, EntityType.PANDA, EntityType.PARROT, EntityType.PIG, EntityType.PIGLIN, EntityType.PIGLIN_BRUTE, EntityType.PILLAGER, EntityType.POLAR_BEAR, EntityType.PUFFERFISH, EntityType.RABBIT, EntityType.RAVAGER, EntityType.SALMON, EntityType.SHEEP, EntityType.SHULKER, EntityType.SILVERFISH, EntityType.SLIME, EntityType.SNOW_GOLEM, EntityType.SPIDER, EntityType.SQUID, EntityType.STRAY, EntityType.STRIDER, EntityType.TADPOLE, EntityType.TRADER_LLAMA, EntityType.TROPICAL_FISH, EntityType.TURTLE, EntityType.VEX, EntityType.VILLAGER, EntityType.VINDICATOR, EntityType.WANDERING_TRADER, EntityType.WARDEN, EntityType.WITCH, EntityType.WOLF, EntityType.ZOGLIN, EntityType.ZOMBIE, EntityType.ZOMBIE_HORSE, EntityType.ZOMBIE_VILLAGER, EntityType.ZOMBIFIED_PIGLIN, EntityType.PLAYER};
        for (EntityType entity : ENTITIES) {
            event.add(entity, RADIATION_ATTRIBUTE.get(), 0);
        }
    }

    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.getAttributes().hasAttribute(RADIATION_ATTRIBUTE.get())) {
            AttributeInstance instance = entity.getAttribute(RADIATION_ATTRIBUTE.get());
            int radiationLevel = (int) instance.getValue();
            boolean hasRadiationEffect = entity.hasEffect(RADIATION_EFFECT.get());
            if (tick == 20 || tick == 40) {
                if ((hasRadiationEffect || radiationLevel >= 100) && radiationLevel < 300) {
                    if (!hasRadiationEffect) entity.addEffect(new MobEffectInstance(RADIATION_EFFECT.get(), 6000));
                } else if (radiationLevel >= 300 && radiationLevel < 600) {
                    if (!hasRadiationEffect) entity.addEffect(new MobEffectInstance(RADIATION_EFFECT.get(), 6000, 1));
                } else if (radiationLevel >= 600) {
                    if (!hasRadiationEffect) entity.addEffect(new MobEffectInstance(RADIATION_EFFECT.get(), 6000, 3));
                }

                if (entity instanceof Player player) {
                    if (getInventoryItem(player) instanceof Radioactive) {
                        radiationLevel = radiationLevel + 1;
                        instance.setBaseValue(radiationLevel);
                        LOGGER.info("Has item: " + getInventoryItem(player).getDescription().getString());
                    }
                    LOGGER.info("Radiation Level of " + entity.getName().getString() + ": " + radiationLevel);
                }
            }
            if (tick == 50) {
                Block mcBlock;
                RadioactiveOreBlock block;

                if (entity instanceof Player) {
                    mcBlock = testFor(entity, 3);
                } else {
                    mcBlock = testFor(entity, 2);
                }
                if (mcBlock instanceof Radioactive) {
                    block = (RadioactiveOreBlock) mcBlock;
                    radiationLevel = radiationLevel + (int) block.getAmplifier();
                    instance.setBaseValue(radiationLevel);
                }
            }
            if (tick == 50) tick = 0;
            else tick++;
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

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(URANIUM_ORE_ITEM);
    }
}
