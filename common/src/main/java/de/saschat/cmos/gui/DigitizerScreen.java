package de.saschat.cmos.gui;

import de.saschat.cmos.ExposureComputerMod;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DigitizerScreen extends AbstractContainerScreen<DigitizerMenu> {
    private static final ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(ExposureComputerMod.MOD_ID, "textures/gui/digitizer.png");
    public DigitizerScreen(DigitizerMenu abstractContainerMenu, Inventory inventory, Component component) {
        super(abstractContainerMenu, inventory, component);
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float f, int mouseX, int mouseY) {
        guiGraphics.blit(BACKGROUND, leftPos, topPos, 0, 0, imageWidth, imageHeight);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int i, int j, float f) {
        renderBackground(guiGraphics, i, j, f);
        super.render(guiGraphics, i, j, f);
        renderTooltip(guiGraphics, i, j);
    }
}
