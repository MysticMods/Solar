package mart.solar.setup;

import mart.solar.capability.energy.EnergyCapability;
import mart.solar.capability.energy.EnergyCapabilityStorage;
import mart.solar.capability.energy.IEnergyCapability;
import mart.solar.deffered.SolarBlockstateProvider;
import mart.solar.deffered.SolarItemModelProvider;
import mart.solar.deffered.SolarLangProvider;
import mart.solar.util.RgbColorUtil;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.GatherDataEvent;

public class ModSetup {

    public void preInit(FMLCommonSetupEvent event) {
        CapabilityManager.INSTANCE.register(IEnergyCapability.class, new EnergyCapabilityStorage(), EnergyCapability::new);

        RgbColorUtil.init();
        ModRituals.init();
    }

    public void gatherData (GatherDataEvent event) {
        DataGenerator gen = event.getGenerator();
        if (event.includeClient()) {
            gen.addProvider(new SolarBlockstateProvider(gen, event.getExistingFileHelper()));
            gen.addProvider(new SolarItemModelProvider(gen, event.getExistingFileHelper()));
            gen.addProvider(new SolarLangProvider(gen));
        }
        if (event.includeServer()) {
        }
    }

}

