package com.iconmaster.aec2.block;

import com.iconmaster.aec2.AetherCraft;
import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TECrucible;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 *
 * @author iconmaster
 */
public class BlockCrucible extends AetherCraftTEBlock {

	public BlockCrucible(String name, BlockTextures textures, Class<? extends AetherCraftTE> teclass) {
		super(name, textures, teclass);
	}
	
	@Override
	public void breakBlock(World world, int x, int y, int z, Block par5, int par6) {
		Random rand = new Random();
		TileEntity tileEntity = world.getTileEntity(x, y, z);

		if (!(tileEntity instanceof TECrucible)) {
			return;
		}
		TECrucible te = (TECrucible) tileEntity;
		
		for (Compound c : te.reactor.reactants) {
			ItemStack item = new ItemStack(AetherCraft.compound);
			item.stackTagCompound = new NBTTagCompound();
			c.writeToNBT(item.stackTagCompound);
			
			float rx = rand.nextFloat() * 0.8F + 0.1F;
			float ry = rand.nextFloat() * 0.8F + 0.1F;
			float rz = rand.nextFloat() * 0.8F + 0.1F;
			
			EntityItem entityItem = new EntityItem(world, x + rx, y + ry, z + rz, item.copy());
			
			float factor = 0.05F;
			entityItem.motionX = rand.nextGaussian() * factor;
			entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
			entityItem.motionZ = rand.nextGaussian() * factor;
			world.spawnEntityInWorld(entityItem);
		}
		
		super.breakBlock(world, x, y, z, par5, par6);
	}
}
