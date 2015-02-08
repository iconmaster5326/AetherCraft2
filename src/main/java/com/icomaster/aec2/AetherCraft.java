package com.icomaster.aec2;

import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;

@Mod(modid = AetherCraft.MODID, version = AetherCraft.VERSION)
public class AetherCraft {
	@SidedProxy(clientSide = "com.iconmaster.aec2.client.ClientProxy", serverSide = "com.iconmaster.aec2.CommonProxy")
	public static CommonProxy proxy;

	public static final String MODID = "AetherCraft2";
	public static final String VERSION = "@VERSION@";

	@EventHandler
	public void init(FMLInitializationEvent event) {
		
	}
}
