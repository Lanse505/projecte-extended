package lanse505.projecteextended;

import lanse505.projecteextended.appliedenergistics2.AEProvider;
import lanse505.projecteextended.create.CreateProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ProjectEExtended.MODID)
public class ProjectEExtended {
  public static final String MODID = "projecteextended";
  private static final Logger LOGGER = LogManager.getLogger();

  public ProjectEExtended() {
    IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
    eventBus.addListener(this::setupDatagen);
  }

  private void setupDatagen(final GatherDataEvent event) {
    DataGenerator generator = event.getGenerator();
    generator.addProvider(new AEProvider(generator));
    generator.addProvider(new CreateProvider(generator));
  }

}
