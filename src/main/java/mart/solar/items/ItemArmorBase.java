package mart.solar.items;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.item.ItemBase;
import epicsquid.mysticallib.model.CustomModelItem;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.common.util.EnumHelper;

import javax.annotation.Nonnull;

public class ItemArmorBase extends ItemArmor  implements IModeledObject, ICustomModeledObject {

    private boolean hasCustomModel = false;

    public static ArmorMaterial SILVER = EnumHelper.addArmorMaterial("silver", "solar:silver",200, new int[] {4, 9, 7, 4}, 30, SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, 5.0F);

    public ItemArmorBase(@Nonnull String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
        super(materialIn, renderIndexIn, equipmentSlotIn);
        setUnlocalizedName(name);
        setRegistryName(LibRegistry.getActiveModid(), name);
    }

    public ItemArmorBase setModelCustom(boolean custom) {
        this.hasCustomModel = custom;
        return this;
    }

    @Override
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "handlers"));
    }

    @Override
    public void initCustomModel() {
        if (this.hasCustomModel) {
            CustomModelLoader.itemmodels.put(getRegistryName(),
                    new CustomModelItem(false, new ResourceLocation(getRegistryName().getResourceDomain() + ":items/" + getRegistryName().getResourcePath())));
        }
    }
}
