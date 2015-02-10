package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEHeater;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerHeater extends AetherCraftContainer<TEHeater> {

	public ContainerHeater(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TEHeater) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(80, 33, 1, 1));
	}
}
