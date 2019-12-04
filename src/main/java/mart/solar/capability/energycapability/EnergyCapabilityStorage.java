package mart.solar.capability.energycapability;

import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nullable;

public class EnergyCapabilityStorage implements Capability.IStorage<IEnergyCapability> {

    @Nullable
    @Override
    public NBTBase writeNBT(Capability<IEnergyCapability> capability, IEnergyCapability instance, EnumFacing side) {
        return instance.getData();
    }

    @Override
    public void readNBT(Capability<IEnergyCapability> capability, IEnergyCapability instance, EnumFacing side, NBTBase nbt) {
        instance.setData((NBTTagCompound) nbt);
    }
}