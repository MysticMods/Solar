package mart.solar.particle;

import epicsquid.mysticallib.particle.ParticleBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ParticleSolarLine extends ParticleBase {
  public float colorR = 0;
  public float colorG = 0;
  public float colorB = 0;
  public float initAlpha = 1.0f;
  public float initScale = 0;

  public double vx, vy, vz;

  public ParticleSolarLine(World world, double x, double y, double z, double vx, double vy, double vz, double[] data) {
    super(world, x, y, z, vx, vy, vz, data);
    this.colorR = (float) data[1];
    this.colorG = (float) data[2];
    this.colorB = (float) data[3];
    if (this.colorR > 1.0) {
      this.colorR = this.colorR / 255.0f;
    }
    if (this.colorG > 1.0) {
      this.colorG = this.colorG / 255.0f;
    }
    if (this.colorB > 1.0) {
      this.colorB = this.colorB / 255.0f;
    }
    this.setRBGColorF(colorR, colorG, colorB);
    this.setAlphaF((float) data[4]);
    this.initAlpha = (float) data[4];
    this.particleScale = (float) data[5];
    this.initScale = (float) data[5];
    this.vx = vx;
    this.vy = vy;
    this.vz = vz;
    this.motionX = (vx - x) * 0.05;
    this.motionY = (vy - y) * 0.05;
    this.motionZ = (vz - z) * 0.05;
  }

  @Override
  public void onUpdate() {
    super.onUpdate();
    float lifeCoeff = (float) this.particleAge / (float) this.particleMaxAge;
    this.particleScale = initScale - initScale * lifeCoeff;
    this.particleAlpha = initAlpha * (1.0f - lifeCoeff);

    BlockPos beginVec = new BlockPos(this.posX, this.posY, this.posZ);
    BlockPos endVec = new BlockPos(this.vx, this.vy, this.vz);

    double distance = beginVec.distanceSq(endVec);

    if (distance > -0.2 && distance < 0.2) {
      this.setExpired();
    }

    this.move(this.motionX / 4, this.motionY / 4, this.motionZ / 4);
  }

  @Override
  public boolean isAdditive() {
    return true;
  }
}
