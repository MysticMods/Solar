package mart.solar.setup;

import mart.solar.RegistryManager;
import mart.solar.capability.energycapability.EnergyCapability;
import mart.solar.capability.energycapability.EnergyCapabilityStorage;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.crafting.AltarCraftingRecipeRegistry;
import mart.solar.enums.EnergyRegistry;
import mart.solar.event.SolarEvents;
import mart.solar.ritual.RitualRegistry;
import mart.solar.tile.*;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ModSetup {

    public void preInit(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IEnergyCapability.class, new EnergyCapabilityStorage(), EnergyCapability::new);

        MinecraftForge.EVENT_BUS.register(new RegistryManager());
        MinecraftForge.EVENT_BUS.register(new SolarEvents());

        SolarUtil.init();
        RgbColorUtil.init();
        AltarCraftingRecipeRegistry.init();

        RitualRegistry.init();
        EnergyRegistry.init();
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

