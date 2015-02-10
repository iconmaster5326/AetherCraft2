package com.iconmaster.aec2.network;

import com.iconmaster.aec2.te.TECrucible;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 *
 * @author iconmaster
 */
public class HeatSyncPacket extends AetherCraftTEPacket<TECrucible> {
	public int heat;
	
	public HeatSyncPacket() {
		
	}

	public HeatSyncPacket(int x, int y, int z, int heat) {
		super(x, y, z);
		this.heat = heat;
	}

	@Override
	public void toBytes(ByteBuf b) {
		super.toBytes(b);
		b.writeInt(heat);
	}

	@Override
	public void fromBytes(ByteBuf b) {
		super.fromBytes(b);
		heat = b.readInt();
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		
		if (te!=null) {
			te.realHeat = ((HeatSyncPacket)message).heat;
		}
		
		return null;
	}
}
