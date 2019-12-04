package mart.solar.capability.energycapability;

import mart.solar.enums.EnumEnergy;
import mart.solar.enums.EnergyRegistry;
import mart.solar.enums.IEnergyEnum;
import net.minecraft.nbt.NBTTagCompound;

public class EnergyCapability implements IEnergyCapability {

    private IEnergyEnum energy;
    private float energyAmount;
    private boolean dirty = true;

    public EnergyCapability(){
        this.energyAmount = 0;
        this.energy = EnumEnergy.SOLAR;
    }

    @Override
    public NBTTagCompound getData() {
        NBTTagCompound compound = new NBTTagCompound();

        compound.setString("energy", energy.getName());
        compound.setFloat("energyAmount", energyAmount);

        return compound;
    }

    @Override
    public void setData(NBTTagCompound tag) {
        this.energy = EnergyRegistry.getEnergy(tag.getString("energy"));

        this.energyAmount = tag.getFloat("energyAmount");
    }

    @Override
    public void markDirty() {
        this.dirty = true;
    }

    @Override
    public boolean isDirty() {
        return this.dirty;
    }

    @Override
    public void clean() {
        this.dirty = false;
    }

    @Override
    public void setEnergy(IEnergyEnum energy) {
        this.energy = energy;
    }

    @Override
    public IEnergyEnum getEnergy() {
        return energy;
    }

    @Override
    public float getEnergyAmount() {
        return energyAmount;
    }

    @Override
    public void setEnergyAmount(float energyAmount) {
        this.energyAmount = energyAmount;
    }

    @Override
    public void addEnergy(float energyAmount) {
        this.energyAmount += energyAmount;
    }

    @Override
    public void removeEnergy(float energyAmount) {
        this.energyAmount -= energyAmount;
        if(this.energyAmount < 0){
            this.energyAmount = 0;
        }
    }
}
