package com.iconmaster.eqlib.recipe;

import com.iconmaster.eqlib.recipe.handler.FurnaceHandler;
import com.iconmaster.eqlib.recipe.handler.OreDictHandler;
import com.iconmaster.eqlib.recipe.handler.ShapedCraftingHandler;
import com.iconmaster.eqlib.recipe.handler.ShapedOreCraftingHandler;
import com.iconmaster.eqlib.recipe.handler.ShapelessCraftingHandler;
import com.iconmaster.eqlib.recipe.handler.ShapelessOreCraftingHandler;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author iconmaster
 */
public class RecipeRegistry {

	private static List<RecipeLink> recipes;
	public static List<RecipeHandler> handlers = new ArrayList<RecipeHandler>();
	public static Map<Class, List> vanillaCrafting;
	
	public static String[] oreDictBlacklist;
	
	static { //add default handlers
		handlers.add(new ShapedCraftingHandler());
		handlers.add(new ShapelessCraftingHandler());
		handlers.add(new ShapedOreCraftingHandler());
		handlers.add(new ShapelessOreCraftingHandler());
		handlers.add(new OreDictHandler());
		handlers.add(new FurnaceHandler());
	}

	public static List<RecipeLink> getRecipes() {
		if (recipes == null) {
			generateRecipesList();
		}
		return recipes;
	}

	public static void generateRecipesList() {
		vanillaCrafting = new HashMap();
		for (Object recipe : CraftingManager.getInstance().getRecipeList()) {
			if (!vanillaCrafting.containsKey(recipe.getClass())) {
				vanillaCrafting.put(recipe.getClass(), new ArrayList());
			}
			vanillaCrafting.get(recipe.getClass()).add(recipe);
		}
		
		recipes = new ArrayList<RecipeLink>();
		for (RecipeHandler handler : handlers) {
			recipes.addAll(handler.getRecipes());
		}
	}
	
	public static ItemStack flatten(Object obj) {
		if (obj instanceof ItemStack) {
			return (ItemStack) obj;
		} else if (obj instanceof List) {
			if (((List)obj).isEmpty()) {
				return null;
			}
			return flatten(((List)obj).get(0));
		} else if (obj instanceof String) {
			ArrayList<ItemStack> ores = OreDictionary.getOres((String)obj);
			if (ores.isEmpty()) {
				return null;
			}
			return ores.get(0);
		}
		
		return null;
	}
}
