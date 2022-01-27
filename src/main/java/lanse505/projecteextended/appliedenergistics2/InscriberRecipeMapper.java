package lanse505.projecteextended.appliedenergistics2;

import appeng.recipes.handlers.InscriberProcessType;
import appeng.recipes.handlers.InscriberRecipe;
import lanse505.projecteextended.util.IngredientHelper;
import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.mapper.recipe.INSSFakeGroupManager;
import moze_intel.projecte.api.mapper.recipe.IRecipeTypeMapper;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import moze_intel.projecte.api.nss.NSSItem;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

@RecipeTypeMapper(requiredMods = "ae2", priority = 1)
public class InscriberRecipeMapper implements IRecipeTypeMapper {

  @Override
  public String getName() {
    return "InscriberRecipe";
  }

  @Override
  public String getDescription() {
    return "Maps AE2 Inscriber Recipes";
  }

  @Override
  public boolean canHandle(RecipeType<?> recipeType) {
    return recipeType == InscriberRecipe.TYPE;
  }

  @Override
  public boolean handleRecipe(IMappingCollector<NormalizedSimpleStack, Long> mapper, Recipe<?> recipe, INSSFakeGroupManager inssFakeGroupManager) {
    if (!(recipe instanceof InscriberRecipe inscriber)) {
      return false;
    }
    ItemStack[] top = inscriber.getTopOptional().getItems();
    ItemStack[] middle = inscriber.getMiddleInput().getItems();
    ItemStack[] bottom = inscriber.getBottomOptional().getItems();
    ItemStack output = inscriber.getResultItem();

    if (!output.isEmpty()) {
      IngredientHelper helper = new IngredientHelper(mapper);
      for (ItemStack centerStack : middle) {
        NormalizedSimpleStack nssCenter = null;
        if (!centerStack.isEmpty()) {
          nssCenter = NSSItem.createItem(centerStack);
        }
        for (ItemStack topStack : top) {
          NormalizedSimpleStack nssTop = null;
          if (!topStack.isEmpty()) {
            nssTop = NSSItem.createItem(topStack);
          }
          for (ItemStack bottomStack : bottom) {
            NormalizedSimpleStack nssBottom = null;
            if (!bottomStack.isEmpty())
              nssBottom = NSSItem.createItem(bottomStack);
              helper.put(nssTop, top.length);
              helper.put(nssCenter, middle.length);
              helper.put(nssBottom, bottom.length);
            if (helper.addAsConversion(output)) {
              return true;
            }
          }
        }
        helper.put(nssCenter, middle.length);
        if (helper.addAsConversion(output)) {
          return true;
        }
      }
    }
    return false;
  }
}
