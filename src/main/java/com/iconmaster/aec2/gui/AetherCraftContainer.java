package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.te.AetherCraftTE;
import java.util.ArrayList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 * @param <T>
 */
public abstract class AetherCraftContainer<T extends AetherCraftTE> extends Container {
	public T te;
	
	public int pinv_x = 8;
	public int pinv_y = 84;
	
	public ArrayList<SlotGrid> grids = new ArrayList<SlotGrid>();

	public AetherCraftContainer(InventoryPlayer player, T tileEntity) {
		this.te = tileEntity;
		
		this.registerGrids();
		int i=0;
		for (SlotGrid g : grids) {
			i=g.addSlots(this,i);
		}
		
		this.bindPlayerInventory(player);
	}

	public Slot addSlot(Slot slot) {
		return this.addSlotToContainer(slot);
	}
	
	@Override
	public boolean canInteractWith(EntityPlayer player) {
		return this.te.isUseableByPlayer(player);
	}
	
	public void bindPlayerInventory(InventoryPlayer player) {
		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				addSlotToContainer(new Slot(player, x + y * 9 + 9, pinv_x + x * 18,
						pinv_y + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			addSlotToContainer(new Slot(player, x, pinv_x + x * 18, pinv_y + (142-84)));
		}
	}
	
	public int getPublicSlots() {
		return te.inventory.length;
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
			else if (!this.mergeItemStack(stackInSlot, 0, getPublicSlots(), false)) {
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

	public abstract void registerGrids();
}
