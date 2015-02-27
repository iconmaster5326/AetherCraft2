package com.iconmaster.aec2.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class ForgeOutputSlot extends Slot {
	public ForgeOutputSlot(IInventory p_i1824_1_, int p_i1824_2_, int p_i1824_3_, int p_i1824_4_) {
		super(p_i1824_1_, p_i1824_2_, p_i1824_3_, p_i1824_4_);
	}

	@Override
	public ItemStack getStack() {
		
		return super.getStack();
	}

	@Override
	public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_) {
		
		
		super.onPickupFromSlot(p_82870_1_, p_82870_2_);
	}

	@Override
	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}
}
