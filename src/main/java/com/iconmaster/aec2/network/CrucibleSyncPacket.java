package com.iconmaster.aec2.network;

import com.iconmaster.aec2.aether.Reactor;
import com.iconmaster.aec2.te.TECrucible;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 *
 * @author iconmaster
 */
public class CrucibleSyncPacket extends AetherCraftTEPacket<TECrucible> {

	public Reactor r;

	public CrucibleSyncPacket() {

	}

	public CrucibleSyncPacket(int x, int y, int z, Reactor r) {
		super(x, y, z);
		this.r = r;
	}

	@Override
	public void toBytes(ByteBuf b) {
		super.toBytes(b);
		
		r.serialize(b);
	}

	@Override
	public void fromBytes(final ByteBuf b) {
		super.fromBytes(b);
		
		r = Reactor.deserialize(b);
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		te.reactor = ((CrucibleSyncPacket) message).r;

		return null;
	}
}
