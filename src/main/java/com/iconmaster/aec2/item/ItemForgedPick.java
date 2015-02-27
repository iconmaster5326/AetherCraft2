package com.iconmaster.aec2.item;

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
		return 100;
	}

	@Override
	public double getAttackPower(ItemStack stack) {
		return 0;
	}

	@Override
	public float getDigSpeed(ItemStack stack) {
		return 4;
	}

	@Override
	public int getHarvestLevel(ItemStack stack) {
		return 2;
	}
	
}
