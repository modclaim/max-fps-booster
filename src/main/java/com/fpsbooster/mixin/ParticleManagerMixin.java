package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ParticleEngine.class)
public abstract class ParticleManagerMixin {
    @Inject(method = "add", at = @At("HEAD"), cancellable = true)
    private void onAdd(Particle particle, CallbackInfo ci) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (!config.particleCulling || particle == null) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.level == null || client.gameRenderer == null || client.gameRenderer.mainCamera() == null) {
            return;
        }
        Vec3 camPos = client.gameRenderer.mainCamera().position();
        if (camPos == null) {
            return;
        }
        AABB box = particle.getBoundingBox();
        if (box == null) {
            return;
        }
        Vec3 center = box.getCenter();
        if (center == null) {
            return;
        }
        double dx = center.x - camPos.x;
        double dy = center.y - camPos.y;
        double dz = center.z - camPos.z;
        double distSq = dx * dx + dy * dy + dz * dz;
        double maxDist = config.maxParticleDistance;
        if (distSq > maxDist * maxDist) {
            ci.cancel();
        }
    }
}
