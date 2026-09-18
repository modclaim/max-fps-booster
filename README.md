# Max FPS booster (Fabric 26.3)

[![Minecraft 26.3](https://img.shields.io/badge/Minecraft-26.3-brightgreen.svg)](https://minecraft.net/)
[![Fabric Loader](https://img.shields.io/badge/Fabric-0.19.5+-blue.svg)](https://fabricmc.net/)
[![Modrinth](https://img.shields.io/badge/Modrinth-max--fps--booster-green.svg)](https://modrinth.com/project/max-fps-booster)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](https://opensource.org/licenses/MIT)

**Max FPS booster** is an ultra-high-performance optimization mod and customizable FPS HUD for **Minecraft: Java Edition 26.3** ("Wilderness Bound"), built natively on the **Fabric** ecosystem.

Designed from the ground up by **modclaim** to operate in complete synergy with **Sodium**, Max FPS booster eliminates performance bottlenecks across entity rendering, block entity extraction, particle budgets, animated texture uploads, and singleplayer/LAN host server tick lag.

---

## ⚡ Key Features

### 📊 Dynamic Color FPS Overlay HUD
- **Real-Time Display**: Live framerate counter rendered smoothly on your screen.
- **Dynamic Color Shifting**:
  - 🟢 **Green** (`#55FF55`): >= 60 FPS (Smooth performance)
  - 🟡 **Yellow** (`#FFFF55`): 30 - 59 FPS (Moderate framerate)
  - 🔴 **Red** (`#FF5555`): < 30 FPS (Low framerate warning)
- **Customizable Screen Corner**: Top-Left (Default), Top-Right, Bottom-Left, or Bottom-Right.
- **Performance Diagnostics**: Optional Min/Max FPS tracking and live frame time in milliseconds (e.g. `6.8 ms`).
- **Clean F3 Integration**: Automatically hides when opening the full vanilla F3 debug overlay.

### 🚀 Advanced Rendering Optimizations
- **Entity Distance Culling & Scaling**: Culls distant entities outside player view or beyond configured distance multipliers before expensive model evaluation and matrix calculations.
- **Block Entity Culling**: Skips rendering of heavy block entities (chests, shulker boxes, beacons, banners) located beyond configured view distances.
- **Particle Throttling & Culling**: Prevents out-of-range particles (torches, campfires, portals, weather splashes) from polluting memory or running tick updates.
- **Animated Texture Throttling**: Paces animated texture updates, significantly lowering GPU upload strain while keeping animations looking natural.

### 🌐 LAN Host & Singleplayer Server Optimizations
- **Smart Mob Ticking**: When hosting a LAN world or playing in singleplayer, distant inactive mobs without targets are throttled to skip redundant AI goal selector ticks, drastically freeing up CPU headroom and lowering MSPT.
- **Auto-Save Lag Spike Reducer**: Prevents micro-stutters and sudden freeze frames during periodic world saves by smoothing chunk flush routines.

### ⚙️ ModMenu In-Game Settings
- Built-in native configuration screen accessible via **ModMenu** (optional dependency).
- **Instant Hot-Reload**: Every toggle and slider takes effect immediately upon adjustment—no client restarts required.
- Persisted cleanly to `.minecraft/config/max-fps-booster.json`.

---

## 🧩 Compatibility Matrix

| Mod | Compatibility Status | Notes |
| :--- | :---: | :--- |
| **Sodium** | 🟢 **Full Compatibility** | Operates synergistically; Max FPS booster optimizes entities, particles, and tile entities while Sodium manages chunk rendering. |
| **Iris Shaders** | 🟢 **Full Compatibility** | Fully compatible across all shader packs. |
| **ModMenu** | 🟢 **Full Compatibility** | Native config screen integration (Optional). |
| **Lithium / ASC** | 🟢 **Full Compatibility** | Non-conflicting server tick improvements. |

---

## 📥 Installation

1. Install **Fabric Loader** (version `0.19.5` or higher) for Minecraft `26.3`.
2. Download and place **Fabric API** (`0.160.7+26.3` or higher) into your `.minecraft/mods` folder.
3. Download and place **max-fps-booster-1.0.0.jar** into your `.minecraft/mods` folder.
4. *(Optional)* Install **ModMenu** to access the in-game settings screen.
5. Launch Minecraft and enjoy maximum framerates!

---

## 🛠️ Building From Source

### Prerequisites
- JDK 25 or higher (Java 25 is required for Minecraft 26.3)
- Internet connection for initial dependency resolution

### Compilation
```bash
./gradlew build
```
The compiled mod JAR will be generated in:
```
build/libs/max-fps-booster-1.0.0.jar
```

---

## 🔗 Project Links
- **Modrinth**: [https://modrinth.com/project/max-fps-booster](https://modrinth.com/project/max-fps-booster)
- **Author/Owner**: [modclaim](https://modrinth.com/user/modclaim)

---

## 📄 License
This project is licensed under the [MIT License](LICENSE).
