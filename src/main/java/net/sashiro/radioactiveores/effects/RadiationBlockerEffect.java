/*
 * Copyright (C) 2023 Sashiro Nakayoshi (sashiro) - All Rights Reserved.
 */

package net.sashiro.radioactiveores.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import org.jetbrains.annotations.NotNull;

import static net.sashiro.radioactiveores.attributes.Attributes.RADIATION_ATTRIBUTE;

@SuppressWarnings("DataFlowIssue")
public class RadiationBlockerEffect extends MobEffect {
    private static int tick = 0;

    public RadiationBlockerEffect() {
        super(MobEffectCategory.NEUTRAL, 1965962);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getAttributes().hasAttribute(RADIATION_ATTRIBUTE.get())) {
            int radiationLevel = (int) livingEntity.getAttribute(RADIATION_ATTRIBUTE.get()).getValue();
            AttributeInstance instance = livingEntity.getAttribute(RADIATION_ATTRIBUTE.get());

            if (tick == 20 && radiationLevel > 0) {
                radiationLevel = (int) instance.getBaseValue();
                radiationLevel = radiationLevel - 1;
                instance.setBaseValue(radiationLevel);
                tick = 0;
            }
            tick++;
        }
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
