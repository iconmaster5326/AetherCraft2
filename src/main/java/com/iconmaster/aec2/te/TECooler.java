package com.iconmaster.aec2.te;

import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.util.HeatNetwork;
import com.iconmaster.aec2.util.IHeatAcceptor;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.relauncher.Side;
import java.util.HashMap;
import net.minecraft.item.ItemStack;

/**
 *
 * @author iconmaster
 */
public class TECooler extends AetherCraftTE implements IHeatAcceptor {
	public static HashMap<Integer,Integer> coolants = new HashMap<Integer, Integer>();
	
	public static void registerCoolant(ItemStack stack, int amt) {
		coolants.put(ItemConversionRegistry.stackHash(stack), amt);
	}

	public TECooler() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 1;
		name = "aec2.cooler";
	}
	
	@Override
	public void update() {
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			ItemStack stack = inventory[0];
			
			if (stack!=null) {
				Integer fuel = coolants.get(ItemConversionRegistry.stackHash(stack));
				
				if (fuel!=null) {
					HeatNetwork.addHeatToNetwork(worldObj, xCoord, yCoord, zCoord, -fuel);
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
