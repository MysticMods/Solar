package mart.solar.deffered;

import epicsquid.mysticallib.client.data.DeferredItemModelProvider;
import mart.solar.Solar;
import mart.solar.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class SolarItemModelProvider extends DeferredItemModelProvider {

    public SolarItemModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super("Solar Item Model Generator", generator, Solar.MODID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        blockItem(ModBlocks.SUNBURNT_STONE);
        blockItem(ModBlocks.PAVED_SUNBURNT_STONE);
        blockItem(ModBlocks.ENGRAVED_SUNBURNT_STONE);
        blockItem(ModBlocks.ARCHED_SUNBURNT_STONE);
        blockItem(ModBlocks.CULLED_SUNBURNT_STONE);
        blockItem(ModBlocks.SLICED_SUNBURNT_STONE);
        blockItem(ModBlocks.CROSSED_SUNBURNT_STONE);
        blockItem(ModBlocks.TANGLED_SUNBURNT_STONE);
        blockItem(ModBlocks.EMERALD_ENDORSED_SUNBURNT_STONE);
        blockItem(ModBlocks.DIAMOND_ENDORSED_SUNBURNT_STONE);
        blockItem(ModBlocks.SUNBURNT_GLASS);
    }
}
