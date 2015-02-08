package com.iconmaster.aec2.network;


import com.iconmaster.aec2.AetherCraft;
import cpw.mods.fml.common.network.NetworkRegistry;
import cpw.mods.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import cpw.mods.fml.relauncher.Side;

public class AetherCraftPacketHandler {
	public static final SimpleNetworkWrapper HANDLER = NetworkRegistry.INSTANCE.newSimpleChannel(AetherCraft.MODID);
	public static int id = 0;
	
	public static void register(Class claz, Side side) {
		HANDLER.registerMessage(claz, claz, id, side);
		id++;
	}
	
	public static void init() {
		register(HeatSyncPacket.class, Side.CLIENT);
		register(RequestHeatSyncPacket.class, Side.SERVER);
	}
}
