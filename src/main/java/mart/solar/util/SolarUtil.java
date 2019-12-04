package mart.solar.util;

import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.init.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolarUtil {

  public static Map<Block, IEnergyEnum> elementBlocks = new HashMap<>();
  public static Map<IEnergyEnum, Item> elementRunes = new HashMap<>();
  public static List<Item> runes = new ArrayList<>();

  public static void init(){
    //Element blocks
    elementBlocks.put(Blocks.STONE,       EnumEnergy.EARTH);
    elementBlocks.put(Blocks.LAVA,        EnumEnergy.FIRE);
    elementBlocks.put(Blocks.WATER,       EnumEnergy.WATER);
    elementBlocks.put(Blocks.TALL_GRASS,   EnumEnergy.WIND);
    elementBlocks.put(Blocks.WHEAT,       EnumEnergy.LIFE);
    elementBlocks.put(Blocks.DIAMOND_ORE, EnumEnergy.TIME);
    elementBlocks.put(Blocks.COAL_ORE,    EnumEnergy.TIME);
    elementBlocks.put(Blocks.COAL_BLOCK,  EnumEnergy.TIME);
    elementBlocks.put(Blocks.GOLD_ORE,    EnumEnergy.SOLAR);

//    for(ItemStack stack : OreDictionary.getOres("oreSilver")){
//      if(stack.getItem() instanceof ItemBlock){
//        ItemBlock blockItem = (ItemBlock) stack.getItem();
//        elementBlocks.put(blockItem.getBlock(), EnumEnergy.LUNAR);
//      }
//    }

    //Element Runes
    elementRunes.put(EnumEnergy.EARTH, ModItems.earth_rune);
    elementRunes.put(EnumEnergy.FIRE, ModItems.fire_rune);
    elementRunes.put(EnumEnergy.WATER, ModItems.water_rune);
    elementRunes.put(EnumEnergy.WIND, ModItems.wind_rune);
    elementRunes.put(EnumEnergy.LIFE, ModItems.life_rune);
    elementRunes.put(EnumEnergy.TIME, ModItems.time_rune);
    elementRunes.put(EnumEnergy.SOLAR, ModItems.sun_rune);
    elementRunes.put(EnumEnergy.LUNAR, ModItems.moon_rune);
    
    //runes
    runes.add(ModItems.earth_rune);
    runes.add(ModItems.fire_rune);
    runes.add(ModItems.water_rune);
    runes.add(ModItems.wind_rune);
    runes.add(ModItems.life_rune);
    runes.add(ModItems.time_rune);
    runes.add(ModItems.sun_rune);
    runes.add(ModItems.moon_rune);
  }

  @Nonnull
  public static IEnergyEnum getTypeFromRune(Item item) {
    for(Map.Entry<IEnergyEnum, Item> entry : elementRunes.entrySet()){
      if(entry.getValue() == item){
        return entry.getKey();
      }
    }
    return EnumEnergy.SOLAR;
  }

  public static boolean addStackToInventory(int slot, ItemStackHandler inventory, ItemStack heldItem, PlayerEntity player, Hand hand){
    if (inventory.getStackInSlot(slot).isEmpty()) {
      ItemStack insertStack = heldItem.copy();
      insertStack.setCount(1);
      ItemStack attemptedInsert = inventory.insertItem(slot, insertStack, true);
      if (attemptedInsert.isEmpty()) {
        inventory.insertItem(slot, insertStack, false);
        player.getHeldItem(hand).shrink(1);
        if (player.getHeldItem(hand).getCount() == 0) {
          player.setHeldItem(hand, ItemStack.EMPTY);
        }
        return true;
      }
    }
    return false;
  }

  public static boolean isDay(World world) {
    long dayTime = world.getGameTime() % 24000;

    return dayTime < 12566 || dayTime > 23450;
  }

//  public static void removeEnergyFromBaubleItem(PlayerEntity player, int slot, float amount){
//    ItemStack stack = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
//
//    IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
//    if(capability == null){
//      return;
//    }
//
//    if(capability.getEnergyAmount() <= 0){
//      return;
//    }
//
//    capability.removeEnergy(amount);
//    PacketHandler.INSTANCE.sendToAll(new EnergyCapabilityMessage(true, slot, stack, capability.getData()));
//  }
//
//  public static void removeEnergyFromBaubleItem(ItemStack stack, float amount){
//    IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
//    if(capability == null){
//      return;
//    }
//
//    if(capability.getEnergyAmount() <= 0){
//      return;
//    }
//
//    capability.removeEnergy(amount);
//    PacketHandler.INSTANCE.sendToAll(new EnergyCapabilityMessage(true, -1, stack, capability.getData()));
//  }
//
//  public static void removeEnergyFromHeldItem(ItemStack stack, float amount, EnumHand hand){
//    IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
//    if(capability == null){
//      return;
//    }
//
//    if(capability.getEnergyAmount() <= 0){
//      return;
//    }
//
//    capability.removeEnergy(amount);
//    if(hand == EnumHand.MAIN_HAND){
//      PacketHandler.INSTANCE.sendToAll(new EnergyCapabilityMessage(false, -1, stack, capability.getData()));
//    }
//    else{
//      PacketHandler.INSTANCE.sendToAll(new EnergyCapabilityMessage(false, -2, stack, capability.getData()));
//    }
//
//  }

}
