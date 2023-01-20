package net.sashiro.radioactiveores.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;

import static net.sashiro.radioactiveores.RadioactiveOres.*;

@SuppressWarnings("DataFlowIssue")
public class RadiationEffect extends MobEffect {
    static int tick = 0;

    public RadiationEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getAttributes().hasAttribute(RADIATION_ATTRIBUTE.get())) {
            int radiationLevel = (int) livingEntity.getAttribute(RADIATION_ATTRIBUTE.get()).getValue();

            if (livingEntity instanceof Player player) player.causeFoodExhaustion(0.01F * (float) (amplifier + 1));

            if (radiationLevel > 100 && radiationLevel < 200) {
                if (livingEntity.getHealth() > 15.0F) livingEntity.hurt(RADIATION, 1.0F);
            }
            if (radiationLevel > 200 && radiationLevel < 300) {
                if (livingEntity.getHealth() > 10.0F) livingEntity.hurt(RADIATION, 1.0F);
            }
            if (radiationLevel > 300 && radiationLevel < 400) {
                if (livingEntity.getHealth() > 5.0F) livingEntity.hurt(RADIATION, 1.0F);
            }
            if (radiationLevel > 400) {
                if (livingEntity.getHealth() > 1.0F) livingEntity.hurt(RADIATION, 1.0F);
            }

            if (tick == 20) {
                AttributeInstance instance = livingEntity.getAttribute(RADIATION_ATTRIBUTE.get());
                radiationLevel = (int) instance.getBaseValue();

                if ((radiationLevel >= 150) && radiationLevel < 300) {
                    radiationLevel = radiationLevel + 1;
                    instance.setBaseValue(radiationLevel);
                } else if (radiationLevel >= 300 && radiationLevel < 600) {
                    radiationLevel = radiationLevel + 2;
                    instance.setBaseValue(radiationLevel);
                } else if (radiationLevel >= 600) {
                    radiationLevel = radiationLevel + 3;
                    instance.setBaseValue(radiationLevel);
                }
                //if (hasRadiationBlocker && radiationLevel > 0) instance.setBaseValue(radiationLevel - 1);

                if (radiationLevel >= 1000) {
                    radiationLevel = 0;
                    instance.setBaseValue(radiationLevel);
                    livingEntity.removeEffect(RADIATION_EFFECT.get());
                    livingEntity.hurt(RADIATION, Float.MAX_VALUE);
                }
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
