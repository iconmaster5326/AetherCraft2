package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author iconmaster
 */
public abstract class AetherCraftGui<T extends AetherCraftTE> extends GuiContainer {
	public T te;
	public ResourceLocation gui_texture;
	
	public AetherCraftGui(InventoryPlayer player, AetherCraftContainer<T> container, T te) {
		super(container);
		
		this.te = te;
		this.xSize = 176;
		this.ySize = 166;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.renderEngine.bindTexture(this.gui_texture);
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		this.drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		GL11.glDisable(GL11.GL_LIGHTING);
		this.fontRendererObj.drawString(LanguageRegistry.instance().getStringLocalization("tile."+te.getInventoryName()+".name"), 7, 5, 0x404040);
		this.fontRendererObj.drawString(I18n.format("container.inventory", new Object[0]), 8, this.ySize - 96 + 2, 0x404040);
	}
	
}
