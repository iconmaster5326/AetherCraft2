package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.aether.AetoForgeRegistry;
import com.iconmaster.aec2.aether.AetoForgeRegistry.AetoForgeRecipe;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEForge;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author iconmaster
 */
public class GuiForge extends AetherCraftGui<TEForge> {
	
	public int selection = -1;

	public GuiForge(InventoryPlayer player, AetherCraftContainer<TEForge> container, AetherCraftTE te) {
		super(player, container, (TEForge) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/forgeGui.png");
		
		ySize = 198;
	}
	
	public static final int COLS = 4;

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		if (selection!=-1) {
			AetoForgeRecipe r = AetoForgeRegistry.recipes.get(selection);
			
			for (int i=0;i<r.inputs.size(); i++) {
				drawTexturedModalRect(x + 94 + 18*i, y + 9, 238, 0, 18, 18);
			}
		}
		
		List<AetoForgeRecipe> rs = AetoForgeRegistry.recipes;
		for (int i=0;i<rs.size(); i++) {
			int row = i%COLS;
			int col = i/COLS;
			
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture(gui_texture);
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
			
			if (i==selection) {
				drawTexturedModalRect(x+8+18*row-1, y+21+18*col-1, 238, 18, 18, 18);
			}
			
			ItemStack toDisplay = rs.get(i).getDisplayStack();
			drawItemStack(toDisplay, x+8+18*row, y+21+18*col);
		}
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		List<AetoForgeRecipe> rs = AetoForgeRegistry.recipes;
		for (int i=0;i<rs.size(); i++) {
			int row = i%COLS;
			int col = i/COLS;
			
			if (mouseX>=x+8+18*row && mouseX<=x+8+18*row+18 && mouseY>=y+21+18*col && mouseY<=y+21+18*col+18) {
				List<String> list = new ArrayList<String>();
				list.add(rs.get(i).name);
				
				for (String s : rs.get(i).desc) {
					list.add("\u00a77\u00a7o"+s);
				}
				
				this.drawHoveringText(list, mouseX-x, mouseY-y, fontRendererObj);
			}
		}
	}

	@Override
	protected void mouseClicked(int mouseX, int mouseY, int event) {
		super.mouseClicked(mouseX, mouseY, event);
		
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		List<AetoForgeRecipe> rs = AetoForgeRegistry.recipes;
		for (int i=0;i<rs.size(); i++) {
			int row = i%COLS;
			int col = i/COLS;
			
			if (mouseX>=x+8+18*row && mouseX<=x+8+18*row+18 && mouseY>=y+21+18*col && mouseY<=y+21+18*col+18) {
				selection = i;
				
				te.container.gridSize = rs.get(i).inputs.size();
				te.container.regenerateSlots();
			}
		}
	}
}
