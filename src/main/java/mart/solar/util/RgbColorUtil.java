package mart.solar.util;

import mart.solar.energy.EnergyEnum;
import mart.solar.energy.IEnergyEnum;

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
    colorMap.put(EnergyEnum.FIRE, FIRE);
    colorMap.put(EnergyEnum.WATER, WATER);
    colorMap.put(EnergyEnum.EARTH, EARTH);
    colorMap.put(EnergyEnum.WIND, WIND);
    colorMap.put(EnergyEnum.TIME, TIME);
    colorMap.put(EnergyEnum.LIFE, LIFE);
    colorMap.put(EnergyEnum.SOLAR, SOLAR);
    colorMap.put(EnergyEnum.LUNAR, LUNAR);
  }

  public static RgbColor getEnergyColor(EnergyEnum type) {
    return colorMap.get(type);
  }

  public static RgbColor getEnergyColor(IEnergyEnum type) {
    return colorMap.get(type);
  }
}