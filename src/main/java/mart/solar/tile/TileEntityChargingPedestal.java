package mart.solar.tile;

import epicsquid.mysticallib.network.MessageTEUpdate;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.util.Util;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.items.interfaces.IEnergyItem;
import mart.solar.particle.ParticleUtil;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class TileEntityChargingPedestal extends TileBase implements ITickable {

    public ItemStackHandler inventory = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            TileEntityChargingPedestal.this.markDirty();
            if (!world.isRemote) {
                PacketHandler.INSTANCE.sendToAll(new MessageTEUpdate(TileEntityChargingPedestal.this.getUpdateTag()));
            }
        }
    };

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound tag) {
        super.writeToNBT(tag);
        tag.setTag("inventory", inventory.serializeNBT());
        return tag;
    }

    @Override
    public void readFromNBT(NBTTagCompound tag) {
        super.readFromNBT(tag);
        inventory.deserializeNBT(tag.getCompoundTag("inventory"));
    }

    @Override
    public boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand,
                            @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
        ItemStack heldItem = player.getHeldItem(hand);

        //Checking for the gold
        if (heldItem.getItem() instanceof IEnergyItem) {
            if(SolarUtil.addStackToInventory(0, inventory, heldItem, player, hand)){
                return true;
            }
        }

        if (heldItem.isEmpty() && !world.isRemote && hand == EnumHand.MAIN_HAND && !player.isSneaking()) {
            if (!inventory.getStackInSlot(0).isEmpty()) {
                ItemStack extracted = inventory.extractItem(0, inventory.getStackInSlot(0).getCount(), false);
                world.spawnEntity(new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, extracted));
                return true;
            }
        }

        return true;
    }

    @Override
    public void update() {
        if(inventory.getStackInSlot(0).isEmpty()){
            return;
        }

        ItemStack item = inventory.getStackInSlot(0);
        IEnergyCapability capability = item.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
        if(capability == null){
            return;
        }

        IEnergyItem energyItem = (IEnergyItem) item.getItem();

        IEnergyEnum energyType;
        if(SolarUtil.isDay(getWorld())){
            energyType = EnumEnergy.SOLAR;
        }
        else{
            energyType = EnumEnergy.LUNAR;
        }

        RgbColor color = RgbColorUtil.getRuneColor(energyType);

        if(capability.getEnergyAmount() >= energyItem.getMaxCharge() || !energyItem.acceptsEnergy(energyType)){
            return;
        }

        if(capability.getEnergy() != energyType){
            if(capability.getEnergyAmount() == 0){
                capability.setEnergy(energyType);
            }
            else{
                return;
            }
        }

        capability.addEnergy(1);

        float randX = (Util.rand.nextInt(200) - 100) / 100f;
        float randZ = (Util.rand.nextInt(200) - 100) / 100f;
        float randY = Util.rand.nextFloat();

        ParticleUtil.spawnParticleSolarLine(world,
                getPos().getX() + randX + 0.5f,getPos().getY() + randY + 0.5f,getPos().getZ() + randZ + 0.5f,
                getPos().getX() + 0.5f,getPos().getY() + 1f,getPos().getZ() + 0.5f,
                color.getRed(), color.getGreen(), color.getBlue(), 1, 1, 40);
    }
}
