package mart.solar.items;

import baubles.api.BaubleType;
import baubles.api.BaublesApi;
import baubles.api.IBauble;
import epicsquid.mysticallib.item.ItemBase;
import epicsquid.mysticallib.util.Util;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.init.ModItems;
import mart.solar.items.interfaces.IEnergyItem;
import mart.solar.util.SolarUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemLunarRing extends ItemBase implements IBauble, IEnergyItem {

    private static Map<Integer, Item> items = new HashMap<>();

    public ItemLunarRing(@Nonnull String name) {
        super(name);
        setMaxStackSize(1);
        items.put(0, ModItems.silver_boots);
        items.put(1, ModItems.silver_leggings);
        items.put(2, ModItems.silver_chestplate);
        items.put(3, ModItems.silver_helmet);
    }

    @Override
    public boolean willAutoSync(ItemStack itemstack, EntityLivingBase player) {
        return true;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase entity) {
        if(!(entity instanceof EntityPlayer)){
            return;
        }

        if(entity.getEntityWorld().getWorldTime() % 20 != 0){
            return;
        }

        IEnergyCapability capability = itemstack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        if(capability.getEnergyAmount() <= 0){
            return;
        }


        EntityPlayer player = (EntityPlayer) entity;

        if(player.inventory.armorInventory.get(1).getItem() == ModItems.silver_leggings){
            player.addPotionEffect(new PotionEffect(MobEffects.JUMP_BOOST, 200, 0));
            SolarUtil.removeEnergyFromBaubleItem( itemstack, 0.1f);
        }

        if(entity.getEntityWorld().getWorldTime() % 100 != 0){
            for(int i = 0; i < 4; i++){
                ItemStack currentStack = player.inventory.armorInventory.get(i);
                if(currentStack.getItem() == items.get(i)){
                    if(currentStack.isItemDamaged()){
                        currentStack.setItemDamage(currentStack.getItemDamage() - 1);
                        SolarUtil.removeEnergyFromBaubleItem(itemstack, 1f);
                    }
                }
            }
        }

        if(player.inventory.armorInventory.get(3).getItem() == ModItems.silver_helmet){
            List<EntityLiving> entities = Util.getEntitiesWithinRadius(player.getEntityWorld(), EntityLiving.class, player.getPosition(), 30, 30, 30);
            if(entities.size() > 0){
                boolean mobFound = false;
                for(EntityLiving entityMob : entities){
                    if(entityMob instanceof IMob){
                        entityMob.addPotionEffect( new PotionEffect(MobEffects.GLOWING, 10*20, 0));
                        mobFound = true;
                    }
                }
                if(mobFound){
                    SolarUtil.removeEnergyFromBaubleItem(itemstack, 1f);
                }
            }
        }

    }

    public static float getFallDamage(EntityPlayer player, float amount, int slot) {
        if (player.inventory.armorInventory.get(0).getItem() == ModItems.silver_boots) {
            SolarUtil.removeEnergyFromBaubleItem(player, slot, 5);
            return amount/2;
        }
        return amount;
    }

    public static boolean doesNegateDamage(EntityPlayer player, int slot) {
        if (player.inventory.armorInventory.get(2).getItem() == ModItems.silver_chestplate) {
            if(Util.rand.nextInt(100) <= 10){
                SolarUtil.removeEnergyFromBaubleItem(player, slot, 5);
                return true;
            }
        }
        return false;
    }


    @Override
    public boolean canEquip(ItemStack itemstack, EntityLivingBase player) {
        if(!(player instanceof EntityPlayer)){
            return false;
        }

        return BaublesApi.isBaubleEquipped((EntityPlayer) player, ModItems.lunar_ring) == -1;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
    }

    @Override
    public boolean acceptsEnergy(IEnergyEnum energyEnum) {
        return energyEnum == EnumEnergy.LUNAR;
    }

    @Override
    public int getMaxCharge() {
        return 500;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        tooltip.add("Charge: " + (capability.getEnergyAmount() / (getMaxCharge() / 100)) + "%");
    }
}
