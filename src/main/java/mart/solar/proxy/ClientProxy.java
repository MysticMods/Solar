package mart.solar.proxy;

import mart.solar.tile.*;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ClientProxy extends CommonProxy {
  @Override
  public void preInit(FMLPreInitializationEvent event) {
    super.preInit(event);
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltarBase.class, new TileEntityAltarBaseRenderer());
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
    ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChargingPedestal.class, new TileEntityChargingPedestalRenderer());
  }

  @Override
  public void init(FMLInitializationEvent event) {
    super.init(event);
  }

  @Override
  public void postInit(FMLPostInitializationEvent event) {
    super.postInit(event);
  }
}
