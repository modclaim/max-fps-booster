# Changelog

All notable changes to the **Max FPS booster** mod will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [1.0.0] - 2026-09-18

### Initial Release of Max FPS booster for Minecraft 26.3 ("Wilderness Bound")

#### Added
- **Dynamic Color FPS Overlay**:
  - In-game framerate counter with configurable placement (`Top Left`, `Top Right`, `Bottom Left`, `Bottom Right`).
  - Automatic dynamic color gradient: Green (>= 60 FPS), Yellow (30-59 FPS), and Red (< 30 FPS).
  - Optional frame time indicator (ms) and Min/Max framerate tracker.
  - Automatic suppression when the native F3 debug overlay is active.
- **Rendering Optimizations**:
  - `EntityRenderDispatcherMixin`: Entity distance scaling and frustum culling.
  - `BlockEntityRenderDispatcherMixin`: Block entity extraction culling for chests, beacons, shulkers, and banners.
  - `ParticleManagerMixin`: Distance-based particle spawn culling and weather particle throttling.
  - `SpriteContentsAnimatorMixin`: Animated texture update rate throttling.
- **Host & Singleplayer Optimizations**:
  - `MobEntityMixin`: Smart mob ticking to alleviate integrated server load when hosting LAN worlds or in singleplayer.
  - `MinecraftServerMixin`: Smooth world auto-save pacing to eradicate 5-minute lag spikes.
- **ModMenu & Configuration System**:
  - Integrated `ModMenuApi` configuration screen factory.
  - Custom native `FpsBoosterConfigScreen` with instant live setting application.
  - JSON serialization via `config/max-fps-booster.json`.
  - Full English (`en_us.json`) localization for all configuration entries.
