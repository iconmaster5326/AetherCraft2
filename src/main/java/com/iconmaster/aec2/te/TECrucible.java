package com.iconmaster.aec2.te;

import com.iconmaster.aec2.AetherCraft;
import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.Reactor;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.network.AetherCraftPacketHandler;
import com.iconmaster.aec2.network.CrucibleSyncPacket;
import com.iconmaster.aec2.network.HeatSyncPacket;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

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
		if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
			if (inventory[0]!=null) {
				if (inventory[0].getItem() instanceof ItemCompound) {
					Compound c = Compound.readFromNBT(inventory[0].getTagCompound());
					if (c!=null) {
						reactor.addReactant(c);
						decrStackSize(0, 1);
					}
				}
			}
			int oldH = reactor.heat;
			reactor.step();
			
			if (oldH!=reactor.heat) {
				AetherCraftPacketHandler.HANDLER.sendToAllAround(new HeatSyncPacket(this.xCoord,this.yCoord,this.zCoord,reactor.heat), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,4));
			}
			
			if (tick%10==0) {
				AetherCraftPacketHandler.HANDLER.sendToAllAround(new CrucibleSyncPacket(this.xCoord,this.yCoord,this.zCoord,reactor), new NetworkRegistry.TargetPoint(worldObj.provider.dimensionId,xCoord,yCoord,zCoord,4));
			}
		} else {
			reactor.step();
		}
	}
	
	public void openHatch() {
		if (reactor.reactants.isEmpty()) {
			return;
		}

		Compound c = reactor.reactants.get(0);
		ItemStack item = new ItemStack(AetherCraft.compound);
		item.stackTagCompound =  new NBTTagCompound();
		c.writeToNBT(item.stackTagCompound);

		if (inventory[1]!=null && !areStackable(item, inventory[1])) {
			return;
		}

		reactor.reactants.remove(0);

		if (inventory[1]==null) {
			inventory[1] = item;
		} else {
			inventory[1].stackSize += 1;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound tagCompound) {
		super.readFromNBT(tagCompound);
		
		this.reactor = Reactor.readFromNBT(tagCompound.getCompoundTag("reactor"));
	}
	
	@Override
	public void writeToNBT(NBTTagCompound tagCompound) {
		super.writeToNBT(tagCompound);
		
		NBTTagCompound tag = new NBTTagCompound();
		reactor.writeToNBT(tag);
		tagCompound.setTag("reactor", tag);
	}
}
