package com.iconmaster.aec2.network;

import com.iconmaster.aec2.aether.AetoForgeRegistry;
import com.iconmaster.aec2.te.TEForge;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

/**
 *
 * @author iconmaster
 */
public class ForgeSelectionPacket extends AetherCraftTEPacket<TEForge> {
	public int sel;
	
	public ForgeSelectionPacket() {
		
	}

	public ForgeSelectionPacket(int x, int y, int z, int sel) {
		super(x, y, z);
		this.sel = sel;
	}

	@Override
	public IMessage onMessage(AetherCraftTEPacket message, MessageContext ctx) {
		super.onMessage(message, ctx);
		
		if (te!=null) {
			te.selection = ((ForgeSelectionPacket)message).sel;
			te.gridSize = AetoForgeRegistry.recipes.get(te.selection).inputs.size();
			if (te.container!=null) {
				te.container.regenerateSlots();
			}
		}
		
		return null;
	}
}
