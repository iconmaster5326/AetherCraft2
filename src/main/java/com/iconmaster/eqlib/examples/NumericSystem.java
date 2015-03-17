package com.iconmaster.eqlib.examples;

import com.iconmaster.eqlib.recipe.EquivSystem;
import com.iconmaster.eqlib.recipe.ItemData;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iconmaster
 */
public class NumericSystem extends EquivSystem<Double> {

	public NumericSystem() {
		super("example");
	}

	@Override
	public Double sumValues(List<InputStack<Double>> input, double factor) {
		double sum = 0;
		for (InputStack<Double> stack : input) {
			sum += stack.input * stack.amt;
		}
		return sum / factor;
	}

	@Override
	public Map<ItemData, Double> getBaseValues() {
		HashMap<ItemData, Double> map = new HashMap<ItemData, Double>();
		map.put(new ItemData("minecraft:planks",0), 4d);
		map.put(new ItemData("minecraft:planks",1), 4d);
		map.put(new ItemData("minecraft:planks",2), 4d);
		map.put(new ItemData("minecraft:planks",3), 4d);
		map.put(new ItemData("minecraft:planks",4), 4d);
		map.put(new ItemData("minecraft:planks",5), 4d);
		return map;
	}

	@Override
	public Double getBestValue(List<Double> input) {
		double low = Double.MAX_VALUE;
		for (Double d : input) {
			low = Math.min(low,d);
		}
		return low;
	}

	@Override
	public String toString(Double item) {
		return item.toString();
	}

	@Override
	public Double fromString(String item) {
		return Double.parseDouble(item);
	}
	
}
