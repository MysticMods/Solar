package mart.solar;

import mart.solar.capability.energycapability.EnergyCapability;
import mart.solar.capability.energycapability.EnergyCapabilityStorage;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.event.SolarEvents;
import mart.solar.proxy.CommonProxy;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Solar.MODID, version = Solar.VERSION, name = Solar.NAME, dependencies = "required-after:baubles@[1.5.2,);after:thaumcraft;")
public class Solar {
  public static final String MODID = "solar";
  public static final String DOMAIN = "solar";
  public static final String NAME = "Solar";
  public static final String VERSION = "@VERSION@";

  public static ModContainer CONTAINER = null;

  @SidedProxy(clientSide = "mart.solar.proxy.ClientProxy", serverSide = "mart.solar.proxy.CommonProxy")
  public static CommonProxy proxy;

  @Instance(MODID) public static Solar instance;

  public static CreativeTabs tab = new CreativeTabs("solar") {
    @Override
    public String getTabLabel() {
      return "solar";
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getTabIconItem() {
      return new ItemStack(Items.APPLE, 1);
    }
  };

  @EventHandler
  public void preInit(FMLPreInitializationEvent event) {
    CONTAINER = Loader.instance().activeModContainer();

    CapabilityManager.INSTANCE.register(IEnergyCapability.class, new EnergyCapabilityStorage(), EnergyCapability::new);

    MinecraftForge.EVENT_BUS.register(new RegistryManager());
    MinecraftForge.EVENT_BUS.register(new SolarEvents());

    proxy.preInit(event);
  }

  public static Solar getInstance() {
    return instance;
  }

  @EventHandler
  public void init(FMLInitializationEvent event) {
    proxy.init(event);
  }

  @EventHandler
  public void postInit(FMLPostInitializationEvent event) {
    proxy.postInit(event);
  }
}
