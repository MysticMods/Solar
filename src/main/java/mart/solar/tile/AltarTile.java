package mart.solar.tile;

import mart.solar.particle.energy.EnergyParticleData;
import mart.solar.ritual.Ritual;
import mart.solar.setup.ModRituals;
import mart.solar.setup.ModTiles;
import mart.solar.tile.base.TileBase;
import mart.solar.util.SolarUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;

public class AltarTile extends TileBase implements ITickableTileEntity {

    private float initTicks = 0;

    private Ritual currentRitual = null;
    private AltarState state = AltarState.NONE;

    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(this::createItemstackHandler);

    public AltarTile() {
        super(ModTiles.ALTAR_TILE);
    }

    public void activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(player.isSneaking()){
            handler.ifPresent(inventory -> {
                Ritual ritual = ModRituals.getRitual(SolarUtil.getItemsFromHandler(inventory));
                if(ritual != null){
                    this.currentRitual = ritual;
                    this.state = AltarState.INIT;
                }
            });
            return;
        }

        if(insertItemInHandler(handler, player, hand)){
            return;
        }

        if(retrieveItemFromHandler(handler, player, hand)){
            return;
        }
    }

    @Override
    public void tick() {
        if(state == AltarState.INIT){
            if(initTicks >= 80){
                state = AltarState.ENERGY;
            }

            initTicks++;
        }

        if(state == AltarState.ENERGY){
            if(world.getGameTime() % 1 == 0){
                EnergyParticleData data = new EnergyParticleData(0.2f, 20, 255 ,255, 255, getPos().getX() +0.5f, getPos().getY()+ 3f, getPos().getZ()+0.5f);
                world.addParticle(data, false, getPos().getX() +0.5f, getPos().getY()+ 3f, getPos().getZ()+0.5f, 0, 0, 0);

            }
        }
    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));
        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });
        return super.write(tag);
    }

    private ItemStackHandler createItemstackHandler() {
        return new ItemStackHandler(6) {

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
                BlockState state = world.getBlockState(pos);
                world.notifyBlockUpdate(pos, state, state, 3);
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                return true;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        };
    }

    @Nonnull
    @Override
    public <T> LazyOptional<T> getCapability(@Nonnull Capability<T> cap) {
        if (cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            return handler.cast();

        }
        return LazyOptional.empty();
    }


    public float getInitTicks() {
        return initTicks;
    }

    public AltarState getState() {
        return state;
    }

    public enum AltarState{
        NONE,
        INIT,
        ENERGY;
    }
}
