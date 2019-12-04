package mart.solar.setup;

import net.minecraft.data.DataGenerator;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ModSetup {

    public void preInit(FMLCommonSetupEvent event) {
//        CapabilityManager.INSTANCE.register(IEnergyCapability.class, new EnergyCapabilityStorage(), EnergyCapability::new);
//
//        MinecraftForge.EVENT_BUS.register(new RegistryManager());
//        MinecraftForge.EVENT_BUS.register(new SolarEvents());
//
//        SolarUtil.init();
//        RgbColorUtil.init();
//        AltarCraftingRecipeRegistry.init();
//
//        RitualRegistry.init();
//        EnergyRegistry.init();
    }

    public void gatherData (GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            //gen.addProvider(new EmbersBlockstateProvider(gen, event.getExistingFileHelper()));
            //gen.addProvider(new EmbersItemModelProvider(gen, event.getExistingFileHelper()));
            //gen.addProvider(new EmbersLangProvider(gen));
        }
        if (event.includeServer()) {
        }
    }

}

