package com.iconmaster.eqlib.recipe;

import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class ItemDataStack {

	public ItemData item;
	public float amt;

	public ItemDataStack(ItemData item, float amt) {
		this.item = item;
		this.amt = amt;
	}
	
	public ItemDataStack(ItemStack stack) {
		this.item = new ItemData(stack);
		this.amt = stack.stackSize;
	}

	@Override
	public int hashCode() {
		int hash = 3;
		hash = 79 * hash + (this.item != null ? this.item.hashCode() : 0);
		hash = 79 * hash + Float.floatToIntBits(this.amt);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		final ItemDataStack other = (ItemDataStack) obj;
		if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
			return false;
		}
		return Float.floatToIntBits(this.amt) == Float.floatToIntBits(other.amt);
	}

	@Override
	public String toString() {
		return "ItemDataStack{" + "item=" + item + ", amt=" + amt + '}';
	}
}
