package com.iconmaster.eqlib.recipe;

import java.util.List;
import java.util.Map;

/**
 *
 * @author iconmaster
 */
public interface EquivHandler<T> {
	public Map<ItemData, T> getAcceptedValues(RecipeMap<T> map);
	public List<T> calculateValues(RecipeMap<T> map, ItemNode<T> node);
	public T getCorrectValue(RecipeMap<T> map, ItemNode<T> node);
}
