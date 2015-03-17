package com.iconmaster.eqlib.recipe.handler;

import com.iconmaster.eqlib.recipe.ItemDataStack;
import com.iconmaster.eqlib.recipe.RecipeHandler;
import com.iconmaster.eqlib.recipe.RecipeLink;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;

/**
 *
 * @author iconmaster
 */
public class FurnaceHandler implements RecipeHandler {

	@Override
	public List<RecipeLink> getRecipes() {
		List<RecipeLink> a = new ArrayList<RecipeLink>();
		
		for (Object obj : FurnaceRecipes.smelting().getSmeltingList().entrySet()) {
			Map.Entry<ItemStack, ItemStack> entry = ((Map.Entry)obj);
			
			RecipeLink link = new RecipeLink();
			link.inputs.add(new ItemDataStack(entry.getKey()));
			link.outputs.add(new ItemDataStack(entry.getValue()));
			
			a.add(link);
		}
		
		return a;
	}
	
}
