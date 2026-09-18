package com.fpsbooster.client;

import com.fpsbooster.config.FpsBoosterConfig;
import net.fabricmc.api.ClientModInitializer;

public class FpsBoosterClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        FpsBoosterConfig.getInstance();
    }
}
