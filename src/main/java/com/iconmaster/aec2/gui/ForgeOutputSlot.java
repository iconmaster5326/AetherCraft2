package com.iconmaster.aec2.gui;

import com.iconmaster.aec2.aether.AetoForgeRegistry;
import com.iconmaster.aec2.aether.AetoForgeRegistry.AetoForgeInput;
import com.iconmaster.aec2.aether.AetoForgeRegistry.AetoForgeRecipe;
import com.iconmaster.aec2.te.TEForge;
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
		TEForge te = (TEForge)inventory;
		if (te.selection==-1) {
			return null;
		}
		
		AetoForgeRecipe r = AetoForgeRegistry.recipes.get(te.selection);
		
		for (int i=0;i<r.inputs.size();i++) {
			AetoForgeInput inp = r.inputs.get(i);
			if (!inp.isValid(te.getStackInSlot(i))) {
				return null;
			}
		}
		
		return r.getOutput();
	}

	@Override
	public void onPickupFromSlot(EntityPlayer p_82870_1_, ItemStack p_82870_2_) {
		TEForge te = (TEForge)inventory;
		if (te.selection==-1) {
			return;
		}
		
		AetoForgeRecipe r = AetoForgeRegistry.recipes.get(te.selection);
		
		for (int i=0;i<r.inputs.size();i++) {
			AetoForgeInput inp = r.inputs.get(i);
			te.inventory[i] = inp.onCraft(te.inventory[i]);
		}
		
		super.onPickupFromSlot(p_82870_1_, p_82870_2_);
	}

	@Override
	public boolean isItemValid(ItemStack p_75214_1_) {
		return false;
	}

	@Override
	public ItemStack decrStackSize(int n) {
		return getStack();
	}
}
