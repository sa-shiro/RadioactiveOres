package net.sashiro.radioactiveores.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.AttributeInstance;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.sashiro.radioactiveores.attributes.Attributes;
import net.sashiro.radioactiveores.item.RadioactiveItems;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("DataFlowIssue")
public class RadiationEffect extends MobEffect {
    private static int tick = 0;

    public RadiationEffect() {
        super(MobEffectCategory.HARMFUL, 1965873);
    }

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.getAttributes().hasAttribute(Attributes.RADIATION_ATTRIBUTE.get()) && !livingEntity.hasEffect(MobEffects.RADIATION_BLOCKER_EFFECT.get()) && !livingEntity.isInvulnerable()) {
            double radiationLevel = livingEntity.getAttribute(Attributes.RADIATION_ATTRIBUTE.get()).getValue();

            if (livingEntity instanceof Player player) player.causeFoodExhaustion(0.01F * (float) (amplifier + 1));

            if (radiationLevel > 100 && radiationLevel < 200) {
                if (livingEntity.getHealth() > 15.0F) livingEntity.hurt(DamageSources.RADIATION, 1.0F);
            }
            if (radiationLevel > 200 && radiationLevel < 300) {
                if (livingEntity.getHealth() > 10.0F) livingEntity.hurt(DamageSources.RADIATION, 1.0F);
            }
            if (radiationLevel > 300 && radiationLevel < 400) {
                if (livingEntity.getHealth() > 5.0F) livingEntity.hurt(DamageSources.RADIATION, 1.0F);
            }
            if (radiationLevel > 400) {
                if (livingEntity.getHealth() > 1.0F) livingEntity.hurt(DamageSources.RADIATION, 1.0F);
            }

            if (tick == 20) {
                AttributeInstance instance = livingEntity.getAttribute(Attributes.RADIATION_ATTRIBUTE.get());
                radiationLevel = instance.getBaseValue();

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

                if (radiationLevel >= 1000) {
                    radiationLevel = 0;
                    instance.setBaseValue(radiationLevel);
                    livingEntity.removeEffect(MobEffects.RADIATION_EFFECT.get());
                    livingEntity.hurt(DamageSources.RADIATION, Float.MAX_VALUE);
                }
                tick = 0;
            }
            tick++;
        }
        super.applyEffectTick(livingEntity, amplifier);
    }

    @Override
    public List<ItemStack> getCurativeItems() {
        ArrayList<ItemStack> curativeItems = new ArrayList<>();
        curativeItems.add(new ItemStack(RadioactiveItems.RADIATION_BLOCKER.get()));
        return curativeItems;
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
