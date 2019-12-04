package mart.solar.tile;

import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.util.Util;
import mart.solar.particle.ParticleUtil;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ITickable;

public class TileEntitySunMine extends TileBase implements ITickable {

    private static final int MAX_CHARGE = 100;
    private int charge;

    public TileEntitySunMine(){
        this.charge = 0;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        this.charge = compound.getInteger("charge");
        super.readFromNBT(compound);
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound.setInteger("charge", this.charge);
        return super.writeToNBT(compound);
    }

    @Override
    public void update() {
        if(this.charge < MAX_CHARGE){
            if(SolarUtil.isDay(this.world)){
                this.charge++;
            }
        }
        else{
            if (this.getWorld().isRemote) {
               if(Util.rand.nextInt(3) == 0){
                   RgbColor rgbColor = RgbColorUtil.SOLAR;
                   float randX = getPos().getX()  + ((Util.rand.nextInt(100) ) / 100f);
                   float randZ = getPos().getZ() + ((Util.rand.nextInt(100) ) / 100f);
                   ParticleUtil.spawnParticleSolar(world, randX, getPos().getY() + 0.1f, randZ, 0,0,0,
                           rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 1, 100);
               }
            }
        }
    }

    public void trigger(EntityLiving entity){
        if(this.charge == MAX_CHARGE){
            entity.setHealth(entity.getHealth()-20);
            this.charge = 0;

            for(int i = 0; i < 40; i++){
                RgbColor rgbColor = RgbColorUtil.SOLAR;
                ParticleUtil.spawnParticleSolar(world, getPos().getX()+0.5f, getPos().getY() + i / 20f, getPos().getZ()+0.5f, 0,0,0,
                        rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 1, 100);
            }

        }
        markDirty();
    }
}
