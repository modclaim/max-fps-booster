package com.fpsbooster.mixin;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.DebugScreenOverlay;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(DebugScreenOverlay.class)
public abstract class DebugScreenOverlayMixin {
    @Inject(method = "extractLines", at = @At("HEAD"))
    private void onExtractLines(GuiGraphicsExtractor graphics, List<String> lines, boolean left, int width, CallbackInfo ci) {
        if (left) {
            FpsBoosterConfig config = FpsBoosterConfig.getInstance();
            lines.add("§a[Max FPS Booster]§r EntityCull: " + (config.entityCulling ? "§aON" : "§cOFF") + "§r | BlockCull: " + (config.blockEntityCulling ? "§aON" : "§cOFF") + "§r | ParticleCull: " + (config.particleCulling ? "§aON" : "§cOFF"));
        }
    }
}
