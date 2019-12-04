package mart.solar.items;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import epicsquid.mysticallib.item.ItemBase;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.init.ModItems;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Items;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ItemPendant extends ItemBase implements IBauble {

    private static Map<IEnergyEnum, ItemPendant> pendantTypes = new HashMap<>();

    public ItemPendant(@Nonnull String name) {
        super(name);
        setMaxStackSize(1);
        this.addPropertyOverride(new ResourceLocation("pull"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                if (entityIn == null)
                {
                    return 0.0F;
                }
                else
                {
                    return entityIn.getActiveItemStack().getItem() != ModItems.pendant ? 0.0F : (float)(20 - entityIn.getItemInUseCount()) / 20.0F;
                }
            }
        });
        this.addPropertyOverride(new ResourceLocation("pulling"), new IItemPropertyGetter()
        {
            @SideOnly(Side.CLIENT)
            public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn)
            {
                return entityIn != null && entityIn.isHandActive() && entityIn.getActiveItemStack() == stack ? 1.0F : 0.0F;
            }
        });
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemStack) {
        return BaubleType.AMULET;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {

    }

    public static void addEnergyType(IEnergyEnum energy, ItemPendant item ){
        if(!pendantTypes.containsKey(energy)){
            pendantTypes.put(energy, item);
        }
    }

    public static ItemPendant getPendant(IEnergyEnum energyEnum){
        return pendantTypes.get(energyEnum);
    }

    public static IEnergyEnum getEnergyType(ItemPendant pendant){
        if(pendantTypes.values().contains(pendant)){
            for(Map.Entry<IEnergyEnum, ItemPendant> entry : pendantTypes.entrySet()){
                if(entry.getValue() == pendant){
                    return entry.getKey();
                }
            }
        }
        return null;
    }
}
