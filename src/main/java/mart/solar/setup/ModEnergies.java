package mart.solar.setup;

import mart.solar.energy.EnergyEnum;
import mart.solar.energy.IEnergyEnum;

import java.util.HashMap;
import java.util.Map;

public class ModEnergies {

    private Map<String, IEnergyEnum> energies = new HashMap<>();

    public ModEnergies(){
        register(EnergyEnum.SOLAR);
        register(EnergyEnum.LUNAR);
        register(EnergyEnum.FIRE);
        register(EnergyEnum.WATER);
        register(EnergyEnum.EARTH);
        register(EnergyEnum.WIND);
        register(EnergyEnum.TIME);
        register(EnergyEnum.LIFE);
        register(EnergyEnum.NONE);
    }

    public void register(IEnergyEnum energyEnum){
        energies.put(energyEnum.getName(), energyEnum);
    }

    public IEnergyEnum getEnergyType(String s){
        return energies.getOrDefault(s, EnergyEnum.NONE);
    }

}
