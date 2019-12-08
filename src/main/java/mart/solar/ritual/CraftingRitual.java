package mart.solar.ritual;

import net.minecraft.item.Item;

public class CraftingRitual extends Ritual {

    private final Item output;

    public CraftingRitual(String ritualName, Item output) {
        super(ritualName);
        this.output = output;
    }

    @Override
    public Ritual returnCopy() {
        return null;
    }

    public Item getOutput() {
        return output;
    }
}
