package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Mob.class)
public abstract class MobEntityMixin extends LivingEntity {
    private int fpsBoosterTickSkip = 0;

    protected MobEntityMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (!config.smartMobTicking || this.level().isClientSide()) {
            return;
        }
        Mob self = (Mob) (Object) this;
        if (self.getTarget() != null || self.hasCustomName() || self.isLeashed()) {
            return;
        }
        if (self.level().getNearestPlayer(self, (double) config.mobTickDistance) == null) {
            fpsBoosterTickSkip++;
            if ((fpsBoosterTickSkip & 1) != 0) {
                ci.cancel();
            }
        }
    }
}
