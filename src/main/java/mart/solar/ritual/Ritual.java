package mart.solar.ritual;

import mart.solar.energy.IEnergyEnum;
import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class Ritual {

    private final String ritualName;
    private List<Ingredient> ritualItems;
    private Map<IEnergyEnum, Integer> ritualEnergy;

    public Ritual(String ritualName){
        this.ritualName = ritualName;
        ritualItems = new ArrayList<>();
        ritualEnergy = new HashMap<>();
    }

    public abstract Ritual returnCopy();

    public List<Ingredient> getRitualItems() {
        return ritualItems;
    }

    public void setRitualItems(List<Ingredient> ritualItems) {
        this.ritualItems = ritualItems;
    }

    public void setRitualItems(Item... items){
        for(Item i : items){
            ritualItems.add(Ingredient.fromItems(i));
        }
    }

    public void addRitualEnergy(IEnergyEnum energy, int amount){
        this.ritualEnergy.put(energy, amount);
    }

    public Map<IEnergyEnum, Integer> getRitualEnergy() {
        return ritualEnergy;
    }

    public void decreaseEnergy(IEnergyEnum energyEnum){
        if(ritualEnergy.containsKey(energyEnum)){
            if(ritualEnergy.get(energyEnum) > 0){
                ritualEnergy.put(energyEnum, ritualEnergy.get(energyEnum) - 1);
            }
        }
    }

    public boolean hasAllEnergy() {
        AtomicBoolean hasAll = new AtomicBoolean(true);
        ritualEnergy.forEach((energyEnum, integer) -> {
            if(integer > 0){
                hasAll.set(false);
            }
        });
        return hasAll.get();
    }

    public List<IEnergyEnum> getEnergyList(){
        List<IEnergyEnum> energies = new ArrayList<>();
        for(Map.Entry<IEnergyEnum, Integer> entry : ritualEnergy.entrySet()){
            energies.add(entry.getKey());
        }
        return energies;
    }

    public String getRitualName() {
        return ritualName;
    }

}
