package com.iconmaster.aec2.network;

import com.iconmaster.aec2.aether.AetoForgeRegistry;
import com.iconmaster.aec2.te.TEForge;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;

/**
 *
 * @author iconmaster
 */
public class ForgeSelectionPacket extends AetherCraftTEPacket<TEForge> {
	public int sel = -1;
	
	public ForgeSelectionPacket() {
		
	}

	public ForgeSelectionPacket(int x, int y, int z, int sel) {
		super(x, y, z);
		this.sel = sel;
	}
	
	@Override
	public void toBytes(ByteBuf b) {
		super.toBytes(b);
		b.writeInt(sel);
	}

	@Override
	public void fromBytes(ByteBuf b) {
		super.fromBytes(b);
		sel = b.readInt();
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		
		if (te!=null) {
			te.selection = ((ForgeSelectionPacket)message).sel;
			if (te.selection==-1) {
				te.gridSize = 0;
			} else {
				te.gridSize = AetoForgeRegistry.recipes.get(te.selection).inputs.size();
			}
			if (te.container!=null) {
				te.container.regenerateSlots();
			}
		}
		
		return null;
	}
}
