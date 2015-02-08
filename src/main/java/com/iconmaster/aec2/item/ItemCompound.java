package com.iconmaster.aec2.item;

import com.iconmaster.aec2.aether.Aether;
import com.iconmaster.aec2.aether.Compound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author iconmaster
 */
public class ItemCompound extends AetherCraftItem {

	public ItemCompound() {
		super("compound", new ItemTextures("aec2:compound"),1);
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		Compound cpd = Compound.readFromNBT(stack.getTagCompound());
		if (cpd==null) {
			return "Unknown Compound";
		} else {
			return cpd.name;
		}
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public int getColorFromItemStack(ItemStack stack, int par2) {
		Compound cpd = Compound.readFromNBT(stack.getTagCompound());
		if (cpd==null) {
			return super.getColorFromItemStack(stack, par2);
		} else {
			return cpd.color;
		}
	}
	
	@Override
	public void getSubItems(Item par1,CreativeTabs tab,List list) {
		for (Aether a : Aether.values()) {
			ItemStack stack = new ItemStack(this, 1, 0);
		
			stack.setTagCompound(new NBTTagCompound());
			Compound cpd = new Compound(new Compound.Ratio(a, 1));
			cpd.writeToNBT(stack.getTagCompound());

			list.add(stack);
		}
		
	}
}
