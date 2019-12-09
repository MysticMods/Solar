package mart.solar.setup;

import mart.solar.Solar;
import mart.solar.block.AltarBaseBlock;
import mart.solar.block.AltarBlock;
import mart.solar.block.SunburntGlassBlock;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraftforge.common.ToolType;
import net.minecraftforge.fml.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {

    public static final Supplier<Item.Properties> SIG = () -> new Item.Properties().group(Solar.GROUP);

    public static RegistryObject<AltarBaseBlock> ALTAR_BASE = Solar.REGISTRY.registerBlock("altar_base", Solar.REGISTRY.block(AltarBaseBlock::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<AltarBlock> ALTAR = Solar.REGISTRY.registerBlock("altar", Solar.REGISTRY.block(AltarBlock::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);

    //Dec blocks
    public static RegistryObject<Block> SUNBURNT_STONE = Solar.REGISTRY.registerBlock("sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> PAVED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("paved_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> ENGRAVED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("engraved_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> ARCHED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("arched_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> CULLED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("culled_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> SLICED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("sliced_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> CROSSED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("crossed_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> TANGLED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("tangled_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> EMERALD_ENDORSED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("emerald_endorsed_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    public static RegistryObject<Block> DIAMOND_ENDORSED_SUNBURNT_STONE = Solar.REGISTRY.registerBlock("diamond_endorsed_sunburnt_stone", Solar.REGISTRY.block(Block::new, () -> Block.Properties.create(Material.ROCK).hardnessAndResistance(2.0F, 6.0F).harvestTool(ToolType.PICKAXE)), SIG);
    
    public static RegistryObject<SunburntGlassBlock> SUNBURNT_GLASS = Solar.REGISTRY.registerBlock("sunburnt_glass", Solar.REGISTRY.block(SunburntGlassBlock::new, () -> Block.Properties.create(Material.GLASS).hardnessAndResistance(0.3F).sound(SoundType.GLASS)), SIG);

    public static void load() {
    }
}
