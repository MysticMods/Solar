package mart.solar.ritual;

import mart.solar.energy.EnergyEnum;
import net.minecraft.item.Items;

public class RitualOfAshes extends Ritual{

    public RitualOfAshes() {
        super("ritual_of_ashes");
        setRitualItems(Items.COAL, Items.FLINT_AND_STEEL);
        addRitualEnergy(EnergyEnum.FIRE, 2);
        addRitualEnergy(EnergyEnum.WATER, 2);
    }

    @Override
    public Ritual returnCopy() {
        return new RitualOfAshes();
    }
}
