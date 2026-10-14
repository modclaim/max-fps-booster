package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Camera;
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
        if (config == null || !config.particleCulling || particle == null) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client == null || client.level == null || client.player == null || client.gameRenderer == null) {
            return;
        }

        if (client.player.isSpectator()) {
            return;
        }

        Camera camera = client.gameRenderer.mainCamera();
        if (camera == null || !camera.isInitialized()) {
            return;
        }

        Vec3 camPos = camera.position();
        if (camPos == null) {
            return;
        }

        AABB box = particle.getBoundingBox();
        if (box == null) {
            return;
        }

        double maxDist = config.maxParticleDistance;
        if (maxDist <= 0) {
            return;
        }

        double centerX = (box.minX + box.maxX) * 0.5;
        double centerY = (box.minY + box.maxY) * 0.5;
        double centerZ = (box.minZ + box.maxZ) * 0.5;
        double dx = centerX - camPos.x;
        double dy = centerY - camPos.y;
        double dz = centerZ - camPos.z;
        double maxDistSq = maxDist * maxDist;

        if (dx * dx + dy * dy + dz * dz > maxDistSq) {
            ci.cancel();
        }
    }
}
