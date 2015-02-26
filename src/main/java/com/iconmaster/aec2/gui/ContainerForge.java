package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEForge;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class ContainerForge extends AetherCraftContainer<TEForge> {
	public int gridSize = 4;

	public ContainerForge(InventoryPlayer player, AetherCraftTE tileEntity) {
		super(player, (TEForge) tileEntity);
		
		te.container = this;
	}

	@Override
	public void registerGrids() {
		grids.add(new SlotGrid(95, 10, gridSize, 1));
		grids.add(new SlotGrid(9999, 9999, 4-gridSize, 1));
		grids.add(new SlotGrid(123, 58, 1, 1));
		
		pinv_y = 148;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slot) {
		ItemStack stack = null;
		Slot slotObject = (Slot) inventorySlots.get(slot);

		// null checks and checks if the item can be stacked (maxStackSize > 1)
		if (slotObject != null && slotObject.getHasStack()) {
			ItemStack stackInSlot = slotObject.getStack();
			stack = stackInSlot.copy();

			// merges the item into player inventory since its in the te
			if (slot < getPublicSlots()) {
				if (!this.mergeItemStack(stackInSlot, getPublicSlots(), getPublicSlots()+26, false)) {
					return null;
				}
			}
			// places it into the te is possible since its in the player
			// inventory
			else if (!this.mergeItemStack(stackInSlot, 0, gridSize, false)) {
				return null;
			}

			if (stackInSlot.stackSize == 0) {
				slotObject.putStack(null);
			} else {
				slotObject.onSlotChanged();
			}

			if (stackInSlot.stackSize == stack.stackSize) {
				return null;
			}

			slotObject.onPickupFromSlot(player, stackInSlot);
		}

		return stack;
	}
}
