package com.iconmaster.eqlib.recipe.handler;

import com.iconmaster.eqlib.recipe.ItemDataStack;
import com.iconmaster.eqlib.recipe.RecipeHandler;
import com.iconmaster.eqlib.recipe.RecipeLink;
import com.iconmaster.eqlib.recipe.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

/**
 *
 * @author iconmaster
 */
public class OreDictHandler implements RecipeHandler {

	@Override
	public List<RecipeLink> getRecipes() {
		List<RecipeLink> a = new ArrayList<RecipeLink>();
		
		for (String s : OreDictionary.getOreNames()) {
			boolean blacklisted = false;
			for (String black : RecipeRegistry.oreDictBlacklist) {
				if (black.equals(s)) {
					blacklisted = true;
				}
			}
			
			if (!blacklisted) {
			for (ItemStack item1 : OreDictionary.getOres(s)) {
					for (ItemStack item2 : OreDictionary.getOres(s)) {
						if (item1!=item2) {
							RecipeLink link = new RecipeLink();

							link.inputs.add(new ItemDataStack(item1));
							link.outputs.add(new ItemDataStack(item2));

							a.add(link);
						}
					}
				}
			}
		}
		
		return a;
	}
	
}
