package com.iconmaster.aec2.network;

import com.iconmaster.aec2.te.TECrucible;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author iconmaster
 */
public class RequestHeatSyncPacket extends AetherCraftTEPacket<TECrucible> {
	public RequestHeatSyncPacket() {
		
	}

	public RequestHeatSyncPacket(int x, int y, int z) {
		super(x, y, z);
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		
		if (te==null) {
			return null;
		}
		
		return new HeatSyncPacket(message.x, message.y, message.z, te.reactor.heat);
	}
}
