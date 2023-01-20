package net.sashiro.radioactiveores.effects;

import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.util.Random;

public class RadiationEffect extends MobEffect {
    public RadiationEffect(MobEffectCategory mobEffectCategory, int color) {
        super(mobEffectCategory, color);
    }
    private static final Logger LOGGER = LogUtils.getLogger();

    Random random = new Random();
    static int tick = 0;

    @Override
    public void applyEffectTick(@NotNull LivingEntity livingEntity, int amplifier) {
        if (livingEntity.level.isClientSide()) {
            Double x = livingEntity.getX();
            Double y = livingEntity.getY();
            Double z = livingEntity.getZ();
        }
        if (tick == 40) {

            if (livingEntity.getHealth() > 10.0F) {
                livingEntity.hurt(DamageSource.MAGIC, 1.0F);
            }
            if (livingEntity instanceof Player) {
                ((Player) livingEntity).causeFoodExhaustion(0.005F * (float) (amplifier + 1));
            }

            if (amplifier >= 1.0F && !livingEntity.hasEffect(MobEffects.CONFUSION)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0));
            }
            if (amplifier >= 2.0F && !livingEntity.hasEffect(MobEffects.BLINDNESS)) {
                livingEntity.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 400));
            }


            tick = 0;
        }
        tick++;
        super.applyEffectTick(livingEntity, amplifier);
    }

    public boolean hasRadioactiveItemInInventory(ItemStack item) {
        Player player = Minecraft.getInstance().player;

        assert player != null;
        return player.getInventory().contains(item);
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
