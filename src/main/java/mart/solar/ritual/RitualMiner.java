package mart.solar.ritual;

import mart.solar.enums.EnumEnergy;
import mart.solar.init.ModItems;
import mart.solar.tile.TileEntityAltar;
import mart.solar.util.SolarUtil;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RitualMiner extends RitualBase {

  public RitualMiner(){
    addBaseElement(EnumEnergy.EARTH, 2);
    addBaseElement(EnumEnergy.SOLAR, 2);
  }

  @Override
  public void fire(World world, BlockPos pos, TileEntityAltar altar) {
    System.out.println("Fires");
  }

  @Override
  public boolean canFire(World world, BlockPos pos, TileEntityAltar altar) {
    return SolarUtil.isDay(world) && altar.inventory.getStackInSlot(0).getItem() == ModItems.pendant;
  }
}
