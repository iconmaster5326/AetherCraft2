package com.iconmaster.eqlib.recipe;

import java.util.Map;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class EquivRegistry<T> {
	public Map<ItemData, T> map;

	public EquivRegistry(Map<ItemData, T> map) {
		this.map = map;
	}
	
	public T getValue(ItemStack stack) {
		ItemData item = new ItemData(stack);
		return map.get(item);
	}
}
