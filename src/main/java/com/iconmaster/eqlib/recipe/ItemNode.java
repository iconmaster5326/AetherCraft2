package com.iconmaster.eqlib.recipe;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author iconmaster
 */
public class ItemNode<T> {
	public ItemData item;
	public T acceptedValue;
	public List<T> calculatedValues = new ArrayList<T>();
	
	public List<RecipeLink> usedAsInput = new ArrayList<RecipeLink>();
	public List<RecipeLink> usedAsOutput = new ArrayList<RecipeLink>();

	public ItemNode(ItemData item, T acceptedValue, List<T> calcualtedValues) {
		this.item = item;
		this.acceptedValue = acceptedValue;
		this.calculatedValues = calcualtedValues;
	}
	
	public ItemNode(ItemData item) {
		this(item, null, null);
	}
	
	public ItemNode(ItemData item, T acceptedValue) {
		this(item, acceptedValue, null);
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 79 * hash + (this.item != null ? this.item.hashCode() : 0);
		hash = 79 * hash + (this.acceptedValue != null ? this.acceptedValue.hashCode() : 0);
		hash = 79 * hash + (this.calculatedValues != null ? this.calculatedValues.hashCode() : 0);
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
		final ItemNode<?> other = (ItemNode<?>) obj;
		if (this.item != other.item && (this.item == null || !this.item.equals(other.item))) {
			return false;
		}
		if (this.acceptedValue != other.acceptedValue && (this.acceptedValue == null || !this.acceptedValue.equals(other.acceptedValue))) {
			return false;
		}
		return !(this.calculatedValues != other.calculatedValues && (this.calculatedValues == null || !this.calculatedValues.equals(other.calculatedValues)));
	}

	@Override
	public String toString() {
		return "ItemNode{" + "item=" + item + ", acceptedValue=" + acceptedValue + ", calculatedValues=" + calculatedValues + ", usedAsInput=" + usedAsInput + ", usedAsOutput=" + usedAsOutput + '}';
	}
}
