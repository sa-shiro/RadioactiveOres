package net.sashiro.radioactiveores;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.*;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.structure.templatesystem.AxisAlignedLinearPosTest;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.event.entity.EntityAttributeModificationEvent;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.attributes.RadiationAttribute;
import net.sashiro.radioactiveores.effects.RadiationEffect;
import org.slf4j.Logger;

import java.util.*;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(RadioactiveOres.MOD_ID)
public class RadioactiveOres {

    public static final String MOD_ID = "radioactiveores";
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);

    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new Block(BlockBehaviour.Properties.of(Material.STONE)));
    public static final RegistryObject<Item> URANIUM_ORE_ITEM = ITEMS.register("uranium_ore", () -> new BlockItem(URANIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<MobEffect> RADIATION_EFFECT = MOB_EFFECTS.register("radiation", () -> new RadiationEffect(MobEffectCategory.HARMFUL, 222)
            //.addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION)
            //.addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", (double)-0.3F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            //.addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", (double)-0.2F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            //.addAttributeModifier(Attributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.5D, AttributeModifier.Operation.ADDITION)
    );
    public static final RegistryObject<Attribute> RADIATION_ATTRIBUTE = ATTRIBUTES.register("radiation", () -> new RadiationAttribute(200.0D, 1.0D).setSyncable(true));


    public RadioactiveOres() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        modEventBus.addListener(this::commonSetup);

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        ATTRIBUTES.register(modEventBus);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        // Register the item to a creative tab
        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(this::modifyAttributes);
    }

    @SubscribeEvent
    public void modifyAttributes(EntityAttributeModificationEvent event) {
        event.add(EntityType.PLAYER, RADIATION_ATTRIBUTE.get(), 0.0D);
    }

    private boolean testForBlock(Player player) {
        ArrayList<BlockPos> posList = new ArrayList<>();
        double x = player.getX();
        double y = player.getX();
        double z = player.getX();

        for (double x1 = x -5; x1 < x +5; x1++) {
            for (double y1 = y - 5; y1 < y + 5; y1++) {
                for (double z1 = z - 5; z1 < z + 5; z1 ++) {
                    posList.add(new BlockPos(x1, y1, z1));
                }
            }
        }

        for (BlockPos pos : posList) {
            BlockState state = player.level.getBlockState(pos);
            if (state.is(Blocks.DIAMOND_BLOCK)) {
                return true;
            }
        }
        return false;
    }

    static int tick = 0;
    @SubscribeEvent
    public void onLivingUpdate(LivingEvent.LivingTickEvent event) {
        if (event.getEntity() instanceof Player player) {
            RadiationAttribute radiationAttribute = (RadiationAttribute) player.getAttribute(RADIATION_ATTRIBUTE.get()).getAttribute();

                double posX = player.getX();
                double posY = player.getY();
                double posZ = player.getZ() + 1;

                BlockPos pos2 = new BlockPos(posX, posY, posZ);
                BlockState state2 = player.level.getBlockState(pos2);

                //if (state2.is(Blocks.DIAMOND_BLOCK)) {
                if (testForBlock(player)) {
                    LOGGER.info("SUCCESS");
                }

                ArrayList<BlockPos> posList = new ArrayList<>();
                double x = player.getX();
                double y = player.getY();
                double z = player.getZ();

                for (double x1 = x - 5; x1 < x +5; x1++) {
                    for (double y1 = y - 5; y1 < y + 5; y1++) {
                        for (double z1 = z - 5; z1 < z + 5; z1 ++) {
                            posList.add(new BlockPos(x1, y1, z1));
                        }
                    }
                }

                for (BlockPos pos : posList) {
                    BlockState state = player.level.getBlockState(pos);
                    if (state.is(Blocks.DIAMOND_BLOCK)) {
                        LOGGER.info("SUCCESS");
                    }
                }



            //if(isPlayerInRadiationArea(player)) {
            if (player.hasEffect(RADIATION_EFFECT.get())) {
                radiationAttribute.setRadiationLevel(Math.min(radiationAttribute.getDefaultValue() + radiationAttribute.getDecayRate(), radiationAttribute.getMaxValue()));
            } else {
                radiationAttribute.decay();
            }
            if (radiationAttribute.getDefaultValue() == radiationAttribute.getMaxValue() && radiationAttribute.isDeadly()) {
                player.hurt(DamageSource.GENERIC, Float.MAX_VALUE);
            }
        }
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        // Some common setup code
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS)
            event.accept(URANIUM_ORE_ITEM);
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call
    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    // You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
    @Mod.EventBusSubscriber(modid = MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
    public static class ClientModEvents {
        @SubscribeEvent
        public static void onClientSetup(FMLClientSetupEvent event) {
            // Some client setup code
        }
    }
}
