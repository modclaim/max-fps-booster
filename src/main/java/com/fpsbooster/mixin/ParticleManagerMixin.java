package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.ParticleEngine;
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
        if (!config.particleCulling) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.gameRenderer != null && client.gameRenderer.mainCamera() != null) {
            Vec3 camPos = client.gameRenderer.mainCamera().position();
            double dx = particle.getBoundingBox().getCenter().x - camPos.x;
            double dy = particle.getBoundingBox().getCenter().y - camPos.y;
            double dz = particle.getBoundingBox().getCenter().z - camPos.z;
            double distSq = dx * dx + dy * dy + dz * dz;
            double maxDist = config.maxParticleDistance;
            if (distSq > maxDist * maxDist) {
                ci.cancel();
            }
        }
    }
}
