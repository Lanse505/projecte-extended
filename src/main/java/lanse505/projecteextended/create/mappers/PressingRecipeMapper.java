package lanse505.projecteextended.create.mappers;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.press.PressingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
import com.simibubi.create.content.curiosities.tools.SandPaperPolishingRecipe;
import lanse505.projecteextended.create.CreateProvider;
import lanse505.projecteextended.util.IngredientHelper;
import moze_intel.projecte.api.mapper.collector.IMappingCollector;
import moze_intel.projecte.api.mapper.recipe.INSSFakeGroupManager;
import moze_intel.projecte.api.mapper.recipe.IRecipeTypeMapper;
import moze_intel.projecte.api.mapper.recipe.RecipeTypeMapper;
import moze_intel.projecte.api.nss.NSSItem;
import moze_intel.projecte.api.nss.NormalizedSimpleStack;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeType;

import java.util.stream.Collectors;

@RecipeTypeMapper(requiredMods = CreateProvider.MODID, priority = 1)
public class PressingRecipeMapper implements IRecipeTypeMapper {

  @Override
  public String getName() {
    return "Pressing Recipe";
  }

  @Override
  public String getDescription() {
    return "Maps Create Pressing Recipes";
  }

  @Override
  public boolean canHandle(RecipeType<?> recipeType) {
    return recipeType == AllRecipeTypes.PRESSING.getType();
  }

  @Override
  public boolean handleRecipe(IMappingCollector<NormalizedSimpleStack, Long> mapper, Recipe<?> recipe, INSSFakeGroupManager inssFakeGroupManager) {
    if (!(recipe instanceof PressingRecipe pressing)) {
      return false;
    }
    boolean handled = false;
    NonNullList<Ingredient> inputs = pressing.getIngredients();
    NonNullList<ProcessingOutput> outputs = pressing.getRollableResults().stream().filter(output -> output.getChance() >= 1f).collect(Collectors.toCollection(NonNullList::create));

    if (!inputs.isEmpty() && !outputs.isEmpty()) {
      for (Ingredient input : inputs) {
        for (ItemStack stack : input.getItems()) {
          NormalizedSimpleStack nssInput = NSSItem.createItem(stack);
          for (ProcessingOutput output : outputs) {
            ItemStack outputStack = output.getStack();
            NormalizedSimpleStack nssOutput = NSSItem.createItem(outputStack);
            IngredientHelper helper = new IngredientHelper(mapper);
            helper.put(nssInput, stack.getCount());
            if (helper.addAsConversion(nssOutput, outputStack.getCount())) {
              handled = true;
            }
          }
        }
      }
    }
    return handled;
  }
}
