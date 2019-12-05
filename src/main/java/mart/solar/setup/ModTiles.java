package mart.solar.setup;

import mart.solar.Solar;
import mart.solar.tile.AltarBaseTile;
import mart.solar.tile.AltarTile;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Solar.MODID)
public class ModTiles {
    public static final TileEntityType<AltarBaseTile> ALTAR_BASE_TILE = null;
    public static final TileEntityType<AltarTile> ALTAR_TILE = null;
}
