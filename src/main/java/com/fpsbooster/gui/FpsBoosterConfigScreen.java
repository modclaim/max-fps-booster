package com.fpsbooster.gui;

import com.fpsbooster.config.FpsBoosterConfig;
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
        int leftCol = this.width / 2 - 155;
        int rightCol = this.width / 2 + 5;
        int y = 40;
        int rowHeight = 26;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.hudEnabled)
                .create(leftCol, y, 150, 20, Component.translatable("maxfpsbooster.option.hud_enabled"), (button, value) -> {
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
                .create(rightCol, y, 150, 20, Component.translatable("maxfpsbooster.option.hud_position"), (button, value) -> {
                    config.hudPosition = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.dynamicColor)
                .create(leftCol, y, 150, 20, Component.translatable("maxfpsbooster.option.dynamic_color"), (button, value) -> {
                    config.dynamicColor = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.showFrameTime)
                .create(rightCol, y, 150, 20, Component.translatable("maxfpsbooster.option.show_frame_time"), (button, value) -> {
                    config.showFrameTime = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.showMinMaxFps)
                .create(leftCol, y, 150, 20, Component.translatable("maxfpsbooster.option.show_min_max"), (button, value) -> {
                    config.showMinMaxFps = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.particleCulling)
                .create(rightCol, y, 150, 20, Component.translatable("maxfpsbooster.option.particle_culling"), (button, value) -> {
                    config.particleCulling = value;
                    config.save();
                }));

        y += rowHeight;

        this.addRenderableWidget(CycleButton.onOffBuilder(config.entityCulling)
                .create(leftCol, y, 150, 20, Component.translatable("maxfpsbooster.option.entity_culling"), (button, value) -> {
                    config.entityCulling = value;
                    config.save();
                }));

        this.addRenderableWidget(CycleButton.onOffBuilder(config.blockEntityCulling)
                .create(rightCol, y, 150, 20, Component.translatable("maxfpsbooster.option.block_entity_culling"), (button, value) -> {
                    config.blockEntityCulling = value;
                    config.save();
                }));

        y += rowHeight + 14;

        this.addRenderableWidget(Button.builder(CommonComponents.GUI_DONE, button -> onClose())
                .bounds(this.width / 2 - 100, Math.min(y, this.height - 28), 200, 20)
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
        this.extractBackground(graphics, mouseX, mouseY, delta);
        super.extractRenderState(graphics, mouseX, mouseY, delta);
        graphics.centeredText(this.font, this.title, this.width / 2, 16, 0xFFFFFF);
    }
}
