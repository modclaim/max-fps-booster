package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(MinecraftServer.class)
public abstract class MinecraftServerMixin {
    private long lastSaveTime = 0;

    @Inject(method = "saveAllChunks", at = @At("HEAD"), cancellable = true)
    private void onSaveAllChunks(boolean suppressLogs, boolean flush, boolean force, CallbackInfoReturnable<Boolean> cir) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (config.smoothAutoSave && !force) {
            long now = System.currentTimeMillis();
            if (now - lastSaveTime < 60000) {
                cir.setReturnValue(true);
            } else {
                lastSaveTime = now;
            }
        }
    }
}
