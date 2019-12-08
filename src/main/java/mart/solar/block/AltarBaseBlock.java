package mart.solar.block;

import epicsquid.mysticallib.util.Util;
import mart.solar.tile.AltarBaseTile;
import mart.solar.tile.base.ITile;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class AltarBaseBlock extends Block implements ITile {

    protected static final VoxelShape SHAPE = Block.makeCuboidShape(0.0D, 0.0D, 0.0D, 16.0D, 13.0D, 16.0D);

    public AltarBaseBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        AltarBaseTile tile = (AltarBaseTile) worldIn.getTileEntity(pos);
        tile.activate(state, worldIn, pos, player, handIn, hit);
        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }

    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return new AltarBaseTile();
    }

    @Override
    public Supplier<AltarBaseTile> getTile() {
        return AltarBaseTile::new;
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT_MIPPED;
    }

    @Override
    public VoxelShape getShape(BlockState p_220053_1_, IBlockReader p_220053_2_, BlockPos p_220053_3_, ISelectionContext p_220053_4_) {
        return SHAPE;
    }

    @Override
    public void onReplaced(BlockState state, World worldIn, BlockPos pos, BlockState newState, boolean isMoving) {
        AltarBaseTile tile = (AltarBaseTile)worldIn.getTileEntity(pos);
        tile.getHandler().ifPresent(handler -> Util.spawnInventoryInWorld(worldIn, pos.getX(), pos.getY(), pos.getZ(), handler));

        super.onReplaced(state, worldIn, pos, newState, isMoving);
    }
}
