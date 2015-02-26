package com.iconmaster.aec2.aether;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class ForgeRegistry {
	public static class ForgeInput {
		public static final int HARDNESS = 0;
		public static final int DENSITY = 1;
		public static final int ENERGY = 2;
		public static final int REACTIVITY = 3;
		public static final int STABILITY = 4;
		
		public ItemStack item;
		public boolean compound;
		public int amt;
		public int[] statsNeeded = new int[5];
		
		public int getStat(Compound c, int i) {
			switch (i) {
				case HARDNESS:
					return c.hardness;
				case DENSITY:
					return c.density;
				case ENERGY:
					return c.energy;
				case REACTIVITY:
					return c.reactivity;
				case STABILITY:
					return c.stability;
			}
			return -1;
		}

		public ForgeInput(ItemStack item) {
			this.item = item;
			this.compound = false;
		}
		
		public ForgeInput(int amt, int... statsNeeded) {
			this.compound = true;
			this.statsNeeded = statsNeeded;
		}
		
		public boolean isValid(ItemStack stack) {
			if (compound) {
				if (stack.stackSize<amt) {
					return false;
				}
				Compound c = Compound.readFromNBT(stack.stackTagCompound);
				if (c==null) {
					return false;
				}
				for (int i=0;i<statsNeeded.length;i++) {
					if (getStat(c, i)<statsNeeded[i]) {
						return false;
					}
				}
				return true;
			} else {
				if (stack.stackSize<item.stackSize) {
					return false;
				}
				return item.getItem()==stack.getItem() && (item.getHasSubtypes() ? (stack.getItemDamage()==item.getItemDamage()) : true);
			}
		}
	}
	
	public static abstract class ForgeRecipe {
		public Collection<ForgeInput> inputs;
		public String name;
		public String desc;

		public ForgeRecipe(Collection<ForgeInput> inputs, String name, String desc) {
			this.inputs = inputs;
			this.name = name;
			this.desc = desc;
		}
		
		public abstract ItemStack getOutput(ItemStack... inputs);
		public abstract ItemStack getDisplayStack();
	}
	
	public static List<ForgeRecipe> recipes = new ArrayList<ForgeRecipe>();
	
	public static void addRecipe(ForgeRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static void registerForgeRecipes() {
		addRecipe(new ForgeRecipe(Arrays.asList(new ForgeInput[] {null}), "Test Item", "Test item desc goes here") {
			@Override
			public ItemStack getOutput(ItemStack... inputs) {
				return null;
			}

			@Override
			public ItemStack getDisplayStack() {
				return new ItemStack(Items.apple);
			}
		});
		
		addRecipe(new ForgeRecipe(Arrays.asList(new ForgeInput[] {null,null}), "Test Item 2", "Test item desc goes here 2") {
			@Override
			public ItemStack getOutput(ItemStack... inputs) {
				return null;
			}

			@Override
			public ItemStack getDisplayStack() {
				return new ItemStack(Items.arrow);
			}
		});
		
		addRecipe(new ForgeRecipe(Arrays.asList(new ForgeInput[] {null,null,null}), "Test Item 3", "Test item desc goes here 3") {
			@Override
			public ItemStack getOutput(ItemStack... inputs) {
				return null;
			}

			@Override
			public ItemStack getDisplayStack() {
				return new ItemStack(Items.baked_potato);
			}
		});
	}
}
