package com.iconmaster.aec2.item;

import com.iconmaster.aec2.AetherCraft;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

/**
 *
 * @author iconmaster
 */
public class AetherCraftItem extends Item {
	public String name;
	public int metas = 1;
	public ItemTextures textures;

	public AetherCraftItem(String name, ItemTextures textures, int metas) {
		this.name = name;
		this.metas = metas;
		this.textures = textures;
		this.setHasSubtypes(true);
		
		this.setUnlocalizedName(getUnlocalizedName());
	}
	
	public AetherCraftItem(String name, ItemTextures textures) {
		this.name = name;
		this.textures = textures;
		this.setHasSubtypes(false);
		
		this.setUnlocalizedName(getUnlocalizedName());
	}
	
	public void register() {
		GameRegistry.registerItem(this,name);
		this.setCreativeTab(AetherCraft.tabAetherCraft);
	}

	@Override
	public String getUnlocalizedName() {
		return "aec2."+name;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IIconRegister register) {
		textures.register(register);
	}

	@Override
	public IIcon getIconIndex(ItemStack stack) {
		return textures.getTexture(stack.getItemDamage());
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass) {
		return textures.getTexture(stack.getItemDamage());
	}
	
	@Override
	public void getSubItems(Item par1,CreativeTabs tab,List list) {
		for (int i = 0; i < metas; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}
}
