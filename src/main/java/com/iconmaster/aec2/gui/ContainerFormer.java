package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEFormer;
import net.minecraft.entity.player.InventoryPlayer;

/**
 *
 * @author iconmaster
 */
public class ContainerFormer extends AetherCraftContainer<TEFormer> {

	public ContainerFormer(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TEFormer) tileEntity);
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(80, 9, 4, 1));
		grids.add(new SlotGrid(80, 60, 4, 1));
	}
}
