package com.iconmaster.aec2.item;

import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 *
 * @author iconmaster
 */
public class AetherCraftItem extends Item {
	public String name;
	public boolean hasMeta = false;
	public int metas;
	public ItemTextures textures;

	public AetherCraftItem(String name, ItemTextures textures, int metas) {
		this.name = name;
		this.metas = metas;
		this.textures = textures;
		this.hasMeta = true;
	}
	
	public AetherCraftItem(String name, ItemTextures textures) {
		this.name = name;
		this.textures = textures;
	}
	
	public void register() {
		GameRegistry.registerItem(this,name);
	}

	@Override
	public String getUnlocalizedName() {
		return "aec.item."+name;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return textures.getTexture(stack.getItemDamage());
	}
}
