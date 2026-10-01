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
- **Entity Distance Culling**: Intelligently skips rendering of distant mobs and entities beyond the configured distance multiplier. Automatically protects named mobs, glowing entities, and mounted players.
- **Block Entity Culling**: Skips expensive render state evaluation for distant block entities (chests, shulker boxes, beacons, banners) located beyond configured view distances.
- **Particle Culling**: Prevents out-of-range particles from running unnecessary bounding box updates or render matrix operations.

### ⚙️ ModMenu In-Game Settings
- Built-in native configuration screen accessible via **ModMenu** (optional dependency).
- **Instant Hot-Reload**: Every toggle takes effect immediately upon adjustment—no client restarts required.
- Persisted cleanly to `.minecraft/config/max-fps-booster.json`.

---

## 🧩 Compatibility Matrix

| Mod | Compatibility Status | Notes |
| :--- | :---: | :--- |
| **Sodium** | 🟢 **Full Compatibility** | Operates synergistically; Max FPS booster optimizes entities, particles, and tile entities while Sodium manages chunk rendering. |
| **Iris Shaders** | 🟢 **Full Compatibility** | Fully compatible across all shader packs. |
| **ModMenu** | 🟢 **Full Compatibility** | Native config screen integration (Optional). |

---

## 📥 Installation

1. Install **Fabric Loader** (version `0.19.5` or higher) for Minecraft `26.3`.
2. Download and place **Fabric API** (`0.160.7+26.3` or higher) into your `.minecraft/mods` folder.
3. Download and place **max-fps-booster-1.0.3.jar** into your `.minecraft/mods` folder.
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
