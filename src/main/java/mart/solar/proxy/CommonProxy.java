package mart.solar.proxy;

import mart.solar.crafting.AltarCraftingRecipeRegistry;
import mart.solar.enums.EnergyRegistry;
import mart.solar.init.ModItems;
import mart.solar.ritual.RitualRegistry;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CommonProxy {
  public void preInit(FMLPreInitializationEvent event) {
    ModItems.registerOredict();
  }

  public void init(FMLInitializationEvent event) {
    SolarUtil.init();
    RgbColorUtil.init();
    AltarCraftingRecipeRegistry.init();

    RitualRegistry.init();
    EnergyRegistry.init();
  }

  public void postInit(FMLPostInitializationEvent event) {
  }
}
