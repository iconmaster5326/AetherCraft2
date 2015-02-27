package com.iconmaster.aec2.item;

import com.iconmaster.aec2.aether.Compound;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class ItemForgedPick extends ItemForgedTool {

	public ItemForgedPick(String name, ItemTextures textures) {
		super(name, textures, "pickaxe", ItemForgedTool.pickGoodOn);
	}

	@Override
	public int getMaxDamage(ItemStack stack) {
		Compound c1 = getCompo(stack, 1);
		Compound c2 = getCompo(stack, 2);
		
		if (c1==null || c2==null) {
			return 0;
		}
		
		return (int) (((c1.density+0D)+(c2.density+0D)/4)*20);
	}

	@Override
	public double getAttackPower(ItemStack stack) {
		Compound c1 = getCompo(stack, 1);
		Compound c2 = getCompo(stack, 2);
		
		if (c1==null || c2==null) {
			return 0;
		}
		
		return c1.hardness/10D;
	}

	@Override
	public float getDigSpeed(ItemStack stack) {
		Compound c1 = getCompo(stack, 1);
		Compound c2 = getCompo(stack, 2);
		
		if (c1==null || c2==null) {
			return 0;
		}
		
		return (((c1.hardness+0F)+(c2.hardness+0F)/2)/4);
	}

	@Override
	public int getHarvestLevel(ItemStack stack) {
		Compound c1 = getCompo(stack, 1);
		Compound c2 = getCompo(stack, 2);
		
		if (c1==null || c2==null) {
			return 0;
		}
		
		return c1.hardness/7;
	}

	@Override
	public int getItemEnchantability(ItemStack stack) {
		Compound c1 = getCompo(stack, 1);
		Compound c2 = getCompo(stack, 2);
		
		if (c1==null || c2==null) {
			return 0;
		}
		
		return 5+c1.energy/4+c2.energy/4;
	}
}
