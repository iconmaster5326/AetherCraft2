package com.iconmaster.aec2;

import com.iconmaster.aec2.aether.Aether;
import com.iconmaster.aec2.aether.Compound;
import com.iconmaster.aec2.aether.Compound.Ratio;
import com.iconmaster.aec2.aether.ItemConversionRegistry;
import com.iconmaster.aec2.block.AetherCraftBlock;
import com.iconmaster.aec2.block.AetherCraftTEBlock;
import com.iconmaster.aec2.block.BlockTextures;
import com.iconmaster.aec2.client.gui.GuiCrucible;
import com.iconmaster.aec2.client.gui.GuiFormer;
import com.iconmaster.aec2.client.gui.GuiSpectrometer;
import com.iconmaster.aec2.client.gui.GuiStill;
import com.iconmaster.aec2.gui.AetherCraftGuiHandler;
import com.iconmaster.aec2.gui.ContainerCrucible;
import com.iconmaster.aec2.gui.ContainerFormer;
import com.iconmaster.aec2.gui.ContainerSpectrometer;
import com.iconmaster.aec2.gui.ContainerStill;
import com.iconmaster.aec2.item.AetherCraftItem;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.network.AetherCraftPacketHandler;
import com.iconmaster.aec2.te.TECrucible;
import com.iconmaster.aec2.te.TEData;
import com.iconmaster.aec2.te.TEFormer;
import com.iconmaster.aec2.te.TESpectrometer;
import com.iconmaster.aec2.te.TEStill;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
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
	public void init(FMLInitializationEvent event) {
		NetworkRegistry.INSTANCE.registerGuiHandler(this, new AetherCraftGuiHandler());
		AetherCraftPacketHandler.init();
		
		register(new AetherCraftTEBlock("still", new BlockTextures("aec2:still"), TEStill.class));
		register(new TEData("aec2.still", TEStill.class, ContainerStill.class, GuiStill.class));
		
		register(new AetherCraftTEBlock("spectrometer", new BlockTextures("aec2:spectrometer"), TESpectrometer.class));
		register(new TEData("aec2.spectrometer", TESpectrometer.class, ContainerSpectrometer.class, GuiSpectrometer.class));

		register(new AetherCraftTEBlock("crucible", new BlockTextures("aec2:crucible"), TECrucible.class));
		register(new TEData("aec2.crucible", TECrucible.class, ContainerCrucible.class, GuiCrucible.class));
		
		register(new AetherCraftTEBlock("former", new BlockTextures("aec2:former"), TEFormer.class));
		register(new TEData("aec2.former", TEFormer.class, ContainerFormer.class, GuiFormer.class));

		compound = new ItemCompound();
		register(compound);
		
		ItemConversionRegistry.addConversion(new ItemStack(Blocks.dirt), new Compound(new Ratio(Aether.SOLGEM, 1),new Ratio(Aether.HAETRONOUS, 1)));
	}
	
	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {
		//register ALL the random compounds; ALL OF THEM
		//TODO: get rid of this, for God's sake
		Random r = new Random(5326);
		for (Object s : Item.itemRegistry.getKeys()) {
			Item item = (Item) Item.itemRegistry.getObject(s);
			ArrayList<ItemStack> a = new ArrayList<ItemStack>();
			item.getSubItems(item, null, a);
			for (int i=0;i<a.size();i++) {
				ItemConversionRegistry.addConversion(new ItemStack(item,1,i), Compound.randomCompound(2+r.nextInt(5), r));
			}
		}
	}
}
