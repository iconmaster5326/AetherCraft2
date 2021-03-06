package com.iconmaster.aec2.block;

import com.iconmaster.aec2.AetherCraft;
import com.iconmaster.aec2.te.AetherCraftTE;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 *
 * @author iconmaster
 */
public class AetherCraftTEBlock extends BlockContainer {

	public String name;
	public Class<? extends ItemBlock> itemclass = ItemBlock.class;
	public BlockTextures textures;
	public int subBlocks;
	public Class<? extends AetherCraftTE> teclass;

	public AetherCraftTEBlock(String name, BlockTextures textures, int subBlocks, Class<? extends AetherCraftTE> teclass) {
		super(Material.rock);
		this.setHardness(1.5f);

		this.name = name;
		this.textures = textures;
		this.subBlocks = subBlocks;
		this.teclass = teclass;
	}

	public AetherCraftTEBlock(String name, BlockTextures textures, Class<? extends AetherCraftTE> teclass) {
		this(name, textures, 1, teclass);
	}

	public AetherCraftTEBlock addItemClass(Class<? extends ItemBlock> itemclass) {
		this.itemclass = itemclass;
		return this;
	}

	@Override
	public String getUnlocalizedName() {
		return "tile.aec2." + name;
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

	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		dropItems(world, x, y, z);
		super.breakBlock(world, x, y, z, par5, par6);
	}

	public void dropItems(World world, int x, int y, int z) {
		Random rand = new Random();
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (!(tileEntity instanceof IInventory)) {
			return;
		}

		IInventory inventory = (IInventory) tileEntity;

		for (int i = 0; i < inventory.getSizeInventory(); i++) {
			ItemStack item = inventory.getStackInSlot(i);

			if (item != null && item.stackSize > 0) {
				float rx = rand.nextFloat() * 0.8F + 0.1F;
				float ry = rand.nextFloat() * 0.8F + 0.1F;
				float rz = rand.nextFloat() * 0.8F + 0.1F;
				EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, new ItemStack(item.getItem(), item.stackSize, item.getItemDamage()));

				if (item.hasTagCompound()) {
					entityItem.getEntityItem().setTagCompound(
				   (NBTTagCompound) item.getTagCompound().copy());
				}

				float factor = 0.05F;
				entityItem.motionX = rand.nextGaussian() * factor;
				entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
				entityItem.motionZ = rand.nextGaussian() * factor;
				world.spawnEntityInWorld(entityItem);
				item.stackSize = 0;
			}
		}
	}
	
	@Override
	public TileEntity createNewTileEntity(World world, int par2) {
		try {
			return teclass.newInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
	
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9) {
		TileEntity te = world.getTileEntity(x, y, z);

		if (te == null || player.isSneaking()) {
			return false;
		}

		player.openGui(AetherCraft.instance, 0, world, x, y, z);
		return true;
	}
}
