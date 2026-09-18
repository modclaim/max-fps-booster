package com.fpsbooster.client;

import com.fpsbooster.hud.FpsHudOverlay;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.hud.HudElementRegistry;
import net.minecraft.resources.Identifier;

public class FpsBoosterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HudElementRegistry.addLast(Identifier.fromNamespaceAndPath("max-fps-booster", "hud"), new FpsHudOverlay());
    }
}
