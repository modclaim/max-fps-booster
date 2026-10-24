package com.fpsbooster.hud;

/** Per-session statistics sampled from Minecraft's real FPS counter. */
public final class FrameStats {
    private int minimum = Integer.MAX_VALUE;
    private int maximum;
    private boolean sampled;
    private long previousNs;
    private double frameTimeMs;

    public void sample(int fps, long nowNs) {
        if (fps > 0) {
            minimum = Math.min(minimum, fps);
            maximum = Math.max(maximum, fps);
        }
        if (sampled) {
            long elapsed = nowNs - previousNs;
            frameTimeMs = elapsed > 0 && elapsed < 1_000_000_000L ? elapsed / 1_000_000.0 : 0.0;
        }
        sampled = true;
        previousNs = nowNs;
    }

    public void reset() {
        minimum = Integer.MAX_VALUE;
        maximum = 0;
        sampled = false;
        previousNs = 0;
        frameTimeMs = 0;
    }

    public int minimum() { return minimum == Integer.MAX_VALUE ? 0 : minimum; }
    public int maximum() { return maximum; }
    public double frameTimeMs() { return frameTimeMs; }
}
