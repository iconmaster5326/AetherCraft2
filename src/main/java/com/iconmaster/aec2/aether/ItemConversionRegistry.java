package com.iconmaster.aec2.aether;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import scala.actors.threadpool.Arrays;

/**
 *
 * @author iconmaster
 */
public class ItemConversionRegistry {

	public static HashMap<Integer, ArrayList<HashSet<Compound>>> itemToAether = new HashMap<Integer, ArrayList<HashSet<Compound>>>();
	public static HashMap<HashSet<Compound>, ArrayList<ItemStack>> aetherToItem = new HashMap<HashSet<Compound>, ArrayList<ItemStack>>();

	public static int stackHash(ItemStack stack) {
		int hash = 7;
		hash = 89 * hash + Item.itemRegistry.getNameForObject(stack.getItem()).hashCode();
		hash = 89 * hash + (stack.getItem().getHasSubtypes() ? stack.getItemDamage() : 0);
		return hash;
	}

	public static void addConversion(ItemStack stack, Compound... cpds) {
		ArrayList<HashSet<Compound>> ita = itemToAether.get(stackHash(stack));
		if (ita==null) {
			ita = new ArrayList<HashSet<Compound>>();
			itemToAether.put(stackHash(stack), ita);
		}
		
		HashSet<Compound> newCpds = new HashSet<Compound>(Arrays.asList(cpds));
		ita.add(newCpds);
		
		ArrayList<ItemStack> ati = aetherToItem.get(newCpds);
		if (ati==null) {
			ati = new ArrayList<ItemStack>();
			aetherToItem.put(newCpds, ati);
		}
		
		ati.add(stack);
	}
	
	public static Compound[] getComposition(ItemStack stack) {
		int hash = stackHash(stack);
		Random r = new Random();
		
		ArrayList<HashSet<Compound>> a = itemToAether.get(hash);
		if (a==null) {
			//TODO: remove this later
			addConversion(stack, Compound.randomCompound(-1));
			a = itemToAether.get(hash);
		}
		HashSet<Compound> cpds = a.get(r.nextInt(a.size()));
		return cpds.toArray(new Compound[0]);
	}
}
