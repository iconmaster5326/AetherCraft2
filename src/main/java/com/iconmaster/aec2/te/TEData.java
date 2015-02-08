package com.iconmaster.aec2.te;

import com.iconmaster.aec2.client.gui.AetherCraftGui;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import cpw.mods.fml.common.registry.GameRegistry;

/**
 *
 * @author iconmaster
 */
public class TEData {
	public String name;
	public Class<? extends AetherCraftTE> te;
	public Class<? extends AetherCraftContainer> inv;
	public Class<? extends AetherCraftGui> gui;

	public TEData(String name, Class<? extends AetherCraftTE> te, Class<? extends AetherCraftContainer> inv, Class<? extends AetherCraftGui> gui) {
		this.name = name;
		this.te = te;
		this.inv = inv;
		this.gui = gui;
	}
	
	public void register() {
		GameRegistry.registerTileEntity(te, name);
	}
}
