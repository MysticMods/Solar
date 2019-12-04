package mart.solar.capability.energycapability;

import mart.solar.capability.ICapability;
import mart.solar.enums.IEnergyEnum;

public interface IEnergyCapability extends ICapability {

    void setEnergy(IEnergyEnum energy);

    IEnergyEnum getEnergy();

    float getEnergyAmount();

    void setEnergyAmount(float energyAmount);

    void addEnergy(float energyAmount);

    void removeEnergy(float energyAmount);
}
