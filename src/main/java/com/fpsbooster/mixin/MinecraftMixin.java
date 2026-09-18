package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import com.fpsbooster.memory.MemoryOptimizer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public abstract class MinecraftMixin {
    @Inject(method = "clearClientLevel", at = @At("TAIL"))
    private void onClearClientLevel(Screen screen, CallbackInfo ci) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (config.cleanOnWorldExit) {
            MemoryOptimizer.cleanAsync((Minecraft) (Object) this);
        }
    }
}
