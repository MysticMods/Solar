package mart.solar.tile;

import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.particle.ParticleRenderer;
import epicsquid.mysticallib.setup.ClientProxy;
import epicsquid.mysticallib.util.Util;
import mart.solar.energy.IEnergyEnum;
import mart.solar.particle.EnergyParticle;
import mart.solar.particle.EnergyParticleData;
import mart.solar.setup.ModParticles;
import mart.solar.setup.ModTiles;
import mart.solar.util.SolarUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SUpdateTileEntityPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.util.INBTSerializable;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.ItemStackHandler;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class AltarBaseTile  extends TileEntity implements ITickableTileEntity {

    private Map<IEnergyEnum, BlockPos> elementBlocks = null;
    private int runTicks = 0;

    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(this::createItemstackHandler);

    public AltarBaseTile() {
        super(ModTiles.ALTAR_BASE_TILE);
    }
    private ItemStackHandler createItemstackHandler() {
        return new ItemStackHandler(2) {

            @Override
            protected void onContentsChanged(int slot) {
                markDirty();
            }

            @Override
            public boolean isItemValid(int slot, @Nonnull ItemStack stack) {
                System.out.println("hits");
                if (slot == 0 && stack.getItem() == Items.GOLD_INGOT) {
                    return true;
                }
                if (slot == 1 && ItemTags.getCollection().get(new ResourceLocation("forge", "ingots/silver")).contains(stack.getItem())) {
                    return true;
                }
                return false;
            }

            @Override
            protected int getStackLimit(int slot, @Nonnull ItemStack stack) {
                return 1;
            }
        };
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

    public void activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        ItemStack heldItem = player.getHeldItem(hand);

        if(!player.getHeldItem(hand).isEmpty()){
            this.handler.ifPresent(handler -> {
                ItemStack returnStack = handler.insertItem(0, player.getHeldItem(hand), false);
                if(returnStack.getCount() == player.getHeldItem(hand).getCount()){
                    returnStack = handler.insertItem(1, player.getHeldItem(hand), false);
                }
                player.setHeldItem(hand, returnStack);
                return;
            });
        }

        //if hand is empty retrieve the items.
        if (heldItem.isEmpty() && !world.isRemote && hand == Hand.MAIN_HAND && !player.isSneaking() && this.elementBlocks == null) {
            handler.ifPresent(inventory -> {
                for (int i = 1; i >= 0; i--) {
                    if (!inventory.getStackInSlot(i).isEmpty()) {
                        ItemStack extracted = inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false);
                        player.addItemStackToInventory(extracted);
                        markDirty();
                        return;
                    }
                }
            });
        }

        if (player.isSneaking()) {
            handler.ifPresent(inventory -> {
                if (inventory.getStackInSlot(0).isEmpty() || inventory.getStackInSlot(1).isEmpty()) {
                    return;
                }

                if(elementBlocks == null){
                    elementBlocks = new HashMap<>();
                }

                Map<Block, IEnergyEnum> blockEnergyMap = SolarUtil.getElementalAllBlocksAsMap();
                for (int x = -3; x < 4; x++) {
                    for (int y = -1; y < 2; y++) {
                        for (int z = -3; z < 4; z++) {
                            Block b = world.getBlockState(pos.add(x, y, z)).getBlock();
                            if(blockEnergyMap.containsKey(b)){
                                this.elementBlocks.put(blockEnergyMap.get(b), pos.add(x, y, z));
                            }
                        }
                    }
                }

                if (elementBlocks.size() != 8) {
                    this.elementBlocks = null;
                }
            });

        }
    }

    @Override
    public void tick() {
        if (this.elementBlocks != null) {
            //For each block spawn nice particles like the old times
            if(world.isRemote){

                for (Map.Entry<IEnergyEnum, BlockPos> entry : this.elementBlocks.entrySet()){
                    BlockPos beginPos = entry.getValue();
                    for(int i = 0; i < 6; i++){
                        float randX = Util.rand.nextFloat() -0.5f;
                        float randY = Util.rand.nextFloat() -0.5f;
                        float randZ = Util.rand.nextFloat() -0.5f;
                        if(ModParticles.ENERGY != null){
                            EnergyParticleData data = new EnergyParticleData(1, 150,150, 140);
                            world.addParticle(data, false, beginPos.getX() + 0.5f + randX,beginPos.getY() + 1.5f + randY,beginPos.getZ() + 0.5f + randZ, 0, 0, 0);
                        }
                        else{
                            System.out.println("AAAAAAAAAAAAH");
                        }

//                        ClientProxy.particleRenderer.spawnParticle(world, ModParticles.PARTICLE_SOLAR_LINE, beginPos.getX() + 0.5f + randX,beginPos.getY() + 0.5f + randY,beginPos.getZ() + 0.5f + randZ,
//                                getPos().getX()+0.5f, getPos().getY()+0.5f, getPos().getZ()+0.5f,
//                                255, 160, 180, 1, 2, 0.2);
                    }
                }

                //RgbColor rgbColor = RgbColorUtil.getRuneColor(this.currentEntry.getKey());


            }

            if(runTicks >= 8 * 40){
                for(Map.Entry<IEnergyEnum, BlockPos> pos : elementBlocks.entrySet()){
                    world.setBlockState(pos.getValue(), Blocks.AIR.getDefaultState());
                }
                runTicks = 0;
                elementBlocks = null;
            }

            runTicks++;
        }
    }

    public LazyOptional<ItemStackHandler> getHandler() {
        return handler;
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
}
