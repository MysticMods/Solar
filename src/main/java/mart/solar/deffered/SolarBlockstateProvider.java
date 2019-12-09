package mart.solar.deffered;

import epicsquid.mysticallib.client.data.DeferredBlockStateProvider;
import mart.solar.Solar;
import mart.solar.setup.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.client.model.generators.ExistingFileHelper;

public class SolarBlockstateProvider extends DeferredBlockStateProvider {

    public SolarBlockstateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super("Solar Blockstate and Block Model provider", gen, Solar.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        simpleBlock(ModBlocks.SUNBURNT_STONE);
        simpleBlock(ModBlocks.PAVED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.ENGRAVED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.ARCHED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.CULLED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.SLICED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.CROSSED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.TANGLED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.EMERALD_ENDORSED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.DIAMOND_ENDORSED_SUNBURNT_STONE);
        simpleBlock(ModBlocks.SUNBURNT_GLASS);
    }
}
