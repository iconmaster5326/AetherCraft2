package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEForge;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author iconmaster
 */
public class GuiForge extends AetherCraftGui<TEForge> {

	public GuiForge(InventoryPlayer player, AetherCraftContainer<TEForge> container, AetherCraftTE te) {
		super(player, container, (TEForge) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/forgeGui.png");
		
		ySize = 230;
	}
	
}
