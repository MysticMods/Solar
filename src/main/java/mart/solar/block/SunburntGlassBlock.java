package mart.solar.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.util.BlockRenderLayer;

public class SunburntGlassBlock extends Block {

    public SunburntGlassBlock(Properties properties) {
        super(properties);
    }

    @Override
    public boolean isSolid(BlockState state) {
        return false;
    }


    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }
}
