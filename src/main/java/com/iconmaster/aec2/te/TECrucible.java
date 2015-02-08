package com.iconmaster.aec2.te;

import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.aether.Reactor;
import com.iconmaster.aec2.item.ItemCompound;

/**
 *
 * @author iconmaster
 */
public class TECrucible extends AetherCraftTE {
	public Reactor reactor = new Reactor();

	public TECrucible() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 2;
		name = "aec2.crucible";
	}

	@Override
	public void update() {
		if (inventory[0]!=null) {
			if (inventory[0].getItem() instanceof ItemCompound) {
				Compound c = Compound.readFromNBT(inventory[0].getTagCompound());
				if (c!=null) {
					reactor.addReactant(c);
					decrStackSize(0, 1);
				}
			} else {
				Compound[] cpds = ItemConversionRegistry.getComposition(inventory[0]);
				if (cpds!=null) {
					for (Compound c : cpds) {
						reactor.addReactant(c);
					}
					decrStackSize(0, 1);
				}
			}
		}
		reactor.step();
	}
}
