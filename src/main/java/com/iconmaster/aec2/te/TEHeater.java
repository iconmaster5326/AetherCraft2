package com.iconmaster.aec2.te;

import com.iconmaster.aec2.util.HeatNetwork;
import com.iconmaster.aec2.util.IHeatAcceptor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntityFurnace;

/**
 *
 * @author iconmaster
 */
public class TEHeater extends AetherCraftTE implements IHeatAcceptor {

	public TEHeater() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 1;
		name = "aec2.heater";
	}
	
	@Override
	public void update() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			ItemStack stack = inventory[0];
			
			if (stack!=null) {
				int fuel = TileEntityFurnace.getItemBurnTime(stack)/100;

				if (fuel>0) {
					HeatNetwork.addHeatToNetwork(worldObj, xCoord, yCoord, zCoord, fuel);
					decrStackSize(0, 1);
				}
			}
		}
	}
	
	@Override
	public boolean acceptsHeat() {
		return false;
	}

	@Override
	public void acceptHeat(int amt) {}
}
