package mart.solar.crafting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import mart.solar.enums.IEnergyEnum;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import mart.solar.util.TimeEnum;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AltarCraftingRecipe {

  private ItemStack triggerItem;
  private ItemStack result;
  private List<ItemStack> craftingItems = new ArrayList<>();
  private TimeEnum time;
  private Map<Item, RgbColor> colorMap = new HashMap<>();
  private Map<IEnergyEnum, Integer> energyMap = new HashMap<>();

  public AltarCraftingRecipe(@Nonnull ItemStack triggerItem, @Nonnull ItemStack result, @Nonnull TimeEnum time, ItemStack... craftingItems){
    this.triggerItem = triggerItem;
    this.result = result;
    this.time = time;
    Collections.addAll(this.craftingItems, craftingItems);
  }

  public AltarCraftingRecipe addColor(Item item, RgbColor color){
    this.colorMap.put(item, color);
    return this;
  }

  public AltarCraftingRecipe addEnergy(IEnergyEnum energyType, int amount){
    this.energyMap.put(energyType, amount);
    return this;
  }

  public boolean canRun(World world) {
    return time == TimeEnum.BOTH || time == TimeEnum.DAY && SolarUtil.isDay(world) || time == TimeEnum.NIGHT && !SolarUtil.isDay(world);
  }

  public ItemStack getTriggerItem() {
    return triggerItem;
  }

  public ItemStack getResult() {
    return result;
  }

  public List<ItemStack> getCraftingItems() {
    return craftingItems;
  }

  public Map<Item, RgbColor> getColorMap() {
    return colorMap;
  }

  public RgbColor getColor(Item item) {
    if (this.getColorMap().get(item) != null) {
      return this.getColorMap().get(item);
    } else {
      return RgbColorUtil.WIND;
    }
  }

  public Map<IEnergyEnum, Integer> getEnergyMap() {
    return energyMap;
  }
}
