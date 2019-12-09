package mart.solar.tile.base;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.Hand;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nullable;
import java.util.concurrent.atomic.AtomicBoolean;

public class TileBase extends TileEntity {

    public TileBase(TileEntityType<?> tileEntityTypeIn) {
        super(tileEntityTypeIn);
    }

    @Nullable
    @Override
    public SUpdateTileEntityPacket getUpdatePacket() {
        return new SUpdateTileEntityPacket(this.pos, 9, getUpdateTag());
    }

    @Override
    public void onDataPacket(NetworkManager net, SUpdateTileEntityPacket pkt) {
        read(pkt.getNbtCompound());
    }

    @Override
    public CompoundNBT getUpdateTag() {
        return write(super.getUpdateTag());
    }

    @Override
    public void handleUpdateTag(CompoundNBT tag) {
        read(tag);
    }

    /**
     * @param handlerIn The itemstack handler
     * @param player The player thats inserting
     * @param hand The hand the player is inserting with
     * @return Return true if the player has inserted an item, if not return false.
     */
    public boolean insertItemInHandler(LazyOptional<ItemStackHandler> handlerIn, PlayerEntity player, Hand hand){
        AtomicBoolean inserted = new AtomicBoolean(false);
        if(!player.getHeldItem(hand).isEmpty() && hand == Hand.MAIN_HAND){
            handlerIn.ifPresent(handler -> {
                for(int i = 0; i < handler.getSlots(); i++){
                    ItemStack returnStack = handler.insertItem(i, player.getHeldItem(hand), false);
                    if(returnStack.getCount() != player.getHeldItem(hand).getCount()){
                        player.setHeldItem(hand, returnStack);
                        inserted.set(true);
                        break;
                    }
                }
            });
        }
        return inserted.get();
    }

    public boolean retrieveItemFromHandler(LazyOptional<ItemStackHandler> handlerIn, PlayerEntity player, Hand hand){
        AtomicBoolean extracted = new AtomicBoolean(false);
        if(player.getHeldItem(hand).isEmpty() && hand == Hand.MAIN_HAND){
            handlerIn.ifPresent(handler -> {
                for(int i = handler.getSlots() - 1; i >= 0; i--){
                    ItemStack returnStack = handler.extractItem(i, 1, false);
                    if(returnStack != ItemStack.EMPTY){
                        player.addItemStackToInventory(returnStack);
                        extracted.set(true);
                        return;
                    }
                }
            });
        }
        return extracted.get();
    }

    public void emptyItemHandler(LazyOptional<ItemStackHandler> handlerIn){
        handlerIn.ifPresent(inventory -> {
            for(int i = 0; i < inventory.getSlots(); i++){
                inventory.setStackInSlot(i, ItemStack.EMPTY);
            }
        });
    }

    public void syncTileEntity(){
        markDirty();
        BlockState state = world.getBlockState(pos);
        world.notifyBlockUpdate(pos, state, state, 3);
    }
}
