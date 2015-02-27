package com.iconmaster.aec2.network;

import com.iconmaster.aec2.te.TEForge;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author iconmaster
 */
public class RequestForgeSelectionPacket extends AetherCraftTEPacket<TEForge> {
	
	public RequestForgeSelectionPacket() {
		
	}

	public RequestForgeSelectionPacket(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		
		RequestForgeSelectionPacket p = (RequestForgeSelectionPacket) message;
		
		return new ForgeSelectionPacket(p.x, p.y, p.z, te.selection);
	}
}
