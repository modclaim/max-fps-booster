package com.fpsbooster.memory;

import com.fpsbooster.config.FpsBoosterConfig;
import net.minecraft.client.Minecraft;

import java.util.concurrent.CompletableFuture;

public class MemoryOptimizer {
    private static int tickCounter = 0;
    private static long lastCleanTime = 0;

    public static void tick(Minecraft client) {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        if (!config.autoRamClean) {
            return;
        }
        tickCounter++;
        if (tickCounter < 100) {
            return;
        }
        tickCounter = 0;

        long now = System.currentTimeMillis();
        if (now - lastCleanTime < 30000) {
            return;
        }

        Runtime runtime = Runtime.getRuntime();
        long max = runtime.maxMemory();
        long total = runtime.totalMemory();
        long free = runtime.freeMemory();
        long used = total - free;

        if (max > 0) {
            float ratio = (float) used / (float) max;
            if (ratio >= config.ramCleanThreshold) {
                cleanAsync(client);
                lastCleanTime = now;
            }
        }
    }

    public static void cleanAsync(Minecraft client) {
        CompletableFuture.runAsync(() -> {
            try {
                if (client != null && client.getDebugOverlay() != null) {
                    client.getDebugOverlay().clearChunkCache();
                }
                System.gc();
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
