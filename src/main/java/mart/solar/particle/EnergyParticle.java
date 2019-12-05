package mart.solar.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EnergyParticle extends SpriteTexturedParticle {

    public static final ResourceLocation particles = new ResourceLocation("solar:textures/particles/energy.png");


    public final float goalX, goalY, goalZ;

    protected EnergyParticle(World world, double posX, double posY, double posZ, double motionX, double motionY, double motionZ, float[] data) {
        super(world, posX, posY, posZ, motionX, motionY, motionZ);
        this.setMaxAge(60);
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
    public void renderParticle(BufferBuilder buffer, ActiveRenderInfo entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        float f = this.getScale(partialTicks);
        float f1 = 0;
        float f2 = 1;
        float f3 = 0;
        float f4 = 1;
        float f5 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosX, this.posX) - interpPosX);
        float f6 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosY, this.posY) - interpPosY);
        float f7 = (float)(MathHelper.lerp((double)partialTicks, this.prevPosZ, this.posZ) - interpPosZ);
        int i = this.getBrightnessForRender(partialTicks);
        int j = i >> 16 & '\uffff';
        int k = i & '\uffff';
        Vec3d[] avec3d = new Vec3d[]{new Vec3d((double)(-rotationX * f - rotationXY * f), (double)(-rotationZ * f), (double)(-rotationYZ * f - rotationXZ * f)), new Vec3d((double)(-rotationX * f + rotationXY * f), (double)(rotationZ * f), (double)(-rotationYZ * f + rotationXZ * f)), new Vec3d((double)(rotationX * f + rotationXY * f), (double)(rotationZ * f), (double)(rotationYZ * f + rotationXZ * f)), new Vec3d((double)(rotationX * f - rotationXY * f), (double)(-rotationZ * f), (double)(rotationYZ * f - rotationXZ * f))};
        if (this.particleAngle != 0.0F) {
            float f8 = MathHelper.lerp(partialTicks, this.prevParticleAngle, this.particleAngle);
            float f9 = MathHelper.cos(f8 * 0.5F);
            float f10 = (float)((double)MathHelper.sin(f8 * 0.5F) * entityIn.getLookDirection().x);
            float f11 = (float)((double)MathHelper.sin(f8 * 0.5F) * entityIn.getLookDirection().y);
            float f12 = (float)((double)MathHelper.sin(f8 * 0.5F) * entityIn.getLookDirection().z);
            Vec3d vec3d = new Vec3d((double)f10, (double)f11, (double)f12);

            for(int l = 0; l < 4; ++l) {
                avec3d[l] = vec3d.scale(2.0D * avec3d[l].dotProduct(vec3d)).add(avec3d[l].scale((double)(f9 * f9) - vec3d.dotProduct(vec3d))).add(vec3d.crossProduct(avec3d[l]).scale((double)(2.0F * f9)));
            }
        }

        Minecraft.getInstance().getTextureManager().bindTexture(particles);
        buffer.pos((double)f5 + avec3d[0].x, (double)f6 + avec3d[0].y, (double)f7 + avec3d[0].z).tex((double)f2, (double)f4).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[1].x, (double)f6 + avec3d[1].y, (double)f7 + avec3d[1].z).tex((double)f2, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[2].x, (double)f6 + avec3d[2].y, (double)f7 + avec3d[2].z).tex((double)f1, (double)f3).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
        buffer.pos((double)f5 + avec3d[3].x, (double)f6 + avec3d[3].y, (double)f7 + avec3d[3].z).tex((double)f1, (double)f4).color(this.particleRed, this.particleGreen, this.particleBlue, this.particleAlpha).lightmap(j, k).endVertex();
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
        return ICustomParticleRender.PARTICLE_SHEET_TRANSLUCENT_GLOW;
    }


}
