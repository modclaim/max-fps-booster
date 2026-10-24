package com.fpsbooster.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.IOException;
import java.io.Reader;
import java.nio.file.AtomicMoveNotSupportedException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

public class FpsBoosterConfig {
    public enum HudPosition { TOP_LEFT, TOP_RIGHT, BOTTOM_LEFT, BOTTOM_RIGHT }

    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    private static final System.Logger LOGGER = System.getLogger("Max FPS Booster");
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
    public int highFpsColor = 0xFF55FF55;
    public int mediumFpsColor = 0xFFFFFF55;
    public int lowFpsColor = 0xFFFF5555;
    public boolean particleCulling = true;
    public int maxParticleDistance = 32;
    public boolean entityCulling = true;
    public float entityDistanceMultiplier = 0.8f;
    public boolean blockEntityCulling = true;
    public int maxBlockEntityDistance = 48;
    // Retained for existing configurations; automatic forced GC is intentionally disabled.
    public boolean autoRamClean = false;
    public boolean cleanOnWorldExit = true;
    public float ramCleanThreshold = 0.75f;

    private static Path configFile() {
        return FabricLoader.getInstance().getConfigDir().resolve("max-fps-booster.json");
    }

    public static FpsBoosterConfig getInstance() {
        if (INSTANCE == null) INSTANCE = load();
        return INSTANCE;
    }

    public static FpsBoosterConfig load() { return load(configFile()); }

    public static FpsBoosterConfig load(Path path) {
        FpsBoosterConfig config = new FpsBoosterConfig();
        if (Files.exists(path)) {
            try (Reader reader = Files.newBufferedReader(path)) {
                FpsBoosterConfig parsed = GSON.fromJson(reader, FpsBoosterConfig.class);
                if (parsed == null) throw new IllegalArgumentException("Empty configuration");
                parsed.validateAndSanitize();
                return parsed;
            } catch (IOException | RuntimeException e) {
                LOGGER.log(System.Logger.Level.WARNING, "Cannot read FPS configuration; using defaults", e);
                try {
                    Files.copy(path, path.resolveSibling(path.getFileName() + ".corrupt." + System.currentTimeMillis() + ".bak"));
                } catch (IOException backupError) {
                    LOGGER.log(System.Logger.Level.WARNING, "Cannot back up configuration; preserving original file", backupError);
                    return config;
                }
            }
        }
        config.save(path);
        return config;
    }

    public void validateAndSanitize() {
        if (hudPosition == null) hudPosition = HudPosition.TOP_LEFT;
        hudScale = Float.isFinite(hudScale) ? Math.clamp(hudScale, 0.5f, 3.0f) : 1.0f;
        entityDistanceMultiplier = Float.isFinite(entityDistanceMultiplier)
                ? Math.clamp(entityDistanceMultiplier, 0.1f, 4.0f) : 0.8f;
        ramCleanThreshold = Float.isFinite(ramCleanThreshold) ? Math.clamp(ramCleanThreshold, 0.5f, 0.95f) : 0.75f;
        mediumFpsThreshold = Math.clamp(mediumFpsThreshold, 1, 999);
        highFpsThreshold = Math.clamp(highFpsThreshold, mediumFpsThreshold + 1, 1000);
        maxParticleDistance = Math.clamp(maxParticleDistance, 1, 256);
        maxBlockEntityDistance = Math.clamp(maxBlockEntityDistance, 1, 256);
        if ((highFpsColor & 0xFF000000) == 0) highFpsColor |= 0xFF000000;
        if ((mediumFpsColor & 0xFF000000) == 0) mediumFpsColor |= 0xFF000000;
        if ((lowFpsColor & 0xFF000000) == 0) lowFpsColor |= 0xFF000000;
    }

    public void save() { save(configFile()); }

    public void save(Path path) {
        Path temporary = null;
        try {
            validateAndSanitize();
            Path target = path.toAbsolutePath();
            Files.createDirectories(target.getParent());
            temporary = Files.createTempFile(target.getParent(), "max-fps-booster-", ".tmp");
            Files.writeString(temporary, GSON.toJson(this));
            try {
                Files.move(temporary, target, StandardCopyOption.ATOMIC_MOVE, StandardCopyOption.REPLACE_EXISTING);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temporary, target, StandardCopyOption.REPLACE_EXISTING);
            }
        } catch (IOException | RuntimeException e) {
            LOGGER.log(System.Logger.Level.WARNING, "Cannot save FPS configuration", e);
        } finally {
            if (temporary != null) {
                try { Files.deleteIfExists(temporary); } catch (IOException ignored) { }
            }
        }
    }
}
