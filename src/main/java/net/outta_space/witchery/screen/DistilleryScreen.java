package net.outta_space.witchery.screen;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;
import net.outta_space.witchery.WitcheryMod;

public class DistilleryScreen extends AbstractContainerScreen<DistilleryMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation(WitcheryMod.MOD_ID,"textures/gui/distillery_gui.png");

    public DistilleryScreen(DistilleryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();
        this.titleLabelX = 67;
    }

    @Override
    protected void renderBg(GuiGraphics pGuiGraphics, float pPartialTick, int pMouseX, int pMouseY) {

        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0f, 1.0f, 1.0f, 1.0f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        pGuiGraphics.blit(TEXTURE, x, y, 0, 0, imageWidth, imageHeight);

        renderProgressArrow(pGuiGraphics, x, y);
        renderProgressBubbles(pGuiGraphics, x, y);
        renderNoSymbol(pGuiGraphics, x, y);

    }

    private void renderNoSymbol(GuiGraphics pGuiGraphics, int x, int y) {
        if(menu.hasNoAltar()) {
            pGuiGraphics.blit(TEXTURE, x + 35, y + 57, 197, 0, 9, 9);
        }
    }

    private void renderProgressBubbles(GuiGraphics pGuiGraphics, int x, int y) {
        if(menu.isDistilling()) {
            pGuiGraphics.blit(TEXTURE, x + 32, y + 21 + (29 - menu.getBubbleProgress()), 184, (29 - menu.getBubbleProgress()), 13, menu.getBubbleProgress());
        }
    }

    private void renderProgressArrow(GuiGraphics guiGraphics, int x, int y) {
        if(menu.isDistilling()) {
            guiGraphics.blit(TEXTURE, x + 68, y + 15, 176, 29, menu.getScaledProgress(), 35);
        }
    }

    @Override
    public void render(GuiGraphics pGuiGraphics, int pMouseX, int pMouseY, float pPartialTick) {
        renderBackground(pGuiGraphics);
        super.render(pGuiGraphics, pMouseX, pMouseY, pPartialTick);
        renderTooltip(pGuiGraphics, pMouseX, pMouseY);
    }
}
