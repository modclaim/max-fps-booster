# Changelog

All notable changes to the **Max FPS booster** mod will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.1.1] - 2026-10-24

- Target the stable Minecraft 26.3 release with Fabric API 0.161.0+26.3 and Java 25.
- Recover malformed configurations with backups; validate enum, numeric and legacy color values.
- Save configurations atomically where supported.
- Apply HUD scale; track real FPS minima/maxima and reset statistics across worlds and hidden HUD intervals.
- Keep optional debug-cache cleanup on the client thread without forced garbage collection.
- Add configuration and frame-statistic regression tests; remove machine-specific JDK paths and unverified performance claims.

## [1.1.0] - 2026-10-14

### Minecraft 26.4 Upgrade, 32-Bit ARGB Color Fixes & Zero-Freeze Stabilization

#### Added
- **Minecraft 26.4 Compatibility**:
  - Full native support for Minecraft `26.4` (`26.4-snapshot-4` / release target).
  - Updated to **Fabric API `0.163.0+26.4`** and **ModMenu `22.0.0-alpha.2`**.
  - Compiled and verified on OpenJDK Java 25.
- **Spectator Mode Protection**:
  - Automatically disables culling while the player is in Spectator mode (`client.player.isSpectator()`), ensuring full visibility when flying through terrain.
- **Mount & Vehicle Culling Guard**:
  - Protected ridden vehicles and passengers (`entity.isPassengerOfSameVehicle`, `hasPassenger`, vehicle checks) from being culled.

#### Fixed
- **32-Bit ARGB HUD Visibility**:
  - Converted all color definitions in `FpsBoosterConfig` and `FpsHudOverlay` to full 32-bit ARGB (`0xFF55FF55`, `0xFFFFFF55`, `0xFFFF5555`, `0xFFFFFFFF`).
  - Fixed issue where 24-bit RGB values resulted in a `0x00` alpha channel, causing the HUD and config text to render completely invisible/transparent.
  - Added auto-sanitization on config load to upgrade legacy config colors.
- **System Freeze & OOM Elimination**:
  - Resolved JVM heap bloat and Swap Thrashing on 6GB RAM desktop setups by constraining Gradle build memory (`-Xmx1024M -XX:+UseG1GC`, `parallel=false`, `workers.max=2`).
  - Fixed dangerous asynchronous OpenGL thread access: moved `client.getDebugOverlay().clearChunkCache()` from `CompletableFuture.runAsync` into the main client thread via `client.execute(...)`.
  - Removed disruptive `System.gc()` invocations to eliminate Stop-The-World micro-stutters and OS lockups.
- **Render Culling Null-Safety & Zero-Allocation Math**:
  - Fixed camera distance calculations in `ParticleManagerMixin` to compute distance directly from bounding box extents without allocating temporary `Vec3` objects per particle.
  - Added strict null and initialization checks for `Minecraft.getInstance()`, `client.level`, `client.player`, `camera`, and `box`.

---

## [1.0.2] - 2026-09-18

### RAM Optimization & Memory Leak Prevention

#### Added
- **Active Memory Optimizer (`MemoryOptimizer`)**:
  - Automatically monitors JVM heap every 5 seconds and triggers non-blocking asynchronous heap trimming and garbage collection whenever memory usage reaches the safety threshold (75%).
  - Prevents system OOM crashes and kernel kills on desktop processes.
- **World Exit Heap Reclaim**:
  - Hooked into `Minecraft.clearClientLevel` to immediately release retained chunk sections, models, and entity references upon disconnecting or returning to the main menu.
- **Manual RAM Cleaner**:
  - Added an interactive "Clean RAM Now" button directly in the ModMenu settings screen for on-demand memory purging.
- **F3 & HUD RAM Telemetry**:
  - F3 debug screen displays live RAM statistics: `RAM: <used>MB/<max>MB (<pct>%) | Auto-Clean: ON`.
  - Added configurable `Show RAM in HUD` setting to monitor real-time memory usage alongside FPS.

---

## [1.0.1] - 2026-09-18

### Critical Fixes & Stability Improvements

#### Fixed
- **World Load Crash Resolution**:
  - Fixed NullPointerException in `ParticleManagerMixin` by adding rigorous null-checks on `particle`, `getBoundingBox()`, camera, and level.
  - Eliminated server-side and mob tick cancellations (`MobEntityMixin`, `MinecraftServerMixin`) that caused entity desync, chunk corruption, and world loading freeze.
  - Removed texture animation tick skipping (`SpriteContentsAnimatorMixin`) to ensure seamless RenderPearl GPU buffer synchronization.
- **FPS HUD Overlay Visibility**:
  - Replaced Fabric's `HudElementRegistry` hook with direct injection into `Hud.extractRenderState` at `TAIL`.
  - Guaranteed 100% overlay visibility across all resolutions, GUI scales, and with Sodium installed.
  - Added support for F1 toggle hiding (`client.gui.hud.isHidden()`).
- **Entity Culling Calculation**:
  - Fixed camera distance check in `EntityRenderDispatcherMixin` to use `entity.distanceToSqr(camX, camY, camZ)` instead of distance from world origin `(0, 0, 0)`.
  - Protected local player, camera entity, passengers/mounts, glowing entities, and boss mobs (Ender Dragon, Wither) from culling.
- **Block Entity Culling Guard**:
  - Added complete null safety to `BlockEntityRenderDispatcherMixin`.

#### Added
- **F3 Debug Screen Integration**:
  - Real-time diagnostic info added to F3 debug screen displaying active entity, block entity, and particle culling states.

---

## [1.0.0] - 2026-09-18

### Initial Release of Max FPS booster for Minecraft 26.3 ("Wilderness Bound")

#### Added
- **Dynamic Color FPS Overlay**:
  - In-game framerate counter with configurable placement (`Top Left`, `Top Right`, `Bottom Left`, `Bottom Right`).
  - Automatic dynamic color gradient: Green (>= 60 FPS), Yellow (30-59 FPS), and Red (< 30 FPS).
- **Rendering Optimizations**:
  - High-performance frustum and distance culling for entities, block entities, and particles.
- **ModMenu & Configuration System**:
  - Integrated `ModMenuApi` configuration screen factory.
  - Custom native `FpsBoosterConfigScreen` with instant live setting application.
  - Full English (`en_us.json`) localization for all configuration entries.
