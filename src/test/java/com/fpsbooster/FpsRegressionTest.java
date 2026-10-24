package com.fpsbooster;

import com.fpsbooster.config.FpsBoosterConfig;
import com.fpsbooster.hud.FrameStats;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.nio.file.*;
import static org.junit.jupiter.api.Assertions.*;

class FpsRegressionTest {
    @TempDir Path directory;

    @Test void malformedConfigIsBackedUpAndRecovered() throws Exception {
        Path file = directory.resolve("config.json");
        Files.writeString(file, "{ broken :::: ");
        assertTrue(FpsBoosterConfig.load(file).hudEnabled);
        try (var paths = Files.list(directory)) {
            Path backup = paths.filter(p -> p.toString().endsWith(".bak")).findFirst().orElseThrow();
            assertEquals("{ broken :::: ", Files.readString(backup));
        }
        assertTrue(FpsBoosterConfig.load(file).hudEnabled);
    }

    @Test void invalidEnumsRangesAndTransparentLegacyColorsAreSanitized() throws Exception {
        Path file = directory.resolve("config.json");
        Files.writeString(file, """
            {"hudPosition":"unknown", "hudScale":-5, "entityDistanceMultiplier":0,
             "mediumFpsThreshold":2000, "highFpsThreshold":-1, "maxParticleDistance":-9,
             "maxBlockEntityDistance":9999, "highFpsColor":5635925}
            """);
        var config = FpsBoosterConfig.load(file);
        assertEquals(FpsBoosterConfig.HudPosition.TOP_LEFT, config.hudPosition);
        assertEquals(0.5f, config.hudScale);
        assertEquals(0.1f, config.entityDistanceMultiplier);
        assertTrue(config.highFpsThreshold > config.mediumFpsThreshold);
        assertEquals(1, config.maxParticleDistance);
        assertEquals(256, config.maxBlockEntityDistance);
        assertEquals(0xFF000000, config.highFpsColor & 0xFF000000);
    }

    @Test void nonFiniteValuesCannotBreakRenderMathOrConfigSaving() {
        var config = new FpsBoosterConfig();
        config.hudScale = Float.NaN;
        config.entityDistanceMultiplier = Float.POSITIVE_INFINITY;
        config.ramCleanThreshold = Float.NaN;
        config.validateAndSanitize();
        assertEquals(1.0f, config.hudScale);
        assertEquals(0.8f, config.entityDistanceMultiplier);
        config.save(directory.resolve("config.json"));
        assertEquals(1.0f, FpsBoosterConfig.load(directory.resolve("config.json")).hudScale);
    }

    @Test void configSavesReplaceAtomicallyWithoutLeavingTemporaryFiles() throws Exception {
        var config = new FpsBoosterConfig();
        Path file = directory.resolve("config.json");
        config.save(file);
        config.showRamUsage = true;
        config.save(file);
        assertTrue(FpsBoosterConfig.load(file).showRamUsage);
        try (var paths = Files.list(directory)) { assertEquals(1, paths.count()); }
    }

    @Test void frameStatsTrackActualFpsAndResetBetweenWorlds() {
        FrameStats stats = new FrameStats();
        assertEquals(0, stats.minimum());
        stats.sample(1200, -1_000_000_000L);
        stats.sample(1500, -990_000_000L);
        assertEquals(1200, stats.minimum());
        assertEquals(1500, stats.maximum());
        assertEquals(10.0, stats.frameTimeMs());
        stats.reset();
        stats.sample(60, 100_000_000_000L);
        assertEquals(60, stats.minimum());
        assertEquals(60, stats.maximum());
        assertEquals(0, stats.frameTimeMs());
    }

    @Test void longMenuPauseDoesNotAppearAsAFrameTimeSpike() {
        FrameStats stats = new FrameStats();
        stats.sample(60, 0);
        stats.sample(60, 5_000_000_000L);
        assertEquals(0, stats.frameTimeMs());
    }
}
