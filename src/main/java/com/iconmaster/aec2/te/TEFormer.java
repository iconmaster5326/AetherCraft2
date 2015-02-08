package com.iconmaster.aec2.te;

import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;

/**
 *
 * @author iconmaster
 */
public class TEFormer extends AetherCraftTE {

	public TEFormer() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 8;
		name = "aec2.former";
	}
	
	@Override
	public void update() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			
		}
	}
}
