package mart.solar.particle.energy;

import epicsquid.mysticallib.particle.ParticleBase;
import epicsquid.mysticallib.particle.ParticleRenderer;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EnergyParticle extends ParticleBase {
    public final float goalX, goalY, goalZ;

    protected EnergyParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float[] data) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ, data);
        this.setMaxAge(60);
        setTextureLocation(new ResourceLocation("solar:textures/particles/energy.png"));

        this.goalX = data[0];
        this.goalY = data[1];
        this.goalZ = data[2];

        this.setColor(data[3] / 255, data[4] / 255, data[5] / 255);

        this.motionX = (goalX - posX) * 0.05;
        this.motionY = (goalY - posY) * 0.05;
        this.motionZ = (goalZ - posZ) * 0.05;

        this.canCollide = false;
    }

    @Override
    public void tick() {
        super.tick();
        this.motionX *= 0.95f;
        this.motionY *= 0.95f;
        this.motionZ *= 0.95f;

        this.particleScale = 0.1f;
        this.particleAlpha = 1f;

        BlockPos beginVec = new BlockPos(this.posX, this.posY, this.posZ);
        BlockPos endVec = new BlockPos(this.goalX, this.goalY, this.goalZ);

        double distance = beginVec.distanceSq(endVec);

        if (distance > -0.2 && distance < 0.2) {
            this.setExpired();
        }

        this.move(this.motionX / 4, this.motionY / 4, this.motionZ / 4);

    }

    @Override
    public IParticleRenderType getRenderType() {
        return ParticleRenderer.PARTICLE_IS_ADDITIVE;
    }


}
