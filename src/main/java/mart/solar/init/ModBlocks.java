package mart.solar.init;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.block.BlockBase;
import epicsquid.mysticallib.block.BlockSlabBase;
import epicsquid.mysticallib.block.BlockStairsBase;
import epicsquid.mysticallib.block.BlockWallBase;
import epicsquid.mysticallib.event.RegisterContentEvent;
import epicsquid.mysticalworld.MysticalWorld;
import mart.solar.Solar;
import mart.solar.blocks.*;
import mart.solar.tile.*;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;

public class ModBlocks {

  public static Block menhir, altar, altar_base, ancient_lens, sun_mine, charging_pedestal;

  public static Block solar_infused_stone, solar_infused_stone_stairs, solar_infused_stone_slab, solar_infused_stone_wall;

  // All blocks

  /**
   * Register all blocks
   */
  public static void registerBlocks(@Nonnull RegisterContentEvent event) {
    event.addBlock(menhir = new BlockMenhir(Material.ROCK, SoundType.STONE, 1.4f, "menhir", TileEntityMenhir.class).setCreativeTab(Solar.tab).setLightOpacity(2));
    event.addBlock(altar = new BlockAltar(Material.ROCK, SoundType.STONE, 1.4f, "altar", TileEntityAltar.class).setCreativeTab(Solar.tab).setLightOpacity(2));
    event.addBlock(altar_base = new BlockAltarBase(Material.ROCK, SoundType.STONE, 1.4f, "altar_base", TileEntityAltarBase.class).setCreativeTab(Solar.tab).setLightOpacity(2));
    event.addBlock(ancient_lens = new BlockAncientLens(Material.GLASS, SoundType.GLASS, 1.4f, "ancient_lens", TileEntityAncientLens.class).setCreativeTab(Solar.tab).setLightOpacity(0));
    event.addBlock(sun_mine = new BlockSunMine(Material.ROCK, SoundType.STONE, 1.4f, "sun_mine", TileEntitySunMine.class).setCreativeTab(Solar.tab).setLightOpacity(0));
    event.addBlock(charging_pedestal = new BlockChargingPedestal(Material.ROCK, SoundType.STONE, 1.4f, "charging_pedestal", TileEntityChargingPedestal.class).setCreativeTab(Solar.tab).setLightOpacity(2));

    //solar infused4
    BlockSlabBase slab_temp, double_slab_temp;
    event.addBlock(solar_infused_stone = new BlockBase(Material.ROCK, SoundType.STONE, 1.4f, "solar_infused_stone").setCreativeTab(MysticalWorld.tab));
    event.addBlock(
            solar_infused_stone_stairs = new BlockStairsBase(solar_infused_stone.getDefaultState(), SoundType.STONE, 2.0f, "solar_infused_stone_stairs").setModelCustom(true)
                    .setCreativeTab(MysticalWorld.tab));
    double_slab_temp = new BlockSlabBase(Material.ROCK, SoundType.STONE, 2.0f, "solar_infused_stone_double_slab", solar_infused_stone.getDefaultState(), true, null)
            .setModelCustom(true);
    slab_temp = new BlockSlabBase(Material.ROCK, SoundType.STONE, 2.0f, "solar_infused_stone_slab", solar_infused_stone.getDefaultState(), false, double_slab_temp)
            .setModelCustom(true);
    double_slab_temp.setSlab(slab_temp);
    event.addBlock(solar_infused_stone_slab = slab_temp.setCreativeTab(MysticalWorld.tab));
    event.addBlock(double_slab_temp);
    event.addBlock(solar_infused_stone_wall = new BlockWallBase(solar_infused_stone, SoundType.STONE, 2.0f, "solar_infused_stone_wall").setModelCustom(true)
            .setCreativeTab(MysticalWorld.tab));
  }
}
