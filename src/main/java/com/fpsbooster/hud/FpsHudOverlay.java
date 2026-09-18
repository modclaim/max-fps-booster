package com.fpsbooster.hud;

import com.fpsbooster.config.FpsBoosterConfig;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElement;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

public class FpsHudOverlay implements HudElement {
    private static long lastSampleTime = System.currentTimeMillis();
    private static int frameCount = 0;
    private static int calculatedFps = 0;
    private static int minFps = 999;
    private static int maxFps = 0;
    private static long lastFrameNano = System.nanoTime();
    private static double frameTimeMs = 0.0;

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (!config.hudEnabled) {
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client.getDebugOverlay() != null && client.getDebugOverlay().showDebugScreen()) {
            return;
        }

        long nowNano = System.nanoTime();
        long diffNano = nowNano - lastFrameNano;
        lastFrameNano = nowNano;
        if (diffNano > 0) {
            frameTimeMs = diffNano / 1_000_000.0;
        }

        frameCount++;
        long nowMs = System.currentTimeMillis();
        if (nowMs - lastSampleTime >= 1000) {
            calculatedFps = frameCount;
            if (calculatedFps < minFps) {
                minFps = calculatedFps;
            }
            if (calculatedFps > maxFps) {
                maxFps = calculatedFps;
            }
            frameCount = 0;
            lastSampleTime = nowMs;
        }

        int displayFps = client.getFps();
        if (displayFps <= 0) {
            displayFps = calculatedFps;
        }

        int color;
        if (config.dynamicColor) {
            if (displayFps >= config.highFpsThreshold) {
                color = config.highFpsColor;
            } else if (displayFps >= config.mediumFpsThreshold) {
                color = config.mediumFpsColor;
            } else {
                color = config.lowFpsColor;
            }
        } else {
            color = 0xFFFFFF;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(displayFps).append(" FPS");

        if (config.showMinMaxFps) {
            sb.append(" [").append(minFps).append("/").append(maxFps).append("]");
        }

        if (config.showFrameTime) {
            sb.append(String.format(" (%.1f ms)", frameTimeMs));
        }

        String text = sb.toString();
        Font font = client.font;
        int textWidth = font.width(text);
        int textHeight = font.lineHeight;

        int windowWidth = client.getWindow().getGuiScaledWidth();
        int windowHeight = client.getWindow().getGuiScaledHeight();

        int x = 4;
        int y = 4;

        switch (config.hudPosition) {
            case TOP_LEFT -> {
                x = 4;
                y = 4;
            }
            case TOP_RIGHT -> {
                x = windowWidth - textWidth - 4;
                y = 4;
            }
            case BOTTOM_LEFT -> {
                x = 4;
                y = windowHeight - textHeight - 4;
            }
            case BOTTOM_RIGHT -> {
                x = windowWidth - textWidth - 4;
                y = windowHeight - textHeight - 4;
            }
        }

        graphics.text(font, text, x, y, color, true);
    }
}
