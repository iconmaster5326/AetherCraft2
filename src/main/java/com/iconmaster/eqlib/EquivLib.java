package com.iconmaster.eqlib;

import com.iconmaster.eqlib.config.ConfigHandler;
import com.iconmaster.eqlib.config.ConfigRegistry;
import com.iconmaster.eqlib.examples.NumericSystem;
import com.iconmaster.eqlib.recipe.RecipeRegistry;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import java.io.File;
import java.util.Scanner;
import net.minecraftforge.common.config.Configuration;

/**
 *
 * @author iconmaster
 */
@Mod(modid = EquivLib.MODID, version = EquivLib.VERSION)
public class EquivLib {

	public static final String MODID = "EquivLib";
	public static final String VERSION = "@VERSION@";
	
	public static File configRoot;
	public static File mainConfigDir;
	public static Configuration mainConfig;
	public static File modsConfigDir;
	
	@Mod.Instance("EquivLib")
	public static EquivLib instance = new EquivLib();
	
	@SidedProxy(clientSide = "com.iconmaster.eqlib.ClientProxy", serverSide = "com.iconmaster.eqlib.CommonProxy")
	public static CommonProxy proxy;
	
	public static NumericSystem sys; //for testing
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		//sys = new NumericSystem(); //for testing
		
		configRoot = new File(event.getModConfigurationDirectory(), "eqlib/");
		configRoot.mkdir();
		
		mainConfigDir = new File(configRoot,"eqlib.cfg");
		
		mainConfig = new Configuration(mainConfigDir);
		mainConfig.load();
		RecipeRegistry.oreDictBlacklist = mainConfig.getStringList("oreDictBlacklist", "general", new String[0], "A blacklist of ore dictionary entries NOT to equate.");
		mainConfig.save();
		
		modsConfigDir = new File(configRoot, "mods/");
		modsConfigDir.mkdir();
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event) {
		proxy.registerEvents();
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		for (ConfigHandler handler : ConfigRegistry.handlers.values()) {
			File dir = new File(modsConfigDir, handler.modName+"/");
			dir.mkdir();
			File dir2 = new File(dir, "values/");
			dir2.mkdir();
			handler.loadConfig(dir);
		}
	}
	
	public static String readAll(Scanner in) {
		StringBuilder sb = new StringBuilder();
		while (in.hasNext()) {
			sb.append(in.nextLine());
			sb.append("\n");
		}
		return sb.toString();
	}
}
