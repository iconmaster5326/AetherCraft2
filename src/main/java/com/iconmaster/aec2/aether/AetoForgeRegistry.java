package com.iconmaster.aec2.aether;

import com.iconmaster.aec2.AetherCraft;
import cpw.mods.fml.common.registry.LanguageRegistry;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author iconmaster
 */
public class AetoForgeRegistry {
	public static class AetoForgeInput {
		public static final int HARDNESS = 0;
		public static final int DENSITY = 1;
		public static final int ENERGY = 2;
		public static final int REACTIVITY = 3;
		public static final int STABILITY = 4;
		
		public ItemStack item;
		public boolean compound;
		public int amt;
		public int[] statsNeeded = new int[5];
		
		protected String cachedDesc;
		
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
		
		public String getStatName(int i) {
			switch (i) {
				case HARDNESS:
					return LanguageRegistry.instance().getStringLocalization("strings.aec2.hardness");
				case DENSITY:
					return LanguageRegistry.instance().getStringLocalization("strings.aec2.density");
				case ENERGY:
					return LanguageRegistry.instance().getStringLocalization("strings.aec2.energy");
				case REACTIVITY:
					return LanguageRegistry.instance().getStringLocalization("strings.aec2.reactivity");
				case STABILITY:
					return LanguageRegistry.instance().getStringLocalization("strings.aec2.stability");
			}
			return "???";
		}

		public AetoForgeInput(ItemStack item) {
			this.item = item;
			this.compound = false;
			
			cachedDesc = item.stackSize+" x "+item.getDisplayName();
		}
		
		public AetoForgeInput(int amt, int... statsNeeded) {
			this.compound = true;
			this.statsNeeded = statsNeeded;
			this.amt = amt;
			
			cachedDesc = amt+" x any compound";
			
			boolean addedWith = false;
			for (int i=0;i<statsNeeded.length;i++) {
				int stat = statsNeeded[i];
				
				if (stat>0) {
					if (!addedWith) {
						addedWith = true;
						cachedDesc += " with";
					}
					
					cachedDesc += " "+getStatName(i).toLowerCase()+">"+stat+",";
				}
			}
			
			if (addedWith) {
				cachedDesc = cachedDesc.substring(0, cachedDesc.length()-1);
			}
		}
		
		public boolean isValid(ItemStack stack) {
			if (stack==null) {
				return false;
			}
			
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
		
		public String getDesc() {
			return cachedDesc;
		}
		
		public ItemStack onCraft(ItemStack input) {
			if (compound) {
				if (input.stackSize==amt) {
					return null;
				}
				input.stackSize-=amt;
				return input;
			} else {
				if (input.stackSize==item.stackSize) {
					return null;
				}
				input.stackSize-=item.stackSize;
				return input;
			}
		}
	}
	
	public static abstract class AetoForgeRecipe {
		public List<AetoForgeInput> inputs;
		public String name;
		public String[] desc;

		public AetoForgeRecipe(List<AetoForgeInput> inputs, String name, String... desc) {
			this.inputs = inputs;
			this.name = name;
			this.desc = desc;
		}
		
		public abstract ItemStack getOutput(ItemStack... inputs);
		public abstract ItemStack getDisplayStack();
	}
	
	public static List<AetoForgeRecipe> recipes = new ArrayList<AetoForgeRecipe>();
	
	public static void addRecipe(AetoForgeRecipe recipe) {
		recipes.add(recipe);
	}
	
	public static void registerForgeRecipes() {
		addRecipe(new AetoForgeRecipe(Arrays.asList(new AetoForgeInput[] {new AetoForgeInput(16,10,0,0,0,0), new AetoForgeInput(8,5,0,0,0,0)}), "Aetoforged Pickaxe", "DIGS STONE I GUESS") {
			@Override
			public ItemStack getOutput(ItemStack... inputs) {
				ItemStack stack = new ItemStack(AetherCraft.items.get("forgedPick"));
				stack.stackTagCompound = new NBTTagCompound();
				
				NBTTagCompound cpd1 = new NBTTagCompound();
				Compound.readFromNBT(inputs[0].stackTagCompound).writeToNBT(cpd1);
				stack.stackTagCompound.setTag("cpd1", cpd1);
				
				NBTTagCompound cpd2 = new NBTTagCompound();
				Compound.readFromNBT(inputs[1].stackTagCompound).writeToNBT(cpd2);
				stack.stackTagCompound.setTag("cpd2", cpd2);
				
				return stack;
			}

			@Override
			public ItemStack getDisplayStack() {
				return new ItemStack(AetherCraft.items.get("forgedPick"));
			}
		});
	}
}
