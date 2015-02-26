package com.iconmaster.aec2.te;

import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.aether.ItemConversionRegistry.CRatio;
import com.iconmaster.aec2.aether.ItemConversionRegistry.LookupResult;
import com.iconmaster.aec2.item.ItemCompound;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.ArrayList;
import java.util.Random;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class TEFormer extends AetherCraftTE {

	public Random r = new Random();

	public TEFormer() {
		super();
	}

	@Override
	public void setup() {
		invSize = 8;
		name = "aec2.former";
	}

	@Override
	public void update() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			ArrayList<CRatio> ratios = new ArrayList<CRatio>();
			for (int i = 4; i < 8; i++) {
				if (inventory[i] != null && inventory[i].getItem() instanceof ItemCompound) {
					Compound c = Compound.readFromNBT(inventory[i].stackTagCompound);
					if (c != null) {
						loop: do {
							for (CRatio cr : ratios) {
								if (cr.c.equals(c)) {
									cr.amt += inventory[i].stackSize;
									break loop;
								}
							}
							ratios.add(new CRatio(c, inventory[i].stackSize));
						} while (false);
					}
				}
			}
			if (!ratios.isEmpty()) {
				ArrayList<LookupResult> list = ItemConversionRegistry.getFormation(ratios.toArray(new CRatio[0]));
				if (!list.isEmpty()) {
					LookupResult lr = list.get(r.nextInt(list.size()));
					ItemStack stack = lr.output;
					int output = getStackableSlot(stack, 0, 4);
					if (output != -1) {
						for (CRatio cr : lr.ratios) {
							boolean found = false;
							for (int i = 4; i < 8; i++) {
								if (inventory[i] != null && inventory[i].getItem() instanceof ItemCompound) {
									Compound c = Compound.readFromNBT(inventory[i].stackTagCompound);
									if (c != null && c.equals(cr.c)) {
										if (inventory[i].stackSize<cr.amt) {
											return;
										} else {
											found = true;
										}
									}
								}
							}
							
							if (!found) {
								return;
							}
						}
						
						for (CRatio cr : lr.ratios) {
							for (int i = 4; i < 8; i++) {
								if (inventory[i] != null && inventory[i].getItem() instanceof ItemCompound) {
									Compound c = Compound.readFromNBT(inventory[i].stackTagCompound);
									if (c != null && c.equals(cr.c)) {
										decrStackSize(i, cr.amt);
									}
								}
							}
						}

						if (inventory[output] == null) {
							inventory[output] = stack.copy();
						} else {
							inventory[output].stackSize += stack.stackSize;
						}
					}
				}
			}
		}
	}
}
