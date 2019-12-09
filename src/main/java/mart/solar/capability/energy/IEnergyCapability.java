package mart.solar.capability.energy;

import mart.solar.capability.ICapability;
import mart.solar.energy.IEnergyEnum;

public interface IEnergyCapability extends ICapability {

    void setEnergy(IEnergyEnum energy);

    IEnergyEnum getEnergy();

    float getEnergyAmount();

    void setEnergyAmount(float energyAmount);

    void addEnergy(float energyAmount);

    void removeEnergy(float energyAmount);
}
