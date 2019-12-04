package mart.solar.init;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.particle.ParticleRegistry;
import mart.solar.particle.ParticleSolar;
import mart.solar.particle.ParticleSolarLine;
import net.minecraft.util.ResourceLocation;

public class ModParticles {
  public static String PARTICLE_SOLAR, PARTICLE_SOLAR_LINE;

  public static void init() {
    PARTICLE_SOLAR = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleSolar.class, new ResourceLocation("solar:particles/particle_energy"));
    PARTICLE_SOLAR_LINE = ParticleRegistry.registerParticle(MysticalLib.MODID, ParticleSolarLine.class, new ResourceLocation("solar:particles/particle_energy"));
  }

}
