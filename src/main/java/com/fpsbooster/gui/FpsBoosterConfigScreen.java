package com.fpsbooster.gui;

import com.fpsbooster.config.FpsBoosterConfig;
import com.fpsbooster.memory.MemoryOptimizer;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class FpsBoosterConfigScreen extends Screen {
    private final Screen parent;

    public FpsBoosterConfigScreen(Screen parent) {
        super(Component.translatable("maxfpsbooster.title"));
        this.parent = parent;
    }

    @Override
    protected void init() {
        FpsBoosterConfig config = FpsBoosterConfig.getInstance();
        int buttonWidth = Math.max(80, Math.min(150, (this.width - 24) / 2));
        int leftCol = this.width / 2 - buttonWidth - 5;
        int rightCol = this.width / 2 + 5;
        int y = 34;
        int rowHeight = Math.max(20, Math.min(24, (this.height - 82) / 5));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.hudEnabled)
                .create(leftCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.hud_enabled"), (button, value) -> {
                    config.hudEnabled = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.<FpsBoosterConfig.HudPosition>builder(pos -> switch (pos) {
                    case TOP_LEFT -> Component.translatable("maxfpsbooster.pos.top_left");
                    case TOP_RIGHT -> Component.translatable("maxfpsbooster.pos.top_right");
                    case BOTTOM_LEFT -> Component.translatable("maxfpsbooster.pos.bottom_left");
                    case BOTTOM_RIGHT -> Component.translatable("maxfpsbooster.pos.bottom_right");
                }, config.hudPosition)
                .withValues(FpsBoosterConfig.HudPosition.values())
                .create(rightCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.hud_position"), (button, value) -> {
                    config.hudPosition = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.dynamicColor)
                .create(leftCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.dynamic_color"), (button, value) -> {
                    config.dynamicColor = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.showFrameTime)
                .create(rightCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.show_frame_time"), (button, value) -> {
                    config.showFrameTime = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.showMinMaxFps)
                .create(leftCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.show_min_max"), (button, value) -> {
                    config.showMinMaxFps = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.showRamUsage)
                .create(rightCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.show_ram_usage"), (button, value) -> {
                    config.showRamUsage = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.entityCulling)
                .create(leftCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.entity_culling"), (button, value) -> {
                    config.entityCulling = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.blockEntityCulling)
                .create(rightCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.block_entity_culling"), (button, value) -> {
                    config.blockEntityCulling = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.particleCulling)
                .create(leftCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.particle_culling"), (button, value) -> {
                    config.particleCulling = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.cleanOnWorldExit)
                .create(rightCol, y, buttonWidth, 20, Component.translatable("maxfpsbooster.option.clean_on_world_exit"), (button, value) -> {
                    config.cleanOnWorldExit = value;
                    config.save();
                }));

        y += rowHeight + 10;

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .bounds(this.width / 2 - 100, Math.min(y, this.height - 26), 200, 20)
                .build());
    }

    @Override
    public void onClose() {
        if (this.minecraft != null) {
            this.minecraft.setScreenAndShow(this.parent);
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        if (this.font != null && this.title != null) {
            graphics.centeredText(this.font, this.title, this.width / 2, 14, 0xFFFFFFFF);
        }
    }
}
