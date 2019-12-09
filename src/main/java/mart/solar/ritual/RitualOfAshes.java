package mart.solar.ritual;

import mart.solar.energy.EnergyEnum;
import net.minecraft.item.Items;

public class RitualOfAshes extends Ritual{

    public RitualOfAshes() {
        super("ritual_of_ashes");
        setRitualItems(Items.COAL, Items.COAL);
        addRitualEnergy(EnergyEnum.FIRE, 1);
    }

    @Override
    public Ritual returnCopy() {
        return new RitualOfAshes();
    }
}
