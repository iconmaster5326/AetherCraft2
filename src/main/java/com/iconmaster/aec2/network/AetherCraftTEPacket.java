package com.iconmaster.aec2.network;

import com.iconmaster.aec2.te.AetherCraftTE;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;

/**
 *
 * @author iconmaster
 */
public abstract class AetherCraftTEPacket<T extends AetherCraftTE> implements IMessage, IMessageHandler<AetherCraftTEPacket, IMessage>  {
	public int x,y,z;
	
	public T te;
	
	public AetherCraftTEPacket() {}

	public AetherCraftTEPacket(int x, int y, int z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	@Override
	public void fromBytes(ByteBuf b) {
		x = b.readInt();
		y = b.readInt();
		z = b.readInt();
	}

	@Override
	public void toBytes(ByteBuf b) {
		b.writeInt(x);
		b.writeInt(y);
		b.writeInt(z);
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		te = (T) Minecraft.getMinecraft().theWorld.getTileEntity(message.x, message.y, message.z);
		if (te == null) {
			System.out.println("[AEC2] ERROR: TE not found!");
		}
		return null;
	}
	
}
