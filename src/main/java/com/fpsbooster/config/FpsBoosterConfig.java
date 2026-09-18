package com.fpsbooster.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FpsBoosterConfig {
    public enum HudPosition {
        TOP_LEFT,
        TOP_RIGHT,
        BOTTOM_LEFT,
        BOTTOM_RIGHT
    }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final File CONFIG_FILE = new File(FabricLoader.getInstance().getConfigDir().toFile(), "max-fps-booster.json");
    private static FpsBoosterConfig INSTANCE;

    public boolean hudEnabled = true;
    public HudPosition hudPosition = HudPosition.TOP_LEFT;
    public float hudScale = 1.0f;
    public boolean dynamicColor = true;
    public boolean showFrameTime = false;
    public boolean showMinMaxFps = false;
    public boolean showRamUsage = false;

    public int highFpsThreshold = 60;
    public int mediumFpsThreshold = 30;
    public int highFpsColor = 0x55FF55;
    public int mediumFpsColor = 0xFFFF55;
    public int lowFpsColor = 0xFF5555;

    public boolean particleCulling = true;
    public int maxParticleDistance = 32;

    public boolean entityCulling = true;
    public float entityDistanceMultiplier = 0.8f;

    public boolean blockEntityCulling = true;
    public int maxBlockEntityDistance = 48;

    public boolean autoRamClean = true;
    public boolean cleanOnWorldExit = true;
    public float ramCleanThreshold = 0.75f;

    public static FpsBoosterConfig getInstance() {
        if (INSTANCE == null) {
            INSTANCE = load();
        }
        return INSTANCE;
    }

    public static FpsBoosterConfig load() {
        if (CONFIG_FILE.exists()) {
            try (FileReader reader = new FileReader(CONFIG_FILE)) {
                FpsBoosterConfig config = GSON.fromJson(reader, FpsBoosterConfig.class);
                if (config != null) {
                    return config;
                }
            } catch (IOException ignored) {
            }
        }
        FpsBoosterConfig config = new FpsBoosterConfig();
        config.save();
        return config;
    }

    public void save() {
        try {
            File parent = CONFIG_FILE.getParentFile();
            if (parent != null && !parent.exists()) {
                parent.mkdirs();
            }
            try (FileWriter writer = new FileWriter(CONFIG_FILE)) {
                GSON.toJson(this, writer);
            }
        } catch (IOException ignored) {
        }
    }
}
