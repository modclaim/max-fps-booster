package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.renderer.texture.SpriteContents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SpriteContents.AnimationState.class)
public abstract class SpriteContentsAnimatorMixin {
    private static int tickCounter = 0;

    @Inject(method = "tick", at = @At("HEAD"), cancellable = true)
    private void onTick(CallbackInfo ci) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (config.throttleAnimations) {
            tickCounter++;
            if ((tickCounter & 1) != 0) {
                ci.cancel();
            }
        }
    }
}
