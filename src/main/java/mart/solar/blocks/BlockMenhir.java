package mart.solar.blocks;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.annotation.Nonnull;

import com.google.common.collect.Lists;

import epicsquid.mysticallib.block.BlockBase;
import epicsquid.mysticallib.block.BlockTEBase;
import mart.solar.blocks.enums.MenhirPart;
import mart.solar.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockMenhir extends BlockTEBase {

  private static final IProperty<MenhirPart> propertyMenhir = PropertyEnum.create("part", MenhirPart.class);

  private static final AxisAlignedBB[] axisArray = new AxisAlignedBB[]{
      new AxisAlignedBB(0 + 0.125, 0, 0 + 0.125, 1 - 0.125, 3, 1 - 0.125),
      new AxisAlignedBB(0 + 0.125, -1, 0 + 0.125, 1 - 0.125, 2, 1 - 0.125),
      new AxisAlignedBB(0 + 0.125, -2, 0 + 0.125, 1 - 0.125, 1, 1 - 0.125)
  };

  public BlockMenhir(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nonnull Class<? extends TileEntity> teClass) {
    super(mat, type, hardness, name, teClass);
    setDefaultState(blockState.getBaseState().withProperty(propertyMenhir, MenhirPart.BASE));
  }

  @Override
  public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX,
      float hitY, float hitZ) {
    switch (state.getValue(propertyMenhir).getMetadata()){
    case 0:
      return super.onBlockActivated(worldIn, pos, state, playerIn, hand, facing, hitX, hitY, hitZ);
    case 1:
      return onBlockActivated(worldIn, pos.down(), worldIn.getBlockState(pos.down()), playerIn, hand, facing, hitX, hitY, hitZ);
    case 2:
      return onBlockActivated(worldIn, pos.down(2), worldIn.getBlockState(pos.down(2)), playerIn, hand, facing, hitX, hitY, hitZ);
      default:
        return false;
    }
  }

  @Nonnull
  @Override
  public AxisAlignedBB getBoundingBox(@Nonnull IBlockState state, @Nonnull IBlockAccess world, @Nonnull BlockPos pos) {
    switch (state.getValue(propertyMenhir).getMetadata()){
    case 0:
      return axisArray[0];
    case 1:
      return axisArray[1];
    case 2:
      return axisArray[2];
    }
    return super.getBoundingBox(state, world, pos);
  }

  @Override
  public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
    if(state.getValue(propertyMenhir).getMetadata() != 0){
      return;
    }
    worldIn.setBlockState(pos, getStateFromMeta(0));
    worldIn.setBlockState(pos.up(1), getStateFromMeta(1));
    worldIn.setBlockState(pos.up(2), getStateFromMeta(2));

    super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
  }

  @Override
  public boolean canPlaceBlockAt(World world, BlockPos pos) {
    return world.getBlockState(pos.up(1)).getBlock() == Blocks.AIR && world.getBlockState(pos.up(2)).getBlock() == Blocks.AIR;
  }

  @Override
  public Item getItemDropped(IBlockState state, Random rand, int fortune) {
    return Item.getItemFromBlock(ModBlocks.menhir);
  }

  @Override
  public int damageDropped(IBlockState state) {
    return 0;
  }

  @Override
  public boolean isOpaqueCube(@Nonnull IBlockState state) {
    return false;
  }

  @Override
  public boolean isFullCube(@Nonnull IBlockState state) {
    return false;
  }

  @Override
  public IBlockState getStateFromMeta(int meta) {
    return getDefaultState().withProperty(propertyMenhir, MenhirPart.byMetadata(meta));
  }

  @Override
  public int getMetaFromState(IBlockState state) {
    return state.getValue(propertyMenhir).getMetadata();
  }

  @Override
  protected BlockStateContainer createBlockState() {
    return new BlockStateContainer(this, propertyMenhir);
  }

  @Override
  public void neighborChanged(IBlockState state, World world, BlockPos pos, Block block, BlockPos fromPos) {
    super.neighborChanged(state, world, pos, block, fromPos);
    if (!world.isRemote) {
      checkAndDropBlock(world, pos, state);
    }
  }

  @Override
  public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
    return new ItemStack(ModBlocks.menhir);
  }

  @Override
  public int getFlammability(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return 0;
  }

  @Override
  public int getFireSpreadSpeed(IBlockAccess world, BlockPos pos, EnumFacing face) {
    return 0;
  }

  @Override
  public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
    if(!world.isRemote){
      removeMenhirSafely(world, pos, state);
    }
    return true;
  }

  private void removeMenhirSafely(World world, BlockPos pos, IBlockState state) {
    int part = state.getValue(propertyMenhir).getMetadata();
    List<BlockPos> posList = new ArrayList<>();
    Map<BlockPos, IBlockState> oldStates = new HashMap<>();
    for(MenhirPart menhirPart : MenhirPart.values()){
      posList.add(pos.up(menhirPart.getMetadata() - part));
    }
    for(BlockPos blockPos : posList){
      oldStates.put(blockPos, world.getBlockState(blockPos));
      world.setBlockState(blockPos, Blocks.AIR.getDefaultState(), 0);
    }

    Chunk chunk = world.getChunkFromBlockCoords(pos);
    for(BlockPos blockPos : posList){
      world.markAndNotifyBlock(blockPos, chunk, oldStates.get(blockPos), Blocks.AIR.getDefaultState(), 3);
    }
  }

  private void checkAndDropBlock(World world, BlockPos pos, IBlockState state) {
    if(!canBlockStay(world, pos, state)){
      dropBlockAsItem(world, pos, state, 0);
      removeMenhirSafely(world, pos, state);
    }
  }

  private boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
    int part = state.getValue(propertyMenhir).getMetadata();

    if (part == 0) {
      if (!world.isSideSolid(pos.down(), EnumFacing.UP)) {
        return false;
      }
    }

    for(MenhirPart menhirPart : MenhirPart.values()){
      int segment = menhirPart.getMetadata();
      IBlockState otherState = world.getBlockState(pos.up(segment - part));
      if (otherState.getBlock() != this || otherState.getValue(propertyMenhir).getMetadata() != segment) {
        return false;
      }
    }

    return true;
  }

  @Override
  public boolean hasTileEntity(IBlockState state) {
    return state.getValue(propertyMenhir).getMetadata() == 0; }
}
