package com.fpsbooster.hud;

import com.fpsbooster.config.FpsBoosterConfig;
import com.fpsbooster.memory.MemoryOptimizer;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;

import java.util.Locale;

public class FpsHudOverlay {
    private static final FrameStats STATS = new FrameStats();

    public static void reset() { STATS.reset(); }

    public static void render(GuiGraphicsExtractor graphics, DeltaTracker deltaTracker) {
        if (graphics == null) {
            reset();
            return;
        }

        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (config == null || !config.hudEnabled) {
            reset();
            return;
        }

        Minecraft client = Minecraft.getInstance();
        if (client == null || client.font == null || client.level == null || client.player == null) {
            reset();
            return;
        }

        if (client.gui != null && client.gui.hud != null && client.gui.hud.isHidden()) {
            reset();
            return;
        }

        if (client.getDebugOverlay() != null && client.getDebugOverlay().showDebugScreen()) {
            reset();
            return;
        }

        int displayFps = Math.max(0, client.getFps());
        STATS.sample(displayFps, System.nanoTime());

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
            color = 0xFFFFFFFF;
        }

        if ((color & 0xFF000000) == 0) {
            color |= 0xFF000000;
        }

        StringBuilder sb = new StringBuilder();
        sb.append(displayFps).append(" FPS");

        if (config.showMinMaxFps) {
            sb.append(" [").append(STATS.minimum()).append("/").append(STATS.maximum()).append("]");
        }

        if (config.showFrameTime) {
            sb.append(String.format(Locale.ROOT, " (%.1f ms)", STATS.frameTimeMs()));
        }

        if (config.showRamUsage) {
            sb.append(" | ").append(MemoryOptimizer.getUsedMemoryMb()).append("MB RAM");
        }

        String text = sb.toString();
        Font font = client.font;
        int textWidth = font.width(text);
        int textHeight = font.lineHeight;

        int windowWidth = (int) (graphics.guiWidth() / config.hudScale);
        int windowHeight = (int) (graphics.guiHeight() / config.hudScale);

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

        graphics.pose().pushMatrix();
        try {
            graphics.pose().scale(config.hudScale, config.hudScale);
            graphics.text(font, text, Math.max(0, x), Math.max(0, y), color, true);
        } finally {
            graphics.pose().popMatrix();
        }
    }
}
