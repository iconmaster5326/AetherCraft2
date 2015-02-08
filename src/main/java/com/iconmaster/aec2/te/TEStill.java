package com.iconmaster.aec2.te;

import com.iconmaster.aec2.AetherCraft;
import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import java.util.ArrayList;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author iconmaster
 */
public class TEStill extends AetherCraftTE {

	public TEStill() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 8;
		name = "aec2.still";
	}
	
	@Override
	public void update() {
		int item = getFilledSlot(0, 4);
		if (item!=-1) {
			Compound[] cpds = ItemConversionRegistry.getComposition(inventory[item]);
			if (cpds==null) {
				return;
			}
			boolean sucsess = true;
			ArrayList<ItemStack> output = new ArrayList<ItemStack>();
			for (Compound cpd : cpds) {
				ItemStack cpdStack = new ItemStack(AetherCraft.compound);
				cpdStack.setTagCompound(new NBTTagCompound());
				cpd.writeToNBT(cpdStack.getTagCompound());
				
				int slot = getStackableSlot(cpdStack, 4, 8);
				if (slot==-1) {
					sucsess = false;
					break;
				}
				
				output.add(cpdStack);
			}
			
			if (sucsess) {
				for (ItemStack stack : output) {
					int slot = getStackableSlot(stack, 4, 8);
					if (slot!=-1) {
						if (inventory[slot]==null) {
							inventory[slot] = stack;
						} else {
							inventory[slot].stackSize += 1;
						}
					}
				}
				
				decrStackSize(item, 1);
			}
		}
	}
}
