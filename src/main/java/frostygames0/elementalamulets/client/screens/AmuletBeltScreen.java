package frostygames0.elementalamulets.client.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import frostygames0.elementalamulets.blocks.containers.AmuletBeltContainer;
import net.minecraft.client.gui.screen.inventory.ContainerScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;


import static frostygames0.elementalamulets.ElementalAmulets.modPrefix;

/**
 * @author Frostygames0
 * @date 10.09.2021 23:55
 */
public class AmuletBeltScreen extends ContainerScreen<AmuletBeltContainer> {
    public static final ResourceLocation GUI = modPrefix("textures/gui/amulet_belt_gui.png");
    public AmuletBeltScreen(AmuletBeltContainer screenContainer, PlayerInventory inv, ITextComponent titleIn) {
        super(screenContainer, inv, titleIn);
        this.titleLabelY = this.topPos+46;
    }

    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(matrixStack);
        super.render(matrixStack, mouseX, mouseY, partialTicks);
        this.renderTooltip(matrixStack, mouseX, mouseY);
    }

    @Override
    protected void renderBg(MatrixStack matrixStack, float partialTicks, int x, int y) {
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.getTextureManager().bind(GUI);
        this.blit(matrixStack, this.leftPos, this.topPos, 0, 0, this.imageWidth, this.imageHeight);
    }
}
