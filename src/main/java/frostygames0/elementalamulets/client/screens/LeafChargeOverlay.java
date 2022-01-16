/*
 *  Copyright (c) 2021
 *
 *     This file is part of Elemental Amulets, a Minecraft Mod.
 *
 *     Elemental Amulets is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU Lesser General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     Elemental Amulets is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU Lesser General Public License for more details.
 *
 *     You should have received a copy of the GNU Lesser General Public License
 *     along with Elemental Amulets.  If not, see <https://www.gnu.org/licenses/>.
 */

package frostygames0.elementalamulets.client.screens;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import frostygames0.elementalamulets.config.ModConfig;
import frostygames0.elementalamulets.init.ModItems;
import frostygames0.elementalamulets.items.amulets.TerraProtectionAmuletItem;
import frostygames0.elementalamulets.util.AmuletHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.client.gui.IIngameOverlay;


import static frostygames0.elementalamulets.ElementalAmulets.modPrefix;

/**
 * @author Frostygames0
 * @date 14.10.2021 20:00
 */
public class LeafChargeOverlay implements IIngameOverlay {

    @Override
    public void render(ForgeIngameGui gui, PoseStack ms, float partialTicks, int width, int height) {
        Minecraft mc = Minecraft.getInstance();

        int posX = (width / 2);
        int posY = height - 12;

        if (gui.shouldDrawSurvivalElements() && ModConfig.CachedValues.RENDER_LEAF_CHARGE_OVERLAY) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
            RenderSystem.setShaderTexture(0, modPrefix("textures/gui/leaf_charge_overlay.png"));

            LocalPlayer player = mc.player;
            if (player != null) {
                AmuletHelper.getAmuletInSlotOrBelt(ModItems.TERRA_PROTECTION_AMULET.get(), player).ifPresent(triple -> {
                    ItemStack stack = triple.stack();
                    TerraProtectionAmuletItem amulet = (TerraProtectionAmuletItem) stack.getItem();

                    int offset = posX + 98;

                    RenderSystem.enableBlend();

                    drawLeafBar(ms, Math.min(amulet.getMaxCharge(stack), 16), offset, posY, 0);
                    drawLeafBar(ms, Math.min(amulet.getCharges(stack), 16), offset, posY, 8);

                    RenderSystem.disableBlend();

                });
            }
        }
    }

    private static void drawLeafBar(PoseStack ms, int size, int posX, int posY, int VOffset) {
        for (int j = 1; j <= size; j++) {
            GuiComponent.blit(ms, posX, posY, 0, VOffset, 7, 7, 16, 16);
            posX += 8;
        }
    }
}
