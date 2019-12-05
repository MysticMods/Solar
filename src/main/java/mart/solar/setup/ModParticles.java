package mart.solar.setup;

import mart.solar.Solar;
import mart.solar.particle.energy.EnergyParticleType;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(Solar.MODID)
public class ModParticles {

    public static EnergyParticleType ENERGY = new EnergyParticleType();
}