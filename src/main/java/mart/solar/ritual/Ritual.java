package mart.solar.ritual;

import net.minecraft.item.Item;
import net.minecraft.item.crafting.Ingredient;

import java.util.ArrayList;
import java.util.List;

public abstract class Ritual {

    private final String ritualName;
    private List<Ingredient> ritualItems;

    public Ritual(String ritualName){
        this.ritualName = ritualName;
        ritualItems = new ArrayList<>();
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

    public String getRitualName() {
        return ritualName;
    }
}
