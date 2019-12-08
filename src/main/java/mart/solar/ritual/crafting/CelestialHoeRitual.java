package mart.solar.ritual.crafting;

import mart.solar.energy.EnergyEnum;
import mart.solar.ritual.CraftingRitual;
import mart.solar.ritual.Ritual;
import net.minecraft.item.Items;

public class CelestialHoeRitual extends CraftingRitual {

    public CelestialHoeRitual() {
        super("crafting_celestial_hoe", Items.DIAMOND_HOE);
        setRitualItems(Items.WOODEN_HOE, Items.DIAMOND);
        addRitualEnergy(EnergyEnum.SOLAR, 2);
        addRitualEnergy(EnergyEnum.LUNAR, 2);
    }

    @Override
    public Ritual returnCopy() {
        return new CelestialHoeRitual();
    }
}
