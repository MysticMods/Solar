package mart.solar.tile;

import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.util.Util;
import mart.solar.init.ModBlocks;
import mart.solar.util.SolarUtil;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ITickable;

public class TileEntityAncientLens extends TileBase implements ITickable{

  private int burnTicks = 0;

  @Override
  public void update() {
    if(!SolarUtil.isDay(world)){
      return;
    }
    if(world.getBlockState(pos.down()).getBlock() == Blocks.STONE){
      burnTicks++;
      if(world.isRemote){
        double d3 = (double)pos.getX() + Util.rand.nextDouble();
        double d8 = (double)pos.getY() + Util.rand.nextDouble();
        double d13 = (double)pos.getZ() + Util.rand.nextDouble();
        world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, d3, d8, d13, 0.0D, 0.0D, 0.0D);
      }
    }
    else{
      burnTicks = 0;
    }

    if(burnTicks >= 100){
      world.setBlockState(pos.down(), ModBlocks.solar_infused_stone.getDefaultState(), 2);
    }
  }
}
