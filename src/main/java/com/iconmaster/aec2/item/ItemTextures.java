package com.iconmaster.aec2.item;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

/**
 *
 * @author iconmaster
 */
public class ItemTextures {
	public String[] names;
	public IIcon[] icons;
	public boolean hasMeta = true;

	public ItemTextures(String... names) {
		this.names = names;
	}
	
	public ItemTextures(String names) {
		this.names = new String[] {names};
		hasMeta = false;
	}
	
	public void register(IIconRegister iconRegister) {
		icons = new IIcon[names.length];
		for (int i=0;i<names.length;i++) {
			icons[i] = iconRegister.registerIcon(names[i]);
		}
	}
	
	public IIcon getTexture(int meta) {
		if (hasMeta) {
			return icons[meta];
		} else {
			return icons[0];
		}
	}
}
