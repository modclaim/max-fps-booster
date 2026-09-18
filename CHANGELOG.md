# Changelog

All notable changes to the **Max FPS booster** mod will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
