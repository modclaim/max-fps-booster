package com.fpsbooster.memory;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Minecraft;

import java.util.concurrent.CompletableFuture;

public class MemoryOptimizer {
    private static int tickCounter = 0;
    private static long lastCleanTime = 0;

    public static void tick(Minecraft client) {
        // Passive memory monitor - no disruptive System.gc() calls
    }

    public static void cleanAsync(Minecraft client) {
        CompletableFuture.runAsync(() -> {
            try {
                if (client != null && client.getDebugOverlay() != null) {
                    client.getDebugOverlay().clearChunkCache();
                }
            } catch (Exception ignored) {
            }
        });
    }

    public static long getUsedMemoryMb() {
        Runtime r = Runtime.getRuntime();
        return (r.totalMemory() - r.freeMemory()) / (1024 * 1024);
    }

    public static long getMaxMemoryMb() {
        return Runtime.getRuntime().maxMemory() / (1024 * 1024);
    }

    public static int getMemoryUsagePercent() {
        Runtime r = Runtime.getRuntime();
        long max = r.maxMemory();
        if (max <= 0) {
            return 0;
        }
        long used = r.totalMemory() - r.freeMemory();
        return (int) ((used * 100) / max);
    }
}
