package net.sashiro.radioactiveores.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.sashiro.radioactiveores.RadioactiveOres;

public class MobEffects {
    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, RadioactiveOres.MOD_ID);

    public static void register(IEventBus bus) {
        MOB_EFFECTS.register(bus);
    }

    public static final RegistryObject<MobEffect> RADIATION_BLOCKER_EFFECT = MOB_EFFECTS.register("radiation_blocker", RadiationBlockerEffect::new);
    public static final RegistryObject<MobEffect> RADIATION_EFFECT = MOB_EFFECTS.register("radiation", () -> new RadiationEffect()
            .addAttributeModifier(Attributes.ATTACK_DAMAGE, "22653B89-116E-49DC-9B6B-9971489B5BE5", 0.0D, AttributeModifier.Operation.ADDITION)
            .addAttributeModifier(Attributes.MOVEMENT_SPEED, "7107DE5E-7CE8-4030-940E-514C1F160890", -0.15F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.ATTACK_SPEED, "55FCED67-E92A-486E-9800-B47F202C4386", -0.1F, AttributeModifier.Operation.MULTIPLY_TOTAL)
            .addAttributeModifier(Attributes.LUCK, "CC5AF142-2BD2-4215-B636-2605AED11727", -1.0D, AttributeModifier.Operation.ADDITION)
    );
}
