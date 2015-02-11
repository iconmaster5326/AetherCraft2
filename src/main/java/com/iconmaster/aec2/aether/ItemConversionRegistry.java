package com.iconmaster.aec2.aether;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

/**
 *
 * @author iconmaster
 */
public class ItemConversionRegistry {
	public static class CRatio implements Comparable<CRatio> {
		public Compound c;
		public int amt;

		public CRatio(Compound c, int amt) {
			this.c = c;
			this.amt = amt;
		}

		@Override
		public int hashCode() {
			int hash = 3;
			hash = 47 * hash + this.c.hashCode();
			hash = 47 * hash + this.amt;
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
			final CRatio other = (CRatio) obj;
			if (this.c != other.c && (this.c == null || !this.c.equals(other.c))) {
				return false;
			}
			return this.amt == other.amt;
		}

		@Override
		public int compareTo(CRatio o) {
			return hashCode()-o.hashCode();
		}
	}
	
	public static class RatioList {
		public CRatio[] ratios;

		public RatioList(CRatio... ratios) {
			this.ratios = ratios;
		}

		@Override
		public int hashCode() {
			int hash = 7;
			hash = 53 * hash + java.util.Arrays.deepHashCode(this.ratios);
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
			final RatioList other = (RatioList) obj;
			return java.util.Arrays.deepEquals(this.ratios, other.ratios);
		}
	}

	public static HashMap<Integer, ArrayList<RatioList>> itemToAether = new HashMap<Integer, ArrayList<RatioList>>();
	public static HashMap<RatioList, ArrayList<ItemStack>> aetherToItem = new HashMap<RatioList, ArrayList<ItemStack>>();

	public static int stackHash(ItemStack stack) {
		int hash = 7;
		hash = 89 * hash + Item.itemRegistry.getNameForObject(stack.getItem()).hashCode();
		hash = 89 * hash + (stack.getItem().getHasSubtypes() ? stack.getItemDamage() : 0);
		return hash;
	}

	public static void addConversion(ItemStack stack, RatioList cpds) {
		Arrays.sort(cpds.ratios);
		ArrayList<RatioList> ita = itemToAether.get(stackHash(stack));
		if (ita==null) {
			ita = new ArrayList<RatioList>();
			itemToAether.put(stackHash(stack), ita);
		}
		
		ita.add(cpds);
		
		ArrayList<ItemStack> ati = aetherToItem.get(cpds);
		if (ati==null) {
			ati = new ArrayList<ItemStack>();
			aetherToItem.put(cpds, ati);
		}
		
		ati.add(stack);
	}
	
	public static RatioList getComposition(ItemStack stack) {
		int hash = stackHash(stack);
		Random r = new Random();
		
		ArrayList<RatioList> a = itemToAether.get(hash);
		if (a==null) {
			return null;
		}
		RatioList cpds = a.get(r.nextInt(a.size()));
		return cpds;
	}
	
	public static ItemStack getFormation(RatioList cpds) {
		ArrayList<ItemStack> a = aetherToItem.get(cpds);
		if (a==null || a.isEmpty()) {
			return null;
		}
		Random r = new Random();
		return a.get(r.nextInt(a.size()));
	}
}
