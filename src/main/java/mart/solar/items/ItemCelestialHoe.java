package mart.solar.items;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.model.CustomModelItem;
import epicsquid.mysticallib.model.CustomModelLoader;
import epicsquid.mysticallib.model.ICustomModeledObject;
import epicsquid.mysticallib.model.IModeledObject;
import epicsquid.mysticallib.util.Util;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.items.interfaces.IEnergyItem;
import mart.solar.util.SolarUtil;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;

import javax.annotation.Nullable;
import java.util.List;

public class ItemCelestialHoe extends ItemHoe implements IModeledObject, ICustomModeledObject, IEnergyItem {

    private boolean hasCustomModel = false;

    public ItemCelestialHoe(ToolMaterial material, String name) {
        super(material);
        setUnlocalizedName(name);
        setRegistryName(LibRegistry.getActiveModid(), name);
        setHarvestLevel("hoe", 3);
        setMaxDamage(192);
    }

    @Override
    public int getItemEnchantability() {
        return 22;
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        if(capability == null || capability.getEnergyAmount() <= 0){
            return EnumActionResult.PASS;
        }


        EnumActionResult result = super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
        stack.setItemDamage(0);
        if(result == EnumActionResult.SUCCESS){

            if(capability.getEnergy() == EnumEnergy.SOLAR){

            }

            else if(capability.getEnergy() == EnumEnergy.LUNAR){
                for(int x = -1; x < 2; x++){
                    for(int z = -1; z < 2; z++){
                        if(worldIn.getBlockState(pos.add(x, 0, z)).getBlock() == Blocks.DIRT || worldIn.getBlockState(pos.add(x, 0, z)).getBlock() == Blocks.GRASS){
                            if(worldIn.isAirBlock(pos.add(x, 0, z).up())){
                                worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);
                                worldIn.setBlockState(pos.add(x, 0, z), Blocks.FARMLAND.getDefaultState(), 11);
                                SolarUtil.removeEnergyFromHeldItem(stack, 1, hand);
                            }
                        }
                    }
                }
                return EnumActionResult.SUCCESS;
            }
        }
        return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        if(capability == null || capability.getEnergyAmount() <= 0){
            return false;
        }

        if(capability.getEnergy() != EnumEnergy.SOLAR){
            return false;
        }

        if(state.getBlock() instanceof BlockCrops){
            BlockCrops crop = (BlockCrops) state.getBlock();
            Item drop = crop.getItemDropped(state, Util.rand, 0);
            int chance = Util.rand.nextInt(5);
            if(chance == 0){
                if(!worldIn.isRemote){
                    worldIn.spawnEntity(new EntityItem(worldIn, pos.getX(), pos.getY(), pos.getZ(), new ItemStack(drop)));
                    SolarUtil.removeEnergyFromHeldItem(stack, 1, EnumHand.MAIN_HAND);
                }
            }
        }
        return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    public ItemCelestialHoe setModelCustom(boolean custom) {
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

    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @Override
    public boolean acceptsEnergy(IEnergyEnum energyEnum) {
        return energyEnum == EnumEnergy.SOLAR || energyEnum == EnumEnergy.LUNAR;

    }

    @Override
    public int getMaxCharge() {
        return 2000;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        tooltip.add("Energy: " + capability.getEnergy().getName());
        tooltip.add("Charge: " + (capability.getEnergyAmount() / (getMaxCharge() / 100)) + "%");
    }
}
