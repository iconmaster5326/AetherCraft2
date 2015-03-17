package com.iconmaster.aec2.aether;

import io.netty.buffer.ByteBuf;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author iconmaster
 */
public class Compound {
	public static class Ratio {
		public Aether aether;
		public int amt;

		public Ratio(Aether aether, int amt) {
			this.aether = aether;
			this.amt = amt;
		}
	}
	
	public int[] aethers = new int[Aether.values().length];
	
	public int reactivity;
	public int stability;
	public int energy;
	public int density;
	public int hardness;
	
	public String name;
	public int color;
	
	public static int normalize(int[] aethers) {
		Integer gcd = null;
		for (int i=0;i<aethers.length;i++) {
			if (aethers[i]>0) {
				if (gcd == null) {
					gcd = aethers[i];
				} else {
					gcd = gcd(aethers[i],gcd);
				}
			}
		}
		
		for (int i=0;i<aethers.length;i++) {
			if (aethers[i]>0) {
				aethers[i] /= gcd;
			}
		}
		
		return gcd;
	}

	public void setProperties() {
		normalize(aethers);
		
		Random r = new Random(this.hashCode());
		
		int g = 0;
		int f = 0;
		int v = 0;
		int m = 0;
		int sum = 0;
		for (int i=0;i<aethers.length;i++) {
			if (aethers[i]>0) {
				Aether aether = Aether.values()[i];
				
				g += aethers[i]*aether.gleam;
				f += aethers[i]*aether.flair;
				v += aethers[i]*aether.vigor;
				m += aethers[i]*aether.mass;
				sum += aethers[i];
			}
		}
		g/=sum;f/=sum;v/=sum;m/=sum;
		g+=sum/3;f+=sum/3;v+=sum/3;m+=sum/3;
		
		hardness = min(g,f,v,m)+r.nextInt(g+f+v+m)/2;
		density = min(g,v,m/2)+r.nextInt(g+v+m+m)/2;
		energy = min(f,v/2,m)+r.nextInt(f+v+v+m)/2;
		reactivity = min(g,f/2,v)+r.nextInt(g+f+f+v)/2;
		stability = min(g/2,f,m)+r.nextInt(g+g+f+m)/2;
		
		Integer single = null;
		boolean crystal = true;
		for (int i=0;i<aethers.length;i++) {
			if (aethers[i]>0) {
				if (single==null) {
					single = i;
				} else {
					crystal = false;
					break;
				}
			}
		}
		
		if (crystal) {
			Aether aether = Aether.values()[single];
			name = "Crystallized "+aether;
		} else {
			name = randomName(r);
		}
		
		color = r.nextInt(0x1000000);
	}
	
	public Ratio[] ratios() {
		ArrayList<Ratio> a = new ArrayList<Ratio>();
		
		for (int i=0;i<aethers.length;i++) {
			if (aethers[i]>0) {
				a.add(new Ratio(Aether.values()[i], aethers[i]));
			}
		}
		return a.toArray(new Ratio[0]);
	}

	public Compound(Ratio... a) {
		for (Ratio r : a) {
			aethers[r.aether.ordinal()] = r.amt;
		}
		setProperties();
	}
	
	public Compound(List<Ratio> a) {
		for (Ratio r : a) {
			aethers[r.aether.ordinal()] = r.amt;
		}
		setProperties();
	}
	
	public Compound(int... a) {
		aethers = a;
		setProperties();
	}

	@Override
	public int hashCode() {
		int hash = 7;
		hash = 53 * hash + Arrays.hashCode(this.aethers);
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
		final Compound other = (Compound) obj;
		return Arrays.equals(this.aethers, other.aethers);
	}

	@Override
	public String toString() {
		return name;
	}
	
	public int getTotalComponents() {
		return components(aethers);
	}
	
	public static final String[] NAME_PARTS = new String[] {"ada","mant","mith","vigro","ala","alum","det","roth","rill","thoth","font","org","tame","gith","mega","giga","tera","mal","ex"};
	public static final String[] END_PARTS = new String[] {"ium","ite","is","ine","ril"};
	
	public static String randomName(Random r) {
		String s = "";
		for (int i=0;i<2+r.nextInt(2);i++) {
			s += NAME_PARTS[r.nextInt(NAME_PARTS.length)];
		}
		s += END_PARTS[r.nextInt(END_PARTS.length)];
		return s.substring(0,1).toUpperCase()+s.substring(1);
	}
	
	public static int min(int... items) {
		int low = Integer.MAX_VALUE;
		for (int item : items) {
			if (item<low) {
				low = item;
			}
		}
		return low;
	}
	
	public static int gcd(int number1, int number2) {
		if(number2 == 0) {
			return number1;
		}
		return gcd(number2, number1%number2);
	}
	
	public static int components(int[] aethers) {
		int sum = 0;
		for (int amt : aethers) {
			sum+=amt;
		}
		return sum;
	}
	
	public static Compound readFromNBT(NBTTagCompound tag) {
		if (tag==null) {
			return null;
		}
		
		byte[] bytes = tag.getByteArray("cpd");
		if (bytes.length==0) {
			return null;
		}
		int[] compo = new int[Aether.values().length];
		
		for (int i=0;i<bytes.length;i+=2) {
			byte aether = bytes[i];
			byte amt = bytes[i+1];
			
			compo[aether] = amt;
		}
		
		return new Compound(compo);
	}
	
	public void writeToNBT(NBTTagCompound tag) {
		Ratio[] rs = this.ratios();
		byte[] bytes = new byte[rs.length*2];
		
		int i=0;
		for (Ratio r : rs) {
			bytes[i] = (byte) r.aether.ordinal();
			bytes[i+1] = (byte) r.amt;
			i+=2;
		}
		
		tag.setByteArray("cpd", bytes);
	}
	
	public void serialize(ByteBuf bytes) {
		Ratio[] rs = this.ratios();
		
		bytes.writeInt(rs.length);
		
		for (Ratio r : rs) {
			bytes.writeByte(r.aether.ordinal());
			bytes.writeByte(r.amt);
		}
	}
	
	public static Compound deserialize(ByteBuf bytes) {
		int len = bytes.readInt();
		ArrayList<Ratio> rs = new ArrayList<Ratio>();
		
		for (int i=0; i<len; i++) {
			byte aether = bytes.readByte();
			byte amt = bytes.readByte();
			
			rs.add(new Ratio(Aether.values()[aether], amt));
		}
		
		return new Compound(rs);
	}
	
	public static Compound randomCompound(int size, Random r) {
		int[] a = new int[Aether.values().length];
		
		for (int i=0;i<size;i++) {
			a[r.nextInt(a.length)] += 1;
		}
		
		Compound c = new Compound(a);
		return c.name.startsWith("Crystal") ? randomCompound(size, r) : c;
	}
	
	public String toConfigString() {
		StringBuilder sb = new StringBuilder();
		for (int i=0;i<aethers.length;i++) {
			int amt = aethers[i];
			String name = Aether.values()[i].name.substring(0, 2).toUpperCase();
			sb.append(amt+name);
		}
		return sb.toString();
	}
	
	public static Compound fromConfigString(String s) {
		String[] aes = s.split("\\d+");
		String[] amts = s.split("\\D+");
		int[] cpd = new int[Aether.values().length];
		
		for (int i=0;i<aes.length;i++) {
			String ae = aes[i];
			String amt = amts[i];
			
			for (int j = 0;j<Aether.values().length;j++) {
				Aether a = Aether.values()[j];
				if (a.name.toUpperCase().startsWith(ae.toUpperCase())) {
					cpd[j] = Integer.parseInt(amt);
				}
			}
		}
		
		return new Compound(cpd);
	}
}
