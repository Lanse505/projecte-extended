package lanse505.projecteextended.create.mappers.done;

import com.simibubi.create.AllRecipeTypes;
import com.simibubi.create.content.contraptions.components.crusher.CrushingRecipe;
import com.simibubi.create.content.contraptions.components.mixer.MixingRecipe;
import com.simibubi.create.content.contraptions.processing.ProcessingOutput;
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

import java.util.List;
import java.util.stream.Collectors;

@RecipeTypeMapper(requiredMods = CreateProvider.MODID, priority = 2)
public class MixingRecipeMapper implements IRecipeTypeMapper {

  @Override
  public String getName() {
    return "Mixing Recipe";
  }

  @Override
  public String getDescription() {
    return "Maps Create Mixing Recipes";
  }

  @Override
  public boolean canHandle(RecipeType<?> recipeType) {
    return recipeType == AllRecipeTypes.MIXING.getType();
  }

  @Override
  public boolean handleRecipe(IMappingCollector<NormalizedSimpleStack, Long> mapper, Recipe<?> recipe, INSSFakeGroupManager inssFakeGroupManager) {
    if (!(recipe instanceof MixingRecipe mixing)) {
      return false;
    }
    boolean handled = false;
    NonNullList<Ingredient> inputs = mixing.getIngredients();
    NonNullList<ProcessingOutput> outputs = mixing.getRollableResults().stream().filter(output -> output.getChance() >= 1f).collect(Collectors.toCollection(NonNullList::create));

    NonNullList<ItemStack> firstInputList = NonNullList.create();
    NonNullList<ItemStack> secondInputList = NonNullList.create();

    if (!inputs.isEmpty()) {
      int count = 0;
      for (Ingredient ingredient : inputs) {
        if (count == 0) {
          firstInputList.addAll(List.of(ingredient.getItems()));
          count++;
        } else {
          secondInputList.addAll(List.of(ingredient.getItems()));
        }
      }
    }

    if (!firstInputList.isEmpty() && !secondInputList.isEmpty()) {
      for(ItemStack first : firstInputList) {
        for (ItemStack second : secondInputList) {
          NormalizedSimpleStack nssFirst = NSSItem.createItem(first);
          NormalizedSimpleStack nssSecond = NSSItem.createItem(second);
          for (ProcessingOutput output : outputs) {
            ItemStack outputStack = output.getStack();
            NormalizedSimpleStack nssOutput = NSSItem.createItem(outputStack);
            IngredientHelper helper = new IngredientHelper(mapper);
            helper.put(nssFirst, first.getCount());
            helper.put(nssSecond, second.getCount());
            if (helper.addAsConversion(nssOutput, outputStack.getCount())) {
              handled = true;
            }
          }
        }
      }
    } else if (!firstInputList.isEmpty()) {
      for (ItemStack first : firstInputList) {
        NormalizedSimpleStack nssFirst = NSSItem.createItem(first);
        for (ProcessingOutput output : outputs) {
          ItemStack outputStack = output.getStack();
          NormalizedSimpleStack nssOutput = NSSItem.createItem(outputStack);
          IngredientHelper helper = new IngredientHelper(mapper);
          helper.put(nssFirst, first.getCount());
          if (helper.addAsConversion(nssOutput, outputStack.getCount())) {
            handled = true;
          }
        }
      }
    }
    return handled;
  }
}
