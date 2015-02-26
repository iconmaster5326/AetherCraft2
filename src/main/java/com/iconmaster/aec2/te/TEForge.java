package com.iconmaster.aec2.te;

import com.iconmaster.aec2.gui.ContainerForge;

/**
 *
 * @author iconmaster
 */
public class TEForge extends AetherCraftTE {
	public ContainerForge container;

	public TEForge() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 5;
		name = "aec2.forge";
	}
	
	@Override
	public void update() {}
}
