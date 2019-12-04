package mart.solar.enums;

import java.util.HashMap;
import java.util.Map;

public class EnergyRegistry {

    private static Map<String, IEnergyEnum> energyRegistry = new HashMap<>();

    public static IEnergyEnum getEnergy(String name) {
        return energyRegistry.get(name);
    }

    public static void init() {
        registerEnergy("solar", EnumEnergy.SOLAR);
        registerEnergy("lunar", EnumEnergy.LUNAR);
    }

    public static void registerEnergy(String name, IEnergyEnum energyEnum){
        if(!energyRegistry.containsKey(name)){
            energyRegistry.put(name, energyEnum);
        }
    }

}
