package com.iconmaster.aec2;

import com.iconmaster.aec2.aether.AetoForgeRegistry;
import com.iconmaster.aec2.aether.CompoundSystem;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.block.AetherCraftBlock;
import com.iconmaster.aec2.block.AetherCraftTEBlock;
import com.iconmaster.aec2.block.BlockCrucible;
import com.iconmaster.aec2.block.BlockTextures;
import com.iconmaster.aec2.client.gui.GuiCooler;
import com.iconmaster.aec2.client.gui.GuiCrucible;
import com.iconmaster.aec2.client.gui.GuiForge;
import com.iconmaster.aec2.client.gui.GuiFormer;
import com.iconmaster.aec2.client.gui.GuiHeater;
import com.iconmaster.aec2.client.gui.GuiSpectrometer;
import com.iconmaster.aec2.client.gui.GuiStill;
import com.iconmaster.aec2.gui.AetherCraftGuiHandler;
import com.iconmaster.aec2.gui.ContainerCooler;
import com.iconmaster.aec2.gui.ContainerCrucible;
import com.iconmaster.aec2.gui.ContainerForge;
import com.iconmaster.aec2.gui.ContainerFormer;
import com.iconmaster.aec2.gui.ContainerHeater;
import com.iconmaster.aec2.gui.ContainerSpectrometer;
import com.iconmaster.aec2.gui.ContainerStill;
import com.iconmaster.aec2.item.AetherCraftItem;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.item.ItemForgedPick;
import com.iconmaster.aec2.item.ItemTextures;
import com.iconmaster.aec2.network.AetherCraftPacketHandler;
import com.iconmaster.aec2.te.TECooler;
import com.iconmaster.aec2.te.TECrucible;
import com.iconmaster.aec2.te.TEData;
import com.iconmaster.aec2.te.TEForge;
import com.iconmaster.aec2.te.TEFormer;
import com.iconmaster.aec2.te.TEHeater;
import com.iconmaster.aec2.te.TESpectrometer;
import com.iconmaster.aec2.te.TEStill;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mod(modid = AetherCraft.MODID, version = AetherCraft.VERSION)
public class AetherCraft {

	@SidedProxy(clientSide = "com.iconmaster.aec2.client.ClientProxy", serverSide = "com.iconmaster.aec2.CommonProxy")
	public static CommonProxy proxy;

	@Mod.Instance("AetherCraft2")
	public static AetherCraft instance = new AetherCraft();

	public static final String MODID = "AetherCraft2";
	public static final String VERSION = "@VERSION@";

	public static CreativeTabs tabAetherCraft = new CreativeTabs("aetherCraft") {
		@Override
		public ItemStack getIconItemStack() {
			return new ItemStack(compound, 1, 0);
		}

		@Override
		public Item getTabIconItem() {
			return null;
		}
	};

	public static HashMap<String, AetherCraftBlock> blocks = new HashMap<String, AetherCraftBlock>();
	public static HashMap<String, AetherCraftTEBlock> teBlocks = new HashMap<String, AetherCraftTEBlock>();
	public static HashMap<String, AetherCraftItem> items = new HashMap<String, AetherCraftItem>();
	public static HashMap<String, TEData> tes = new HashMap<String, TEData>();

	//important items get thier own static members
	public static ItemCompound compound;

	public static Random aetherRandom = new Random(5326);

	public static void register(AetherCraftBlock thing) {
		blocks.put(thing.name, thing);
		thing.register();
	}

	public static void register(AetherCraftTEBlock thing) {
		teBlocks.put(thing.name, thing);
		thing.register();
	}

	public static void register(AetherCraftItem thing) {
		items.put(thing.name, thing);
		thing.register();
	}

	public static void register(TEData data) {
		tes.put(data.name, data);
		data.register();
	}
	
	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		ItemConversionRegistry.sys = new CompoundSystem();
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new AetherCraftGuiHandler());
		AetherCraftPacketHandler.init();

		register(new AetherCraftTEBlock("still", new BlockTextures("aec2:still"), TEStill.class));
		register(new TEData("aec2.still", TEStill.class, ContainerStill.class, GuiStill.class));

		register(new AetherCraftTEBlock("spectrometer", new BlockTextures("aec2:spectrometer"), TESpectrometer.class));
		register(new TEData("aec2.spectrometer", TESpectrometer.class, ContainerSpectrometer.class, GuiSpectrometer.class));

		register(new BlockCrucible("crucible", new BlockTextures("aec2:crucible"), TECrucible.class));
		register(new TEData("aec2.crucible", TECrucible.class, ContainerCrucible.class, GuiCrucible.class));

		register(new AetherCraftTEBlock("former", new BlockTextures("aec2:former"), TEFormer.class));
		register(new TEData("aec2.former", TEFormer.class, ContainerFormer.class, GuiFormer.class));

		register(new AetherCraftTEBlock("heater", new BlockTextures("aec2:heater"), TEHeater.class));
		register(new TEData("aec2.heater", TEHeater.class, ContainerHeater.class, GuiHeater.class));

		register(new AetherCraftTEBlock("cooler", new BlockTextures("aec2:cooler"), TECooler.class));
		register(new TEData("aec2.cooler", TECooler.class, ContainerCooler.class, GuiCooler.class));

		register(new AetherCraftTEBlock("forge", new BlockTextures("aec2:forge"), TEForge.class));
		register(new TEData("aec2.forge", TEForge.class, ContainerForge.class, GuiForge.class));

		compound = new ItemCompound();
		register(compound);
		
		register(new ItemForgedPick("forgedPick", new ItemTextures("aec2:forgedPick","aec2:forgedRod")));

		//ItemConversionRegistry.addConversion(new ItemStack(Blocks.dirt), new CRatio(new Compound(new Ratio(Aether.SOLGEM, 1),new Ratio(Aether.HAETRONOUS, 1)),1));
		TECooler.registerCoolant(new ItemStack(Items.snowball), 4);
		TECooler.registerCoolant(new ItemStack(Blocks.snow), 16);
		TECooler.registerCoolant(new ItemStack(Blocks.ice), 32);
		TECooler.registerCoolant(new ItemStack(Blocks.packed_ice), 64);
		TECooler.registerCoolant(new ItemStack(Items.water_bucket), 16);
		TECooler.registerCoolant(new ItemStack(Items.potionitem), 8);
		TECooler.registerCoolant(new ItemStack(Blocks.water), 16);
		
		//add Forge recipes
		AetoForgeRegistry.registerForgeRecipes();
		
		proxy.registerRenders();
	}

	@EventHandler
	public void serverStart(FMLServerStartingEvent event) {
		ItemConversionRegistry.addConversions();
	}
}
