package lanse505.projecteextended.appliedenergistics2;

import appeng.core.definitions.AEItems;
import lanse505.projecteextended.ProjectEExtended;
import moze_intel.projecte.api.data.CustomConversionProvider;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;

public class AEProvider extends CustomConversionProvider {

  public AEProvider(DataGenerator generator) {
    super(generator);
  }

  @Override
  protected void addCustomConversions() {
    createConversionBuilder(new ResourceLocation(ProjectEExtended.MODID, "appliedenergistics2"))
            .comment("Set default conversions for Applied Energistics 2")
            // Presses
            .before(AEItems.SILICON_PRESS, 1024)
            .before(AEItems.LOGIC_PROCESSOR_PRESS, 2048)
            .before(AEItems.CALCULATION_PROCESSOR_PRESS, 4096)
            .before(AEItems.ENGINEERING_PROCESSOR_PRESS, 8192)
            .before(AEItems.NAME_PRESS, 1024)
            // Certus
            .before(AEItems.CERTUS_QUARTZ_CRYSTAL, 256)
            .conversion(AEItems.CERTUS_QUARTZ_CRYSTAL).ingredient(AEItems.CERTUS_CRYSTAL_SEED).end()
            .conversion(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED).ingredient(AEItems.CERTUS_QUARTZ_CRYSTAL).end()
            .conversion(AEItems.CERTUS_QUARTZ_DUST).ingredient(AEItems.CERTUS_QUARTZ_CRYSTAL).end()
            // Fluix
            .conversion(AEItems.FLUIX_DUST, 2).ingredient(AEItems.CERTUS_QUARTZ_CRYSTAL_CHARGED).ingredient(Tags.Items.DUSTS_REDSTONE).ingredient(Items.QUARTZ).end()
            .conversion(AEItems.FLUIX_CRYSTAL).ingredient(AEItems.FLUIX_CRYSTAL_SEED).end();
  }
}
