package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderDispatcher.class)
public abstract class EntityRenderDispatcherMixin {
    @Inject(method = "shouldRender", at = @At("HEAD"), cancellable = true)
    private <E extends Entity> void onShouldRender(E entity, Frustum frustum, double x, double y, double z, float tickDelta, CallbackInfoReturnable<Boolean> cir) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (!config.entityCulling || entity == null) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (entity == client.player || entity == client.getCameraEntity()) {
            return;
        }
        if (client.player != null && (entity.isPassengerOfSameVehicle(client.player) || client.player.isPassengerOfSameVehicle(entity))) {
            return;
        }
        if (entity.isCurrentlyGlowing() || entity.hasCustomName()) {
            return;
        }
        if (entity.getType() == EntityTypes.ENDER_DRAGON || entity.getType() == EntityTypes.WITHER) {
            return;
        }
        double distSq = entity.distanceToSqr(x, y, z);
        double maxDist = 64.0 * config.entityDistanceMultiplier;
        if (distSq > maxDist * maxDist) {
            cir.setReturnValue(false);
        }
    }
}
