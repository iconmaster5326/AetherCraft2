package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEForge;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerForge extends AetherCraftContainer<TEForge> {

	public ContainerForge(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TEForge) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(95, 10, 4, 1));
		grids.add(new SlotGrid(123, 58, 1, 1));
		
		pinv_y = 148;
	}
}
