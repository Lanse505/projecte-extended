package lanse505.projecteextended.create;

import com.simibubi.create.AllItems;
import lanse505.projecteextended.ProjectEExtended;
import moze_intel.projecte.api.data.CustomConversionProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;

public class CreateProvider extends CustomConversionProvider {
  public static final String MODID = "create";

  public CreateProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected void addCustomConversions() {
    createConversionBuilder(new ResourceLocation(ProjectEExtended.MODID, MODID))
            .comment("Set defaults conversions for Create")
            // Zinc
            .before(AllItems.ZINC_INGOT.asStack(), 128);

  }

}
