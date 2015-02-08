package com.iconmaster.aec2.te;

import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.aether.Reactor;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.network.AetherCraftPacketHandler;
import com.iconmaster.aec2.network.HeatSyncPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 *
 * @author iconmaster
 */
public class TECrucible extends AetherCraftTE {
	public Reactor reactor;
	
	@SideOnly(Side.CLIENT)
	public int realHeat;

	public TECrucible() {
		super();
	}
	
	@Override
	public void setup() {
		invSize = 2;
		name = "aec2.crucible";
		reactor = new Reactor();
		reactor.heat = 100;
	}

	@Override
	public void update() {
		if (inventory[0]!=null) {
			if (inventory[0].getItem() instanceof ItemCompound) {
				Compound c = Compound.readFromNBT(inventory[0].getTagCompound());
				if (c!=null) {
					reactor.addReactant(c);
					decrStackSize(0, 1);
				}
			} else {
				Compound[] cpds = ItemConversionRegistry.getComposition(inventory[0]);
				if (cpds!=null) {
					for (Compound c : cpds) {
						reactor.addReactant(c);
					}
					decrStackSize(0, 1);
				}
			}
		}
		int oldH = reactor.heat;
		reactor.step();
		
		if (oldH!=reactor.heat && FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER)
			AetherCraftPacketHandler.HANDLER.sendToAllAround(new HeatSyncPacket(this.xCoord,this.yCoord,this.zCoord,reactor.heat), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,4));
	}
}
