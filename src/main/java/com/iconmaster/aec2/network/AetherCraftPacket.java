package com.iconmaster.aec2.network;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 *
 * @author iconmaster
 */
public class AetherCraftPacket implements IMessage, IMessageHandler<AetherCraftPacket, IMessage>  {

	@Override
	public void fromBytes(ByteBuf buf) {
	}

	@Override
	public void toBytes(ByteBuf buf) {
	}

	@Override
	public IMessage onMessage(AetherCraftPacket message, MessageContext ctx) {
		return null;
	}
	
}
