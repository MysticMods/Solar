package mart.solar.capability.energy;

import mart.solar.Solar;
import mart.solar.energy.EnergyEnum;
import mart.solar.energy.IEnergyEnum;
import net.minecraft.nbt.CompoundNBT;

public class EnergyCapability implements IEnergyCapability {

    private IEnergyEnum energy;
    private float energyAmount;
    private boolean dirty = true;

    public EnergyCapability(){
        this.energyAmount = 0;
        this.energy = EnergyEnum.SOLAR;
    }

    @Override
    public CompoundNBT getData() {
        CompoundNBT compound = new CompoundNBT();

        compound.putString("energy", energy.getName());
        compound.putFloat("energyAmount", energyAmount);

        return compound;
    }

    @Override
    public void setData(CompoundNBT tag) {
        this.energy = Solar.ENERGY.getEnergyType(tag.getString("energy"));

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