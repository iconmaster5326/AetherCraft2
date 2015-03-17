package com.iconmaster.eqlib.recipe.handler;

import com.iconmaster.eqlib.recipe.ItemDataStack;
import com.iconmaster.eqlib.recipe.RecipeHandler;
import com.iconmaster.eqlib.recipe.RecipeLink;
import com.iconmaster.eqlib.recipe.RecipeRegistry;
import java.util.ArrayList;
import java.util.List;
import net.minecraftforge.oredict.ShapelessOreRecipe;

/**
 *
 * @author iconmaster
 */
public class ShapelessOreCraftingHandler implements RecipeHandler {

	@Override
	public List<RecipeLink> getRecipes() {
		List<RecipeLink> a = new ArrayList<RecipeLink>();
		
		for (Object obj : RecipeRegistry.vanillaCrafting.get(ShapelessOreRecipe.class)) {
			ShapelessOreRecipe recipe = (ShapelessOreRecipe) obj;
			RecipeLink link = new RecipeLink();
			
			for (Object stack : recipe.getInput()) {
				if (stack!=null) {
					link.inputs.add(new ItemDataStack(RecipeRegistry.flatten(stack)));
				}
			}
			
			link.outputs.add(new ItemDataStack(recipe.getRecipeOutput()));
			
			a.add(link);
		}
		
		return a;
	}
	
}
