# Max FPS booster

A client-side Fabric mod for Minecraft **26.3** and Java **25**. It adds a configurable FPS HUD and reduces distant entity, block-entity and particle work. Performance gains depend on the world and hardware; no FPS increase is guaranteed.

## Features

- FPS counter with color thresholds, four corners, configurable scale, session minimum/maximum, frame interval and optional JVM heap usage.
- Optional distance culling for entities, block entities and newly spawned particles. Spectators, named/glowing mobs, bosses and player mounts are protected from entity culling.
- Settings through Mod Menu. Configuration is stored in `config/max-fps-booster.json`.
- Malformed configuration is backed up before defaults are written. Values are sanitized and saves use an atomic replacement when supported.
- Clearing the debug chunk cache on world exit stays on the client thread. The mod never forces garbage collection.

Distance culling changes what is visible at range. Disable individual culling options if you prefer full distant visuals. It does not change server ticking, chunk saving, texture animation or game simulation. Compatibility with every Sodium/Iris version and shader pack has not been verified.

## Install

Install Fabric Loader **0.19.5+**, Fabric API **0.161.0+26.3**, and `max-fps-booster-1.1.1.jar` in your Minecraft 26.3 instance. Mod Menu **21.0.0-beta.1** is optional. This artifact is for 26.3; it does not advertise support for snapshots or older Minecraft versions.

## Build and verification

Use JDK 25. Gradle selects it through a Java toolchain; there is no machine-specific Java path in the project.

```bash
./gradlew clean build
```

The JAR and sources are written to `build/libs`. The JUnit suite covers corrupt configuration recovery, numeric validation, atomic saves and FPS session statistics. Runtime client startup is checked separately before release; local automated checks do not establish a performance benchmark or all-server compatibility.

## Links and license

- [Modrinth](https://modrinth.com/mod/max-fps-booster)
- [Source and issues](https://github.com/modclaim/max-fps-booster)
- MIT; see [LICENSE](LICENSE).

AI assisted this maintenance update, its regression tests and documentation. The mod does not use an AI service at runtime.
