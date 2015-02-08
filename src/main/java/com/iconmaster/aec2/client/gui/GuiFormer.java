package com.iconmaster.aec2.client.gui;

import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEFormer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

/**
 *
 * @author iconmaster
 */
public class GuiFormer extends AetherCraftGui<TEFormer> {

	public GuiFormer(InventoryPlayer player, AetherCraftContainer<TEFormer> container, AetherCraftTE te) {
		super(player, container, (TEFormer) te);
		
		gui_texture = new ResourceLocation("aec2", "textures/gui/formerGui.png");
	}
	
}
