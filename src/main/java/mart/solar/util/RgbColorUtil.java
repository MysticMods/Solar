package mart.solar.util;

import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;

import java.util.HashMap;
import java.util.Map;

public class RgbColorUtil {

  private static Map<IEnergyEnum, RgbColor> colorMap = new HashMap<>();

  public static RgbColor FIRE = new RgbColor(255, 103, 16);
  public static RgbColor WATER = new RgbColor(32, 188, 255);
  public static RgbColor EARTH = new RgbColor(52, 255, 36);
  public static RgbColor WIND = new RgbColor(255, 255, 255);
  public static RgbColor TIME = new RgbColor(108, 52, 181);
  public static RgbColor LIFE = new RgbColor(255, 33, 33);
  public static RgbColor SOLAR = new RgbColor(255, 240, 32);
  public static RgbColor LUNAR = new RgbColor(255, 30, 217);


  public static void init(){
    colorMap.put(EnumEnergy.FIRE, FIRE);
    colorMap.put(EnumEnergy.WATER, WATER);
    colorMap.put(EnumEnergy.EARTH, EARTH);
    colorMap.put(EnumEnergy.WIND, WIND);
    colorMap.put(EnumEnergy.TIME, TIME);
    colorMap.put(EnumEnergy.LIFE, LIFE);
    colorMap.put(EnumEnergy.SOLAR, SOLAR);
    colorMap.put(EnumEnergy.LUNAR, LUNAR);
  }

  public static RgbColor getRuneColor(EnumEnergy type) {
    return colorMap.get(type);
  }

  public static RgbColor getRuneColor(IEnergyEnum type) {
    return colorMap.get(type);
  }
}