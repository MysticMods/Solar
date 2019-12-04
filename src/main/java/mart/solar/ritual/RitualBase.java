package mart.solar.ritual;

import java.util.HashMap;
import java.util.Map;

import mart.solar.enums.IEnergyEnum;
import mart.solar.tile.TileEntityAltar;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class RitualBase {

  private Map<IEnergyEnum, Integer> baseElements = new HashMap<>();

  public RitualBase(){

  }

  public void addBaseElement(IEnergyEnum type, int amount){
    this.baseElements.put(type, amount);
  }

  public abstract void fire(World world, BlockPos pos, TileEntityAltar altar);

  public abstract boolean canFire(World world, BlockPos pos, TileEntityAltar altar);

  public Map<IEnergyEnum, Integer> getBaseElements() {
    return baseElements;
  }

  public boolean isRitual(Map<IEnergyEnum, Integer> elements){
    if(elements.size() != this.baseElements.size()){
      return false;
    }
    for(Map.Entry<IEnergyEnum, Integer> entry : this.baseElements.entrySet()){
      if(entry.getValue() != elements.get(entry.getKey())){
        return false;
      }
    }
    return true;
  }
}
