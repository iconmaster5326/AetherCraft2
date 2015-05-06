package com.iconmaster.aec2.aether;

import com.iconmaster.aec2.aether.ItemConversionRegistry.CRatio;
import com.iconmaster.aec2.aether.ItemConversionRegistry.RatioList;
import com.iconmaster.eqlib.recipe.EquivSystem;
import com.iconmaster.eqlib.recipe.ItemData;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author iconmaster
 */
public class CompoundSystem extends EquivSystem<RatioList> {

	public CompoundSystem() {
		super("AetherCraft2");
	}

	@Override
	public RatioList sumValues(List<InputStack<RatioList>> input, double factor) {
		RatioList sum = new RatioList();
		for (InputStack<RatioList> stack : input) {
			List<CRatio> rl = new ArrayList<CRatio>();
			for (CRatio cr : sum.ratios) {
				rl.add(new CRatio(cr.c, cr.amt));
			}
			for (CRatio cr : stack.input.ratios) {
				cr = new CRatio(cr.c, cr.amt);
				cr.amt *= stack.amt;
				boolean found  = false;
				for (CRatio cr2 : rl) {
					if (cr2.c.equals(cr.c)) {
						cr2.amt += cr.amt;
						found = true;
						break;
					}
				}
				if (!found) {
					rl.add(cr);
				}
			}
			sum = new RatioList(rl.toArray(new CRatio[0]));
		}
		
		for (CRatio cr : sum.ratios) {
			cr.amt /= factor;
		}
		return sum;
	}

	@Override
	public Map<ItemData, RatioList> getBaseValues() {
		Map<ItemData, RatioList> map = new HashMap<ItemData, RatioList>();
		map.put(new ItemData("minecraft:log",0), fromString("2HA1SO:8"));
		map.put(new ItemData("minecraft:log",1), fromString("2HA1SO:8"));
		map.put(new ItemData("minecraft:log",2), fromString("2HA1SO:8"));
		map.put(new ItemData("minecraft:log",3), fromString("2HA1SO:8"));
		map.put(new ItemData("minecraft:log2",0), fromString("2HA1SO:8"));
		map.put(new ItemData("minecraft:log2",1), fromString("2HA1SO:8"));
		map.put(new ItemData("minecraft:cobblestone"), fromString("1DR1MY:2"));
		return map;
	}

	@Override
	public RatioList getBestValue(List<RatioList> input) {
		return input.get(0);
	}

	@Override
	public String toString(RatioList item) {
		StringBuilder sb = new StringBuilder();
		if (item.ratios.length!=0) {
			for (CRatio cr : item.ratios) {
				sb.append(cr.c.toConfigString());
				sb.append(":");
				sb.append(cr.amt);
				sb.append(",");
			}
			sb.deleteCharAt(sb.length()-1);
		}
		return sb.toString();
	}

	@Override
	public RatioList fromString(String item) {
		String[] subs = item.split(",");
		ArrayList<CRatio> a = new ArrayList<CRatio>();
		for (String sub : subs) {
			String[] subs2 = sub.split(":");
			if (subs2.length==2) {
				Compound c = Compound.fromConfigString(subs2[0]);
				int amt = Integer.parseInt(subs2[1]);
				a.add(new CRatio(c, amt));
			}
		}
		return new RatioList(a.toArray(new CRatio[0]));
	}
	
}
