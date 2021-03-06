package com.iconmaster.aec2.aether;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

/**
 *
 * @author iconmaster
 */
public class Reactor {
	public ArrayList<Compound> reactants = new ArrayList<Compound>();
	public int heat = 0;
	
	public Random r = new Random();
	
	public void addReactant(Compound c, int amt) {
		for (int i=0;i<amt;i++) {
			reactants.add(c);
		}
	}
	
	public void addReactant(Compound c) {
		addReactant(c, 1);
	}
	
	public void step() {
		if (reactants.size()>1) {
			int pick = r.nextInt(reactants.size());
			Compound c1 = reactants.remove(pick);
			pick = r.nextInt(reactants.size());
			Compound c2 = reactants.remove(pick);

			{
				int[] a = new int[Aether.values().length];
				int i=0;
				for (int amt : c1.aethers) {
					a[i] += amt;
					i++;
				}
				i=0;
				for (int amt : c2.aethers) {
					a[i] += amt;
					i++;
				}

				int factor = Compound.normalize(a);

				Compound c3 = new Compound(a);

				if ((c1.reactivity+c2.reactivity+heat/5)/2>c3.reactivity) {
					int left = Math.abs(c1.getTotalComponents()+c2.getTotalComponents()-c3.getTotalComponents()*factor);
					
					if (left>0) {
						heat += left * 20;
					}

					heat += (c1.energy+c2.energy)-c3.energy*factor;

					addReactant(c3, factor);
				} else {
					reactants.add(c1);
					reactants.add(c2);
				}
			}
		}
		
		doDecay();
	}
	
	public void doDecay() {
		boolean recurse = false;
		
		for (Compound c : ((ArrayList<Compound>)reactants.clone())) {
			int size = c.getTotalComponents();
			if (size>(c.stability-heat/5)*2 && c.getTotalComponents()>2) {
				int[] a,a2;
				do {
					a = Arrays.copyOf(c.aethers, c.aethers.length);
					a2 = new int[Aether.values().length];
					for (int j=0;j<a.length;j++) {
						if (a[j]>0) {
							int diff = r.nextInt(a[j]+1);
							a[j] = a[j] - diff;
							a2[j] = diff;
						}
					}
				} while (Compound.components(a)==0 || Compound.components(a2)==0);
				
				int f1 = Compound.normalize(a);
				int f2 = Compound.normalize(a2);

				Compound c1 = new Compound(a);
				Compound c2 = new Compound(a2);

				reactants.remove(c);
				addReactant(c1, f1);
				addReactant(c2, f2);

				heat += c.energy-(c1.energy+c2.energy);

				recurse = true;
			}
		}
		
		if (recurse) {
			doDecay();
		}
	}
	
	public static Reactor readFromNBT(NBTTagCompound tag) {
		System.out.println(tag);
		Reactor r = new Reactor();
		r.heat = tag.getInteger("heat");
		NBTTagList ctag = tag.getTagList("reactants", 10);
		for (int i=0;i<ctag.tagCount();i++) {
			NBTTagCompound temp = ctag.getCompoundTagAt(i);
			r.reactants.add(Compound.readFromNBT(temp));
		}
		return r;
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		tag.setInteger("heat", heat);
		NBTTagList ctag = tag.getTagList("reactants", 10);
		for (Compound c : reactants) {
			NBTTagCompound temp = new NBTTagCompound();
			c.writeToNBT(temp);
			ctag.appendTag(temp);
		}
		tag.setTag("reactants", ctag);
		System.out.println(tag);
	}
	
	public void serialize(ByteBuf bytes) {
		bytes.writeInt(heat);
		bytes.writeInt(reactants.size());
		
		for (Compound c : reactants) {
			c.serialize(bytes);
		}
	}
	
	public static Reactor deserialize(ByteBuf bytes) {
		Reactor r = new Reactor();
		
		r.heat = bytes.readInt();
		int len = bytes.readInt();
		
		for (int i=0;i<len;i++) {
			Compound c = Compound.deserialize(bytes);
			r.reactants.add(c);
		}
		
		return r;
	}
}
