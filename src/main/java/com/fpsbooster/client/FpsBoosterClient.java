package com.fpsbooster.client;

import com.fpsbooster.config.FpsBoosterConfig;
import com.fpsbooster.memory.MemoryOptimizer;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class FpsBoosterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FpsBoosterConfig.getInstance();
        ClientTickEvents.END_CLIENT_TICK.register(MemoryOptimizer::tick);
    }
}
