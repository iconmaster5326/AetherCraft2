package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TECooler;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerCooler extends AetherCraftContainer<TECooler> {

	public ContainerCooler(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TECooler) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(80, 33, 1, 1));
	}
}
