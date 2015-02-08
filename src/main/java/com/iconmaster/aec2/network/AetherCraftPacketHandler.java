package com.iconmaster.aec2.network;


import com.iconmaster.aec2.AetherCraft;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;

public class AetherCraftPacketHandler {
	public static final SimpleNetworkWrapper HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCraft.MODID);
	
	public static void init() {
		//HANDLER.registerMessage(DeviceSyncPacket.class, DeviceSyncPacket.class, 0, Side.CLIENT);
	}
}
