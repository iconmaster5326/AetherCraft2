package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.AetherCraft;
import com.iconmaster.aec2.client.gui.AetherCraftGui;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEData;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author iconmaster
 */
public class AetherCraftGuiHandler implements IGuiHandler {

	public AetherCraftContainer getContainer(AetherCraftTE te, TEData data, EntityPlayer player) {
		try {
			AetherCraftContainer gui = data.inv.getConstructor(InventoryPlayer.class, AetherCraftTE.class).newInstance(player.inventory, te);
			return gui;
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof AetherCraftTE) {
			AetherCraftTE acte = (AetherCraftTE) te;
			TEData data = AetherCraft.tes.get(acte.getInventoryName());
			return getContainer(acte, data, player);
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof AetherCraftTE) {
			AetherCraftTE acte = (AetherCraftTE) te;
			TEData data = AetherCraft.tes.get(acte.getInventoryName());
			try {
				AetherCraftContainer inv = getContainer(acte, data, player);
				AetherCraftGui gui = data.gui.getConstructor(InventoryPlayer.class, AetherCraftContainer.class, AetherCraftTE.class).newInstance(player.inventory, inv, acte);
				return gui;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		return null;
	}
}
