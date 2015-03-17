package com.iconmaster.eqlib.recipe;

import com.iconmaster.eqlib.EquivLib;
import com.iconmaster.eqlib.config.ConfigHandler;
import com.iconmaster.eqlib.config.ConfigRegistry;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public abstract class EquivSystem<T> {
	public final String modId;
	public SystemConfigHandler<T> config;
	public SystemHandler<T> handler;
	
	public abstract T sumValues(List<InputStack<T>> input, double factor);
	public abstract Map<ItemData, T> getBaseValues();
	public abstract T getBestValue(List<T> input);
	
	public abstract String toString(T item);
	public abstract T fromString(String item);
	
	public static class InputStack<T> {
		public T input;
		public double amt;

		public InputStack(T input, double amt) {
			this.input = input;
			this.amt = amt;
		}
	}
	
	public static class SystemConfigHandler<T> extends ConfigHandler<T> {
		public EquivSystem<T> sys;

		public SystemConfigHandler(EquivSystem<T> sys) {
			super(sys.modId, sys.handler);
			this.sys = sys;
		}

		@Override
		public String toString(T item) {
			return sys.toString(item);
		}

		@Override
		public T fromString(String item) {
			return sys.fromString(item);
		}
	}
	
	public static class SystemHandler<T> implements EquivHandler<T> {
		public EquivSystem<T> sys;

		public SystemHandler(EquivSystem<T> sys) {
			this.sys = sys;
		}

		@Override
		public Map<ItemData, T> getAcceptedValues(RecipeMap<T> map) {
			File values = new File(sys.config.configDir, "values/");
			File defaults = new File(values, "default.eqlib");
			if (!defaults.exists()) {
				try {
					defaults.createNewFile();
					PrintWriter pw = new PrintWriter(defaults);
					sys.config.values = sys.getBaseValues();
					pw.print(sys.config.valuesToString());
					pw.flush();
					pw.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			}
			HashMap<ItemData, T> amap = new HashMap<ItemData, T>();
			for (File f : values.listFiles()) {
				try {
					Scanner in = new Scanner(f);
					amap.putAll(sys.config.stringToValues(EquivLib.readAll(in)));
				} catch (FileNotFoundException ex) {
					ex.printStackTrace();
				}
			}
			return amap;
		}

		@Override
		public List<T> calculateValues(RecipeMap<T> map, ItemNode<T> node) {
			ArrayList<T> a = new ArrayList<T>();
		
			for (RecipeLink link : node.usedAsOutput) {
				if (link.outputs.size()==1) {
					ArrayList<InputStack<T>> sum = new ArrayList<InputStack<T>>();
					boolean failed = true;
					for (ItemDataStack stack : link.inputs) {
						failed = false;
						ItemNode<T> node2 = map.findNode(stack.item);
						if (node2!=null) {
							T d = getCorrectValue(map, node2);
							if (d==null) {
								failed = true;
								break;
							} else {
								sum.add(new InputStack<T>(d, stack.amt));
							}
						} else {
							failed = true;
							break;
						}
					}

					if (!failed) {
						a.add(sys.sumValues(sum, link.outputs.get(0).amt));
					}
				}
			}

			for (RecipeLink link : node.usedAsInput) {
				if (link.inputs.size()==1) {
					ArrayList<InputStack<T>> sum = new ArrayList<InputStack<T>>();
					boolean failed = true;
					for (ItemDataStack stack : link.outputs) {
						failed = false;
						ItemNode<T> node2 = map.findNode(stack.item);
						if (node2!=null) {
							T d = getCorrectValue(map, node2);
							if (d==null) {
								failed = true;
								break;
							} else {
								sum.add(new InputStack<T>(d, stack.amt));
							}
						} else {
							failed = true;
							break;
						}
					}

					if (!failed) {
						a.add(sys.sumValues(sum, link.inputs.get(0).amt));
					}
				}
			}

			return a;
		}

		@Override
		public T getCorrectValue(RecipeMap<T> map, ItemNode<T> node) {
			if (node.acceptedValue!=null) {
				return node.acceptedValue;
			}
			if (node.calculatedValues==null || node.calculatedValues.isEmpty()) {
				return null;
			}
			return sys.getBestValue(node.calculatedValues);
		}
	}

	public EquivSystem(String modId) {
		this.modId = modId;
		handler = new SystemHandler(this);
		config = new SystemConfigHandler(this);
		
		ConfigRegistry.registerConfig(config);
	}
	
	public T getValue(ItemData stack) {
		return config.values.get(stack);
	}
	
	public T getValue(ItemStack stack) {
		return getValue(new ItemData(stack));
	}
}
