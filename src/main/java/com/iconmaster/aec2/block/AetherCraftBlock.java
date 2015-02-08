package com.iconmaster.aec2.block;

import com.iconmaster.aec2.AetherCraft;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 *
 * @author iconmaster
 */
public class AetherCraftBlock extends Block {

	public String name;
	public Class<? extends ItemBlock> itemclass = ItemBlock.class;
	public BlockTextures textures;
	public int subBlocks;

	public AetherCraftBlock(String name, BlockTextures textures, int subBlocks) {
		super(Material.rock);
		this.setHardness(1.5f);

		this.name = name;
		this.textures = textures;
		this.subBlocks = subBlocks;
	}

	public AetherCraftBlock(String name, BlockTextures textures) {
		this(name, textures, 1);
	}

	public AetherCraftBlock addItemClass(Class<? extends ItemBlock> itemclass) {
		this.itemclass = itemclass;
		return this;
	}

	@Override
	public String getUnlocalizedName() {
		return "aec.tile." + name;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister iconRegister) {
		textures.register(iconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int metadata) {
		return textures.getTexture(metadata, side);
	}

	@Override
	public int damageDropped(int meta) {
		return meta;
	}

	@Override
	public void getSubBlocks(Item item, CreativeTabs tab, List list) {
		for (int i = 0; i < subBlocks; i++) {
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z) {
		return new ItemStack(this, 1, world.getBlockMetadata(x, y, z));
	}
	
	public void register() {
		GameRegistry.registerBlock(this, itemclass, name);
		this.setCreativeTab(AetherCraft.tabAetherCraft);
	}
}
