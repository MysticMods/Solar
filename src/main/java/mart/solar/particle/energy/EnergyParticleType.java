package mart.solar.particle.energy;

import mart.solar.Solar;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.particles.ParticleType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

public class EnergyParticleType extends ParticleType<EnergyParticleData> {

    public EnergyParticleType() {
        super(false, EnergyParticleData.DESERIALIZER);
        setRegistryName(new ResourceLocation(Solar.MODID, "energy"));
    }



    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<EnergyParticleData> {

        @Override
        public Particle makeParticle(EnergyParticleData typeIn, World world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            EnergyParticle particle = new EnergyParticle(world, x, y, z, xSpeed, ySpeed, zSpeed, new float[]{typeIn.goalX, typeIn.goalY, typeIn.goalZ, typeIn.r, typeIn.g, typeIn.b});
            return particle;

        }
    }
}
