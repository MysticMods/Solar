package mart.solar.setup;

import mart.solar.Solar;
import mart.solar.block.AltarBaseBlock;
import mart.solar.block.AltarBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    private static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(Solar.GROUP);

    public static RegistryObject<AltarBaseBlock> ALTAR_BASE = Solar.REGISTRY.registerBlock("altar_base", Solar.REGISTRY.block(AltarBaseBlock::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<AltarBlock> ALTAR = Solar.REGISTRY.registerBlock("altar", Solar.REGISTRY.block(AltarBlock::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.5F).harvestTool(ToolType.PICKAXE)), SIG);

    public static void load() {
    }
}
