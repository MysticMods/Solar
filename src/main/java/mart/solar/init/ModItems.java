package mart.solar.init;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.event.RegisterContentEvent;
import epicsquid.mysticallib.item.ItemBase;
import mart.solar.Solar;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.items.*;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModItems {

  // All mod items
  public static Item blank_rune, fire_rune, water_rune, earth_rune, wind_rune, time_rune, sun_rune, moon_rune, life_rune,
          infused_gold, infused_silver, study_tools, solar_ring, lunar_ring, sun_mask, celestial_hoe, pendant;

  //Armor
  public static Item silver_chestplate, silver_boots, silver_leggings, silver_helmet;

  @GameRegistry.ObjectHolder("patchouli:guide_book")
  public static final Item guide_book = null;

  /**
   * Register all items
   */
  public static void registerItems(@Nonnull RegisterContentEvent event) {
    event.addItem(blank_rune = new ItemBase("blank_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(fire_rune = new ItemBase("fire_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(water_rune = new ItemBase("water_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(earth_rune = new ItemBase("earth_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(wind_rune = new ItemBase("wind_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(time_rune = new ItemBase("time_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(sun_rune = new ItemBase("sun_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(moon_rune = new ItemBase("moon_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(life_rune = new ItemBase("life_rune").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(infused_gold = new ItemBase("infused_gold").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(infused_silver = new ItemBase("infused_silver").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(study_tools = new ItemStudyTools("study_tools").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(solar_ring = new ItemSolarRing("solar_ring").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(lunar_ring = new ItemLunarRing("lunar_ring").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(sun_mask = new ItemBase("sun_mask").setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(celestial_hoe = new ItemCelestialHoe(Item.ToolMaterial.GOLD, "celestial_hoe").setModelCustom(true).setCreativeTab(Solar.tab));

    event.addItem(silver_boots = new ItemArmorBase("silver_boots", ItemArmorBase.SILVER, 4, EntityEquipmentSlot.FEET).setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(silver_chestplate = new ItemArmorBase("silver_chestplate", ItemArmorBase.SILVER, 4, EntityEquipmentSlot.CHEST).setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(silver_leggings = new ItemArmorBase("silver_leggings", ItemArmorBase.SILVER, 4, EntityEquipmentSlot.LEGS).setModelCustom(true).setCreativeTab(Solar.tab));
    event.addItem(silver_helmet = new ItemArmorBase("silver_helmet", ItemArmorBase.SILVER, 4, EntityEquipmentSlot.HEAD).setModelCustom(true).setCreativeTab(Solar.tab));

    event.addItem(pendant = new ItemPendant("pendant").setModelCustom(true).setCreativeTab(Solar.tab));
    for(IEnergyEnum energyEnum : EnumEnergy.values()){
      ItemPendant item = (ItemPendant) event.addItem(new ItemPendant("pendant_" + energyEnum.getName()).setModelCustom(true));
      ItemPendant.addEnergyType(energyEnum, item);
    }

  }

  /**
   * Register item oredicts here
   */
  public static void registerOredict() {

  }
}
