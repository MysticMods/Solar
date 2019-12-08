package mart.solar.setup;

import epicsquid.mysticallib.util.ListUtil;
import mart.solar.ritual.Ritual;
import mart.solar.ritual.RitualOfAshes;
import mart.solar.ritual.crafting.CelestialHoeRitual;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ModRituals {

    private static Map<String, Ritual> ritualList = new HashMap<>();

    public static void init(){
        //Ritual
        registerRitual(new RitualOfAshes());

        //Spell

        //Crafting
        registerRitual(new CelestialHoeRitual());
    }

    public static void registerRitual(Ritual ritual){
        ritualList.put(ritual.getRitualName(), ritual);
    }

    public static Ritual getRitual(String name){
        Ritual ritual = ritualList.getOrDefault(name, null);
        if(ritual != null){
            return ritual.returnCopy();
        }
        return null;
    }

    public static Ritual getRitual(List<ItemStack> items){
        for(Map.Entry<String, Ritual> entry : ritualList.entrySet()){
            if(ListUtil.matchesIngredients(items, entry.getValue().getRitualItems())){
                return entry.getValue().returnCopy();
            }
        }

        return null;
    }

}
