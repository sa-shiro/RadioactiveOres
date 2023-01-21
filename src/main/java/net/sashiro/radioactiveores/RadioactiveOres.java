package net.sashiro.radioactiveores;

import com.mojang.logging.LogUtils;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.CreativeModeTabEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.attributes.RadiationAttribute;
import net.sashiro.radioactiveores.block.RadioactiveOreBlock;
import net.sashiro.radioactiveores.block.RadioactiveOreBlockItem;
import net.sashiro.radioactiveores.effects.RadiationBlockerEffect;
import net.sashiro.radioactiveores.effects.RadiationEffect;
import net.sashiro.radioactiveores.events.ModEvents;
import net.sashiro.radioactiveores.item.RadioactiveItem;
import org.slf4j.Logger;

import java.util.UUID;

@SuppressWarnings("unused")
@Mod(RadioactiveOres.MOD_ID)
public class RadioactiveOres {
    public static final String MOD_ID = "radioactiveores";
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final ModEvents instance = new ModEvents();
    // ---------- R E G I S T R Y ----------
    // Deferred Register
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MOD_ID);
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final DeferredRegister<Attribute> ATTRIBUTES = DeferredRegister.create(ForgeRegistries.ATTRIBUTES, MOD_ID);
    // Registry Objects - Blocks
    public static final RegistryObject<Block> URANIUM_ORE = BLOCKS.register("uranium_ore", () -> new RadioactiveOreBlock(2));
    // Registry Objects - Items
    public static final RegistryObject<BlockItem> URANIUM_ORE_ITEM = ITEMS.register("uranium_ore", () -> new RadioactiveOreBlockItem(URANIUM_ORE.get(), new Item.Properties()));
    public static final RegistryObject<Item> RADIATION_BLOCKER_ITEM = ITEMS.register("radiation_blocker", RadioactiveItem::new);
    // Registry Objects - Mob Effects
    public static final RegistryObject<MobEffect> RADIATION_EFFECT = MOB_EFFECTS.register("radiation", () -> new RadiationEffect()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, AttributeModifier.Operation.ADDITION)
    );
    public static final RegistryObject<MobEffect> RADIATION_BLOCKER_EFFECT = MOB_EFFECTS.register("radiation_blocker", RadiationBlockerEffect::new);
    // Registry Objects - Attributes
    public static final RegistryObject<Attribute> RADIATION_ATTRIBUTE = ATTRIBUTES.register("radiation", () -> new RadiationAttribute(0.0D, 0.0D, 25000).setSyncable(true));
    // ---------- E N D ----------
    // Attribute Modifiers
    public static final UUID RADIATION_MODIFIER_UUID = UUID.fromString("6a782908-ac9f-4626-abb6-feed350435eb");
    public static final AttributeModifier RADIATION_MODIFIER = new AttributeModifier(RADIATION_MODIFIER_UUID, "radiation", 20, AttributeModifier.Operation.ADDITION);
    // Damage Sources
    public static final DamageSource RADIATION = (new DamageSource("radiation")).bypassEnchantments().bypassInvul().bypassArmor().setMagic();

    public RadioactiveOres() {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();

        BLOCKS.register(modEventBus);
        ITEMS.register(modEventBus);
        MOB_EFFECTS.register(modEventBus);
        ATTRIBUTES.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(instance);

        modEventBus.addListener(this::addCreative);
        modEventBus.addListener(instance::modifyAttributes);
    }

    private void addCreative(CreativeModeTabEvent.BuildContents event) {
        if (event.getTab() == CreativeModeTabs.BUILDING_BLOCKS) {
            event.accept(URANIUM_ORE_ITEM);
            event.accept(RADIATION_BLOCKER_ITEM);
        }
    }
}
