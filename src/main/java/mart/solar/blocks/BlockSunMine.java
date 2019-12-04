package mart.solar.blocks;

import epicsquid.mysticallib.block.BlockTEBase;
import mart.solar.tile.TileEntitySunMine;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public class BlockSunMine extends BlockTEBase {

    private AxisAlignedBB bounds = new AxisAlignedBB(0.1875, 0, 0.1875, 0.8125, 0.1875, 0.8125);

    public BlockSunMine(@Nonnull Material mat, @Nonnull SoundType type, float hardness, @Nonnull String name, @Nonnull Class<? extends TileEntity> teClass) {
        super(mat, type, hardness, name, teClass);
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

    @Override
    public void onEntityCollidedWithBlock(World worldIn, BlockPos pos, IBlockState state, Entity entityIn) {
        TileEntitySunMine tile = (TileEntitySunMine) worldIn.getTileEntity(pos);
        if(tile != null && entityIn != null){
            if(entityIn instanceof EntityLiving){
                tile.trigger((EntityLiving) entityIn);
            }
        }
        super.onEntityCollidedWithBlock(worldIn, pos, state, entityIn);
    }
}
