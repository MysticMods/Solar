package mart.solar.network;

import baubles.api.BaublesApi;
import io.netty.buffer.ByteBuf;
import mart.solar.capability.energycapability.EnergyCapability;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.capability.energycapability.IEnergyCapability;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumHand;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class EnergyCapabilityMessage implements IMessage {

    public boolean isBaubles;
    public int slot;
    public ItemStack itemStack;
    public NBTTagCompound compound;

    public EnergyCapabilityMessage(){

    }

    public EnergyCapabilityMessage(boolean isBaubles, int slot, ItemStack itemStack, NBTTagCompound compound){
        this.isBaubles = isBaubles;
        this.slot = slot;
        this.itemStack = itemStack;
        this.compound = compound;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        this.itemStack = ByteBufUtils.readItemStack(buf);
        this.compound = ByteBufUtils.readTag(buf);
        this.isBaubles = buf.readBoolean();
        this.slot = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        ByteBufUtils.writeItemStack(buf, itemStack);
        ByteBufUtils.writeTag(buf, compound);
        buf.writeBoolean(isBaubles);
        buf.writeInt(slot);
    }

    public static class MessageHolder implements IMessageHandler<EnergyCapabilityMessage, IMessage> {
        @SideOnly(Side.CLIENT)
        @Override
        public IMessage onMessage(final EnergyCapabilityMessage message, final MessageContext ctx) {
            if (Minecraft.getMinecraft().player == null) {
                return null;
            }

            EntityPlayer player = Minecraft.getMinecraft().player;
            IEnergyCapability capability = null;

            if(!message.isBaubles){
                if(message.slot < 0){
                    if(message.slot == -1){
                        capability = player.getHeldItem(EnumHand.MAIN_HAND).getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
                    }
                    else if(message.slot == -2){
                        capability = player.getHeldItem(EnumHand.OFF_HAND).getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
                    }
                    if(capability == null){
                        return null;
                    }
                }
                return null;
            }
            else{
                if(message.slot != -1){
                    capability = BaublesApi.getBaublesHandler(player).getStackInSlot(message.slot).getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
                }
                else{
                    int slot = BaublesApi.isBaubleEquipped(player, message.itemStack.getItem());
                    if(slot > -1){
                        capability = BaublesApi.getBaublesHandler(player).getStackInSlot(slot).getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);
                    }
                }

                if(capability == null){
                    return null;
                }
            }

            capability.setData(message.compound);

            return null;
        }
    }
}
