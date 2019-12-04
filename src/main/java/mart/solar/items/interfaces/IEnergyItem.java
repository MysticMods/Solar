package mart.solar.items.interfaces;

import baubles.api.BaublesApi;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.enums.IEnergyEnum;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface IEnergyItem {

    boolean acceptsEnergy(IEnergyEnum energyEnum);

    int getMaxCharge();

    default boolean hasEnergy(EntityPlayer player, int slot){
        ItemStack item = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
        IEnergyCapability capability = item.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        if(capability == null){
            return false;
        }

        if(capability.getEnergyAmount() <= 0){
            return false;
        }

        return true;
    }

}
