package mart.solar.setup;

import mart.solar.Solar;
import net.minecraft.item.Item;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModItems {
    public static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(Solar.GROUP);

    public static RegistryObject<Item> SOLAR_RING = Solar.REGISTRY.registerItem("solar_ring", Solar.REGISTRY.item(Item::new, SIG));
    public static RegistryObject<Item> LUNAR_RING = Solar.REGISTRY.registerItem("lunar_ring", Solar.REGISTRY.item(Item::new, SIG));

    public static void load() {}
}
