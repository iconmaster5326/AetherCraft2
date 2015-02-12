package com.iconmaster.aec2.aether;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

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
			return c.hashCode() - o.c.hashCode();
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

	public static int stackHash(ItemStack stack) {
		int hash = 7;
		hash = 89 * hash + Item.itemRegistry.getNameForObject(stack.getItem()).hashCode();
		hash = 89 * hash + (stack.getItem().getHasSubtypes() ? stack.getItemDamage() : 0);
		return hash;
	}

	public static void addConversion(ItemStack stack, RatioList cpds) {
		Arrays.sort(cpds.ratios);
		ArrayList<RatioList> ita = itemToAether.get(stackHash(stack));
		if (ita == null) {
			ita = new ArrayList<RatioList>();
			itemToAether.put(stackHash(stack), ita);
		}

		ita.add(cpds);

		addMat(stack, cpds.ratios);
	}

	public static RatioList getComposition(ItemStack stack) {
		int hash = stackHash(stack);
		Random r = new Random();

		ArrayList<RatioList> a = itemToAether.get(hash);
		if (a == null) {
			return null;
		}
		RatioList cpds = a.get(r.nextInt(a.size()));
		return cpds;
	}

	public static class LookupNode {

		public ArrayList<ItemStack> dest = new ArrayList<ItemStack>();
		public LookupNode left;
		public LookupNode down;
		public CRatio ratio;

		public LookupNode(LookupNode left, LookupNode down, CRatio ratio) {
			this.left = left;
			this.down = down;
			this.ratio = ratio;
		}

		@Override
		public String toString() {
			return "{" + left + "(" + ratio.amt + " x " + ratio.c.hashCode() + ")" + down + "}";
		}
	}

	public static class LookupTree {

		public LookupNode tree;

		public LookupTree(LookupNode tree) {
			this.tree = tree;
		}

		@Override
		public String toString() {
			return tree.toString();
		}
	}

	public static HashMap<Compound, LookupTree> aetherToItem = new HashMap<Compound, LookupTree>();

	public static void addMat(ItemStack stack, CRatio... ratios) {
		if (ratios.length == 0) {
			return;
		}
		Arrays.sort(ratios);
		CRatio first = ratios[0];

		LookupTree tree = aetherToItem.get(first.c);

		if (tree == null) {
			aetherToItem.put(first.c, new LookupTree(makeTree(ratios, stack)));
		} else {
			LookupNode oldTree = tree.tree;
			LookupNode newTree = makeTree(ratios, stack);
			LookupNode merged = merge(null, oldTree, newTree);
			if (merged != null) {
				tree.tree = merged;
			}
		}
	}

	public static LookupNode merge(LookupNode parent, LookupNode n1, LookupNode n2) {
		if (n1 == null) {
			parent.down = n2;
			return parent;
		}
		if (n2 == null) {
			parent.down = n1;
			return parent;
		}

		if (n1.ratio.equals(n2.ratio)) {
			n1.dest.addAll(n2.dest);
			return merge(n1, n1.down, n2.down);
		}

		ArrayList<LookupNode> lefts1a = new ArrayList<LookupNode>();
		ArrayList<LookupNode> lefts2a = new ArrayList<LookupNode>();

		LookupNode leaf = n1;
		while (leaf != null) {
			lefts1a.add(leaf);
			leaf = leaf.left;
		}
		leaf = n2;
		while (leaf != null) {
			lefts2a.add(leaf);
			leaf = leaf.left;
		}

		if (!n1.ratio.c.equals(n2.ratio.c)) {
			if (n1.ratio.compareTo(n2.ratio) < 0) {
				lefts2a.clear();
				LookupNode node = new LookupNode(null, n2, new CRatio(n1.ratio.c, 0));
				if (parent!=null)
					parent.down = node;
				lefts2a.add(node);
			} else {
				lefts1a.clear();
				LookupNode node = new LookupNode(null, n1, new CRatio(n2.ratio.c, 0));
				if (parent!=null)
					parent.down = node;
				lefts1a.add(node);
			}
		}

		HashMap<Integer, LookupNode> nodeMap = new HashMap<Integer, LookupNode>();
		for (LookupNode node : lefts1a) {
			nodeMap.put(node.ratio.amt, node);
		}
		for (LookupNode node : lefts2a) {
			if (nodeMap.containsKey(node.ratio.amt)) {
				LookupNode merged = merge(parent, nodeMap.get(node.ratio.amt), node);
				merged.dest.addAll(n1.dest);
				merged.dest.addAll(n2.dest);
				if (parent!=null) {
					parent.down = merged;
				}
				nodeMap.put(node.ratio.amt, merged);
			} else {
				nodeMap.put(node.ratio.amt, node);
			}
		}

		LookupNode[] nodes = nodeMap.values().toArray(new LookupNode[0]);
		Arrays.sort(nodes, new Comparator<LookupNode>() {

			@Override
			public int compare(LookupNode o1, LookupNode o2) {
				return o1.ratio.amt-o2.ratio.amt;
			}
		});
		for (int i = 0; i < nodes.length - 1; i++) {
			nodes[i].left = nodes[i + 1];
		}
		nodes[nodes.length - 1].left = null;
		
		if (parent!=null) {
			parent.down = nodes[0];
		}
		return parent==null?nodes[0]:parent;
	}

	public static LookupNode makeTree(CRatio[] ratios, ItemStack end) {
		LookupNode node = new LookupNode(null, null, ratios[0]);
		LookupNode leaf = node;
		for (int i = 1; i < ratios.length; i++) {
			LookupNode newLeaf = new LookupNode(null, null, ratios[i]);
			leaf.down = newLeaf;
			leaf = newLeaf;
		}

		leaf.dest.add(end);
		return node;
	}

	public static class LookupResult {
		public CRatio[] ratios;
		public ItemStack output;

		public LookupResult(CRatio[] ratios, ItemStack output) {
			this.ratios = ratios;
			this.output = output;
		}
	}
	
	public static ArrayList<LookupResult> getFormation(CRatio[] ratios, LookupNode node) {
		if (ratios==null || ratios.length==0) {
			return new ArrayList<LookupResult>();
		}
		
		int i=0;
		CRatio ratio = ratios[0];
		ArrayList<LookupResult> a = new ArrayList<LookupResult>();
		ArrayList<CRatio> newRs = new ArrayList<CRatio>();
		
		while (true) {
			if (node.ratio.c.equals(ratio.c) && node.ratio.amt>ratio.amt) {
				break;
			}
			//add single node's stuff
			newRs.add(node.ratio);
			for (ItemStack st : node.dest) {
				a.add(new LookupResult(newRs.toArray(new CRatio[0]), st));
			}
			newRs.remove(newRs.size()-1);
			
			if (node.ratio.c.equals(ratio.c)) {
				if (node.ratio.amt>ratio.amt) {
					break;
				}
				if (node.left!=null && node.left.ratio.amt<=ratio.amt) {
					//add all subnode's stuff
					a.addAll(getFormation(Arrays.copyOfRange(ratios, i+1, ratios.length), node));
					node = node.left;
					i++;
					if (node==null || i>=ratios.length) {
						break;
					}
				} else {
					newRs.add(node.ratio);
					node = node.down;
					i++;
					if (node==null || i>=ratios.length) {
						break;
					}
				}
			} else {
				newRs.add(node.ratio);
				node = node.down;
				i++;
				if (node==null || i>=ratios.length) {
					break;
				}
			}
		}
		
		return a;
	}
	
	public static ArrayList<LookupResult> getFormation(CRatio[] ratios) {
		if (ratios==null || ratios.length==0) {
			return new ArrayList<LookupResult>();
		}
		Arrays.sort(ratios);
		
		LookupTree tree = aetherToItem.get(ratios[0].c);
		if (tree==null) {
			return new ArrayList<LookupResult>();
		}
		
		return getFormation(ratios, tree.tree);
	}
}
