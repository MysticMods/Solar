package mart.solar.deffered;

import epicsquid.mysticallib.client.data.DeferredLanguageProvider;
import mart.solar.Solar;
import mart.solar.setup.ModBlocks;
import net.minecraft.data.DataGenerator;

public class SolarLangProvider extends DeferredLanguageProvider {

    public SolarLangProvider(DataGenerator gen) {
        super(gen, Solar.MODID);
    }

    @Override
    protected void addTranslations() {
        addBlock(ModBlocks.ALTAR);
        addBlock(ModBlocks.ALTAR_BASE);

        addBlock(ModBlocks.SUNBURNT_STONE);
        addBlock(ModBlocks.PAVED_SUNBURNT_STONE);
        addBlock(ModBlocks.ENGRAVED_SUNBURNT_STONE);
        addBlock(ModBlocks.ARCHED_SUNBURNT_STONE);
        addBlock(ModBlocks.CULLED_SUNBURNT_STONE);
        addBlock(ModBlocks.SLICED_SUNBURNT_STONE);
        addBlock(ModBlocks.CROSSED_SUNBURNT_STONE);
        addBlock(ModBlocks.TANGLED_SUNBURNT_STONE);
        addBlock(ModBlocks.EMERALD_ENDORSED_SUNBURNT_STONE);
        addBlock(ModBlocks.DIAMOND_ENDORSED_SUNBURNT_STONE);
        addBlock(ModBlocks.SUNBURNT_GLASS);

        super.addTranslations();
    }
}