package com.iconmaster.aec2;

import com.iconmaster.aec2.block.AetherCraftBlock;
import com.iconmaster.aec2.block.AetherCraftTEBlock;
import com.iconmaster.aec2.block.BlockTextures;
import com.iconmaster.aec2.client.gui.AetherCraftGui;
import com.iconmaster.aec2.gui.AetherCraftContainer;
import com.iconmaster.aec2.gui.AetherCraftGuiHandler;
import com.iconmaster.aec2.item.AetherCraftItem;
import com.iconmaster.aec2.item.ItemCompound;
import com.iconmaster.aec2.te.AetherCraftTE;
import com.iconmaster.aec2.te.TEData;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.SidedProxy;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.network.NetworkRegistry;
import java.util.HashMap;
import java.util.Random;
import net.minecraft.creativetab.CreativeTabs;
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
			return new ItemStack(items.get("compound"), 1, 0);
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
		
		register(new AetherCraftTEBlock("testBlock", new BlockTextures("aec2:still"), AetherCraftTE.class));
		register(new TEData("aec.te", AetherCraftTE.class, AetherCraftContainer.class, AetherCraftGui.class));

		register(new ItemCompound());
	}
}
