package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TECooler;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author iconmaster
 */
public class GuiCooler extends AetherCraftGui<TECooler> {

	public GuiCooler(InventoryPlayer player, AetherCraftContainer<TECooler> container, AetherCraftTE te) {
		super(player, container, (TECooler) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/heaterGui.png");
	}
	
}
