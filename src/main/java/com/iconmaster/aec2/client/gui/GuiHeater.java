package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEHeater;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author iconmaster
 */
public class GuiHeater extends AetherCraftGui<TEHeater> {

	public GuiHeater(InventoryPlayer player, AetherCraftContainer<TEHeater> container, AetherCraftTE te) {
		super(player, container, (TEHeater) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/heaterGui.png");
	}
	
}
