package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEStill;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerStill extends AetherCraftContainer<TEStill> {

	public ContainerStill(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TEStill) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(80, 9, 4, 1));
		grids.add(new SlotGrid(80, 60, 4, 1));
	}
}
