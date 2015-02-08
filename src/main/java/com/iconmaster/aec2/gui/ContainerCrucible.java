package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TECrucible;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerCrucible extends AetherCraftContainer<TECrucible> {

	public ContainerCrucible(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TECrucible) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(18, 33, 1, 1));
		grids.add(new SlotGrid(140, 33, 1, 1));
	}
}
