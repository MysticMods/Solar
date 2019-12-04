package mart.solar.blocks;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.block.BlockTEBase;
import mart.solar.tile.TileEntityAncientLens;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class BlockAncientLens extends BlockTEBase {

  private AxisAlignedBB bounds = new AxisAlignedBB(0, 0.875, 0, 1, 1, 1);

  public BlockAncientLens(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nonnull Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    setOpacity(false);
    setLayer(BlockRenderLayer.CUTOUT_MIPPED);
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return false;
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return false;
  }

  @Nonnull
  @Override
  public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    return bounds;
  }

}
