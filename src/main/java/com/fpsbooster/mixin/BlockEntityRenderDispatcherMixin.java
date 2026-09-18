package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
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
        if (!config.blockEntityCulling) {
            return;
        }
        Minecraft client = Minecraft.getInstance();
        if (client.gameRenderer != null && client.gameRenderer.mainCamera() != null) {
            Vec3 camPos = client.gameRenderer.mainCamera().position();
            BlockPos pos = blockEntity.getBlockPos();
            double dx = pos.getX() + 0.5 - camPos.x;
            double dy = pos.getY() + 0.5 - camPos.y;
            double dz = pos.getZ() + 0.5 - camPos.z;
            double distSq = dx * dx + dy * dy + dz * dz;
            double maxDist = config.maxBlockEntityDistance;
            if (distSq > maxDist * maxDist) {
                cir.setReturnValue(null);
            }
        }
    }
}
