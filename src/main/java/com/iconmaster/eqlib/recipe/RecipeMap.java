package com.iconmaster.eqlib.recipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iconmaster
 */
public class RecipeMap<T> {

	public List<ItemNode<T>> nodes;
	public EquivHandler<T> handler;

	public RecipeMap(EquivHandler handler) {
		this.handler = handler;
	}

	public RecipeMap generateNodes() {
		nodes = new ArrayList<ItemNode<T>>();
		for (RecipeLink link : RecipeRegistry.getRecipes()) {
			for (Object why_java_why : link.inputs) {
				ItemDataStack stack = (ItemDataStack) why_java_why;
				ItemNode node = new ItemNode(stack.item);
				if (nodes.contains(node)) {
					nodes.get(nodes.indexOf(node)).usedAsInput.add(link);
				} else {
					node.usedAsInput.add(link);
					nodes.add(node);
				}
			}
			
			for (Object why_java_why : link.outputs) {
				ItemDataStack stack = (ItemDataStack) why_java_why;
				ItemNode node = new ItemNode(stack.item);
				if (nodes.contains(node)) {
					nodes.get(nodes.indexOf(node)).usedAsOutput.add(link);
				} else {
					node.usedAsOutput.add(link);
					nodes.add(node);
				}
			}
		}
		
		Map<ItemData, T> acceptedValues = handler.getAcceptedValues(this);
		for (ItemNode<T> node : nodes) {
			if (acceptedValues.containsKey(node.item)) {
				node.acceptedValue = acceptedValues.get(node.item);
			}
		}
		
		return this;
	}
	
	public Map<ItemData, T> getMap() {
		int lastHash;
		
		do {
			lastHash = nodes.hashCode();
			
			for (ItemNode<T> node : nodes) {
				if (node.acceptedValue==null) {
					node.calculatedValues = handler.calculateValues(this, node);
				}
			}
		} while (lastHash!=nodes.hashCode());
		
		Map<ItemData, T> map = new HashMap<ItemData, T>();
		for (ItemNode<T> node : nodes) {
			T value = handler.getCorrectValue(this, node);
			if (value!=null) {
				map.put(node.item, value);
			}
		}
		return map;
	}

	public ItemNode<T> findNode(ItemData item) {
		for (ItemNode<T> node : nodes) {
			if (node.item.equals(item)) {
				return node;
			}
		}
		return null;
	}
}
