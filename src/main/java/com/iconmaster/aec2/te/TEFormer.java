package com.iconmaster.aec2.te;

import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.aether.ItemConversionRegistry.CRatio;
import com.iconmaster.aec2.aether.ItemConversionRegistry.RatioList;
import com.iconmaster.aec2.item.ItemCompound;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class TEFormer extends AetherCraftTE {

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
			int input = getFilledSlot(4,8);
			if (input!=-1 && inventory[input].getItem() instanceof ItemCompound) {
				Compound c = Compound.readFromNBT(inventory[input].stackTagCompound);
				if (c!=null) {
					ItemStack stack = ItemConversionRegistry.getFormation(new RatioList(new CRatio(c,1)));
					if (stack!=null) {
						int output = getStackableSlot(stack, 0, 4);
						if (output!=-1) {
							decrStackSize(input, 1);
							
							if (inventory[output]==null) {
								inventory[output] = stack;
							} else {
								inventory[output].stackSize += 1;
							}
						}
					}
				}
			}
		}
	}
}
