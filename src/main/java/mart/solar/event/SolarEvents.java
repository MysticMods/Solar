package mart.solar.event;

import baubles.api.BaublesApi;
import mart.solar.Solar;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.init.ModItems;
import mart.solar.items.ItemLunarRing;
import mart.solar.items.ItemSolarRing;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class SolarEvents {

    @SubscribeEvent
    public void onPlayerHurtEvent(LivingHurtEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer entityPlayer = (EntityPlayer) event.getEntityLiving();

            //Fire Damage
            if(event.getSource().isFireDamage()){
                int slot = BaublesApi.isBaubleEquipped(entityPlayer, ModItems.solar_ring);
                if(slot != -1){
                    if(ItemSolarRing.avoidsFireDamage(entityPlayer, slot)){
                        event.setAmount(0);
                    }
                }
            }

            //Fall Damage
            if(event.getSource().getDamageType().equalsIgnoreCase("fall")){
                int slot = BaublesApi.isBaubleEquipped(entityPlayer, ModItems.solar_ring);
                if(slot != -1){
                    event.setAmount(ItemLunarRing.getFallDamage(entityPlayer, event.getAmount(), slot));
                }
            }

            //Overall Damage
            int slot = BaublesApi.isBaubleEquipped(entityPlayer, ModItems.solar_ring);
            if(slot != -1){
                if(ItemLunarRing.doesNegateDamage(entityPlayer, slot)){
                    event.setAmount(0);
                }
            }
        }
    }

    @SubscribeEvent
    public void addCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if(event.getObject().getItem() == ModItems.solar_ring ||
                event.getObject().getItem() == ModItems.lunar_ring ||
                event.getObject().getItem() == ModItems.celestial_hoe) {
            event.addCapability(new ResourceLocation(Solar.MODID, "energy_capability"), new EnergyCapabilityProvider());
        }

    }

}
