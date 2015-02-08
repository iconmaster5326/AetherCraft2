package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.network.AetherCraftPacketHandler;
import com.iconmaster.aec2.network.RequestHeatSyncPacket;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TECrucible;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

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
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		super.drawGuiContainerForegroundLayer(mouseX, mouseY);
		
		fontRendererObj.drawString("Heat: "+te.realHeat, 7, 54, 0x404040);
	}
}
