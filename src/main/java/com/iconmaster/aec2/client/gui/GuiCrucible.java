package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.network.AetherCraftPacketHandler;
import com.iconmaster.aec2.network.OpenHatchPacket;
import com.iconmaster.aec2.network.RequestHeatSyncPacket;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TECrucible;
import java.util.Random;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

/**
 *
 * @author iconmaster
 */
public class GuiCrucible extends AetherCraftGui<TECrucible> {

	public GuiCrucible(InventoryPlayer player, AetherCraftContainer<TECrucible> container, AetherCraftTE te) {
		super(player, container, (TECrucible) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/crucibleGui.png");
		
		AetherCraftPacketHandler.HANDLER.sendToServer(new RequestHeatSyncPacket(te.xCoord, te.yCoord, te.zCoord));
	}

	@Override
	public void initGui() {
		super.initGui();
		
		buttonList.add(new GuiButton(0, guiLeft+110, guiTop+57, 60, 20, "Open Hatch"));
	}
	
	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		fontRendererObj.drawString("Heat: "+te.realHeat, 7, 54, 0x404040);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float p_146976_1_, int p_146976_2_, int p_146976_3_) {
		super.drawGuiContainerBackgroundLayer(p_146976_1_, p_146976_2_, p_146976_3_);
		
		int x = (this.width - this.xSize) / 2;
		int y = (this.height - this.ySize) / 2;
		
		int i=0;
		for (Compound c : te.reactor.reactants) {
			Random r = new Random(i+c.hashCode());
			
			GL11.glColor3b((byte) (c.color&0x0000ff),(byte) ((c.color&0x00ff00)>>8),(byte) ((c.color&0xff0000)>>16));
			drawTexturedModalRect(x+68+r.nextInt(105-68-16), y+15+r.nextInt(70-15-16), 240, 57, 16, 16);
			i++;
		}
		
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		drawTexturedModalRect(x+68, y+15, 218, 0, 105-68, 70-15);
	}

	@Override
	protected void actionPerformed(GuiButton b) {
		AetherCraftPacketHandler.HANDLER.sendToServer(new OpenHatchPacket(te.xCoord, te.yCoord, te.zCoord));
	}
}
