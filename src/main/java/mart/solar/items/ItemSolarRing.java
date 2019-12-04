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
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ItemSolarRing extends ItemBase implements IBauble, IEnergyItem {

    private static Map<Integer, Item> items = new HashMap<>();
    private static List<Item> tools = new ArrayList<>();

    public ItemSolarRing(@Nonnull String name) {
        super(name);
        setMaxStackSize(1);
        items.put(0, Items.GOLDEN_BOOTS);
        items.put(1, Items.GOLDEN_LEGGINGS);
        items.put(2, Items.GOLDEN_CHESTPLATE);
        items.put(3, Items.GOLDEN_HELMET);

        tools.add(Items.GOLDEN_AXE);
        tools.add(Items.GOLDEN_PICKAXE);
        tools.add(Items.GOLDEN_HOE);
        tools.add(Items.GOLDEN_SHOVEL);
        tools.add(Items.GOLDEN_SWORD);
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
        if(player.inventory.armorInventory.get(3).getItem() == Items.GOLDEN_HELMET){
            if(Util.rand.nextInt(100) <= 20){
                player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 60, 0));
                SolarUtil.removeEnergyFromBaubleItem(itemstack, 5f);
            }
        }

        if(player.inventory.armorInventory.get(1).getItem() == Items.GOLDEN_LEGGINGS){
            player.addPotionEffect(new PotionEffect(MobEffects.SPEED, 200, 0));
            SolarUtil.removeEnergyFromBaubleItem(itemstack, 0.1f);
        }

        for(int i = 0; i < 4; i++){
            ItemStack currentStack = player.inventory.armorInventory.get(i);
            if(currentStack.getItem() == items.get(i)){
                if(currentStack.isItemDamaged()){
                    currentStack.setItemDamage(currentStack.getItemDamage() - 1);
                    SolarUtil.removeEnergyFromBaubleItem(itemstack, 1f);
                }
            }
        }

        if(tools.contains(entity.getHeldItem(EnumHand.MAIN_HAND).getItem())){
            ItemStack stack = entity.getHeldItem(EnumHand.MAIN_HAND);
            if(stack.isItemDamaged()){
                stack.setItemDamage(stack.getItemDamage() - 1);
                SolarUtil.removeEnergyFromBaubleItem(itemstack, 1f);
            }
        }

    }

    public static boolean avoidsFireDamage(EntityPlayer player, int slot){
        if (player.inventory.armorInventory.get(0).getItem() == Items.GOLDEN_BOOTS) {
            if(Util.rand.nextInt(100) <= 50){
                SolarUtil.removeEnergyFromBaubleItem(player, slot, 5);
                return true;
            }
        }

        if (player.inventory.armorInventory.get(0).getItem() == Items.GOLDEN_CHESTPLATE) {
            if(Util.rand.nextInt(100) <= 50){
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

        return BaublesApi.isBaubleEquipped((EntityPlayer) player, ModItems.solar_ring) == -1;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.RING;
    }

    @Override
    public boolean acceptsEnergy(IEnergyEnum energyEnum) {
        return energyEnum == EnumEnergy.SOLAR;
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
