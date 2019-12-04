package mart.solar.util;

import mart.solar.Solar;
import mart.solar.energy.IEnergyEnum;
import net.minecraft.block.Block;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SolarUtil {

    public static Map<Block, IEnergyEnum> getElementalAllBlocksAsMap(){
        Map<Block, IEnergyEnum> elementalBlocks = new HashMap<>();
        for(IEnergyEnum energy : Solar.ENERGY.getEnergies().values()){
            for(Block block : BlockTags.getCollection().get(new ResourceLocation("solar", "energy/" + energy.getName())).getAllElements()){
                elementalBlocks.put(block, energy);
            }
        }
        return elementalBlocks;
    }

    public static List<Block> getElementalAllBlocks(){
        List<Block> elementalBlocks = new ArrayList<>();
        for(IEnergyEnum energy : Solar.ENERGY.getEnergies().values()){
            elementalBlocks.addAll(BlockTags.getCollection().get(new ResourceLocation("solar", "energy/" + energy.getName())).getAllElements());
        }
        return elementalBlocks;
    }






    // public static Map<IEnergyEnum, Item> elementRunes = new HashMap<>();
    //public static List<Item> runes = new ArrayList<>();

    public static void init(){

//
//    for(ItemStack stack : OreDictionary.getOres("oreSilver")){
//      if(stack.getItem() instanceof ItemBlock){
//        ItemBlock blockItem = (ItemBlock) stack.getItem();
//        elementBlocks.put(blockItem.getBlock(), EnumEnergy.LUNAR);
//      }
//    }
//
//    //Element Runes
//    elementRunes.put(EnumEnergy.EARTH, ModItems.earth_rune);
//    elementRunes.put(EnumEnergy.FIRE, ModItems.fire_rune);
//    elementRunes.put(EnumEnergy.WATER, ModItems.water_rune);
//    elementRunes.put(EnumEnergy.WIND, ModItems.wind_rune);
//    elementRunes.put(EnumEnergy.LIFE, ModItems.life_rune);
//    elementRunes.put(EnumEnergy.TIME, ModItems.time_rune);
//    elementRunes.put(EnumEnergy.SOLAR, ModItems.sun_rune);
//    elementRunes.put(EnumEnergy.LUNAR, ModItems.moon_rune);
//
//    //runes
//    runes.add(ModItems.earth_rune);
//    runes.add(ModItems.fire_rune);
//    runes.add(ModItems.water_rune);
//    runes.add(ModItems.wind_rune);
//    runes.add(ModItems.life_rune);
//    runes.add(ModItems.time_rune);
//    runes.add(ModItems.sun_rune);
//    runes.add(ModItems.moon_rune);
    }

//  @Nonnull
//  public static IEnergyEnum getTypeFromRune(Item item) {
//    for(Map.Entry<IEnergyEnum, Item> entry : elementRunes.entrySet()){
//      if(entry.getValue() == item){
//        return entry.getKey();
//      }
//    }
//    return EnumEnergy.SOLAR;
//  }


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
