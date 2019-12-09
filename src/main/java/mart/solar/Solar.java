package mart.solar;

import epicsquid.mysticallib.registry.ModRegistry;
import mart.solar.setup.ModBlocks;
import mart.solar.setup.ModEnergies;
import mart.solar.setup.ModItems;
import mart.solar.setup.ModSetup;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;


@Mod("solar")
public class Solar {
  public static final String MODID = "solar";

  public static ItemGroup GROUP = new ItemGroup(Solar.MODID) {
    @Override
    public ItemStack createIcon() {
      return new ItemStack(Item.getItemFromBlock(ModBlocks.ALTAR.get()));
    }
  };

  public static ModRegistry REGISTRY = new ModRegistry(MODID);
  private static ModSetup setup = new ModSetup();
  public static ModEnergies ENERGY = new ModEnergies();

  public Solar(){
    IEventBus modBus = FMLJavaModLoadingContext.get().getModEventBus();
    modBus.addListener(setup::preInit);
    modBus.addListener(setup::gatherData);


    ModBlocks.load();
    ModItems.load();

    REGISTRY.registerEventBus(modBus);
  }



}
