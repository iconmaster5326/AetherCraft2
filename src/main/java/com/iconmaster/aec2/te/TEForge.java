package com.iconmaster.aec2.te;

import com.iconmaster.aec2.aether.AetoForgeRegistry;
import com.iconmaster.aec2.gui.ContainerForge;
import net.minecraft.nbt.NBTTagCompound;

/**
 *
 * @author iconmaster
 */
public class TEForge extends AetherCraftTE {
	public int selection = -1;
	public int gridSize = 0;
	public ContainerForge container;

	public TEForge() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 5;
		name = "aec2.forge";
	}
	
	@Override
	public void update() {}

	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		selection = tagCompound.getInteger("sel");
		if (selection>-1 && selection<AetoForgeRegistry.recipes.size()) {
			gridSize = AetoForgeRegistry.recipes.get(selection).inputs.size();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		tagCompound.setInteger("sel", selection);
	}
}
