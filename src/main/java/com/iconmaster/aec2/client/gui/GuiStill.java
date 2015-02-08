package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEStill;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author iconmaster
 */
public class GuiStill extends AetherCraftGui<TEStill> {

	public GuiStill(InventoryPlayer player, AetherCraftContainer<TEStill> container, AetherCraftTE te) {
		super(player, container, (TEStill) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/stillGui.png");
	}
	
}
