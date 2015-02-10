package com.iconmaster.aec2.te;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 *
 * @author iconmaster
 */
public class TEHeater extends AetherCraftTE {

	public TEHeater() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 1;
		name = "aec2.heater";
	}
	
	@Override
	public void update() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			
		}
	}
}
