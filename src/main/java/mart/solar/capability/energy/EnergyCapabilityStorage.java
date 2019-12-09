package mart.solar.capability.energy;

import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.INBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.capabilities.Capability;

public class EnergyCapabilityStorage implements Capability.IStorage<IEnergyCapability> {

    @Override
    public INBT writeNBT(Capability<IEnergyCapability> capability, IEnergyCapability instance, Direction side) {
        return instance.getData();
    }

    @Override
    public void readNBT(Capability<IEnergyCapability> capability, IEnergyCapability instance, Direction side, INBT nbt) {
        instance.setData((CompoundNBT) nbt);
    }
}