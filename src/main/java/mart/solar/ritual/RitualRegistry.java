package mart.solar.ritual;

import mart.solar.enums.IEnergyEnum;

import java.util.HashMap;
import java.util.Map;

public class RitualRegistry {

  private static Map<String, RitualBase> ritualRegistry = new HashMap<>();

  public static RitualBase ritual_test;

  public static RitualBase getRitual(Map<IEnergyEnum, Integer> elements) {
    for (int i = 0; i < ritualRegistry.size(); i++) {
      RitualBase ritual = ritualRegistry.values().toArray(new RitualBase[ritualRegistry.size()])[i];
      if(ritual.isRitual(elements)){
        return ritual;
      }
    }
    return null;
  }

  public static void init() {
    ritualRegistry.put("ritual_test", ritual_test = new RitualMiner());
  }

}