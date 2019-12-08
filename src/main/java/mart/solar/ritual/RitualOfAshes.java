package mart.solar.ritual;

import net.minecraft.item.Items;

public class RitualOfAshes extends Ritual{

    public RitualOfAshes() {
        super("ritual_of_ashes");
        setRitualItems(Items.COAL, Items.FLINT_AND_STEEL);
    }

    @Override
    public Ritual returnCopy() {
        return new RitualOfAshes();
    }
}
