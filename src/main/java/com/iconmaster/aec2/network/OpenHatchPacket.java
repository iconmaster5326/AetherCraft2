package com.iconmaster.aec2.network;

import com.iconmaster.aec2.te.TECrucible;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author iconmaster
 */
public class OpenHatchPacket extends AetherCraftTEPacket<TECrucible> {
	public OpenHatchPacket() {
		
	}

	public OpenHatchPacket(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		te.openHatch();
		return null;
	}
}
