package com.fpsbooster;

import com.fpsbooster.config.FpsBoosterConfig;
import net.fabricmc.api.ModInitializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FpsBoosterMod implements ModInitializer {
    public static final String MOD_ID = "max-fps-booster";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitialize() {
        FpsBoosterConfig.getInstance();
    }
}
