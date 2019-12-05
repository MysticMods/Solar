package mart.solar.tile;

import mart.solar.setup.ModTiles;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;

public class AltarTile extends TileEntity implements ITickableTileEntity {

    public AltarTile() {
        super(ModTiles.ALTAR_TILE);
    }

    @Override
    public void tick() {

    }
}
