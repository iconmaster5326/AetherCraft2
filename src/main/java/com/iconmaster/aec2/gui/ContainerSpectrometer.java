package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TESpectrometer;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerSpectrometer extends AetherCraftContainer<TESpectrometer> {

	public ContainerSpectrometer(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TESpectrometer) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(8, 32, 1, 1));
	}
}
