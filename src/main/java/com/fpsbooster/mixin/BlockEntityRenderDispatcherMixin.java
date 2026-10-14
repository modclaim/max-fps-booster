package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderDispatcher;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockEntityRenderDispatcher.class)
public abstract class BlockEntityRenderDispatcherMixin {
    @Inject(method = "tryExtractRenderState", at = @At("HEAD"), cancellable = true)
    private <E extends BlockEntity, S extends BlockEntityRenderState> void onTryExtractRenderState(E blockEntity, float tickDelta, ModelFeatureRenderer.CrumblingOverlay crumblingOverlay, boolean isVisible, CallbackInfoReturnable<S> cir) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (config == null || !config.blockEntityCulling || blockEntity == null || blockEntity.isRemoved()) {
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

        BlockPos pos = blockEntity.getBlockPos();
        if (pos == null) {
            return;
        }

        double maxDist = config.maxBlockEntityDistance;
        if (maxDist <= 0) {
            return;
        }

        double dx = (pos.getX() + 0.5) - camPos.x;
        double dy = (pos.getY() + 0.5) - camPos.y;
        double dz = (pos.getZ() + 0.5) - camPos.z;
        double distSq = dx * dx + dy * dy + dz * dz;
        double maxDistSq = maxDist * maxDist;

        if (distSq > maxDistSq) {
            cir.setReturnValue(null);
        }
    }
}
