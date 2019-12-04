package mart.solar.tile;

import mart.solar.energy.IEnergyEnum;
import mart.solar.setup.ModTiles;
import mart.solar.util.SolarUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
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
import java.util.List;
import java.util.Map;

public class AltarBaseTile  extends TileEntity implements ITickableTileEntity {

    private Map<IEnergyEnum, BlockPos> elementBlocks = null;
    private Map.Entry<IEnergyEnum, BlockPos> currentEntry = null;
    private int runTicks = 0;

    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(this::createItemstackHandler);

    public AltarBaseTile() {
        super(ModTiles.ALTAR_BASE_TILE);
    }

    @Override
    public void tick() {

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
            });
        }



        //if hand is empty retrieve the items.
//        if (heldItem.isEmpty() && !world.isRemote && hand == EnumHand.MAIN_HAND && !player.isSneaking() && this.elementBlocks == null) {
//            for (int i = 1; i >= 0; i--) {
//                if (!inventory.getStackInSlot(i).isEmpty()) {
//                    ItemStack extracted = inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false);
//                    world.spawnEntity(new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, extracted));
//                    return true;
//                }
//            }
//        }
//
//        if (player.isSneaking()) {
//            if (inventory.getStackInSlot(0).isEmpty() || inventory.getStackInSlot(1).isEmpty()) {
//                return true;
//            }
//            Map<IEnergyEnum, BlockPos> elementBlocks = new HashMap<>();
//            for (int x = -3; x < 4; x++) {
//                for (int y = -1; y < 2; y++) {
//                    for (int z = -3; z < 4; z++) {
//                        IEnergyEnum type = SolarUtil.elementBlocks.get(world.getBlockState(pos.add(x, y, z)).getBlock());
//                        if (type != null) {
//                            elementBlocks.put(type, pos.add(x, y, z));
//                        }
//                    }
//                }
//            }
//
//            if (EnumEnergy.values().length == elementBlocks.size()) {
//                this.elementBlocks = elementBlocks;
//            }
//        }
//
//        return super.activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
    }

//    @Override
//    public void update() {
//        if (this.elementBlocks != null) {
//            if (this.currentEntry == null) {
//                this.currentEntry = elementBlocks.entrySet().iterator().next();
//            }
//
//            if(world.isRemote){
//                BlockPos beginPos = this.currentEntry.getValue();
//                RgbColor rgbColor = RgbColorUtil.getRuneColor(this.currentEntry.getKey());
//                for(int i = 0; i < 6; i++){
//                    float randX = Util.rand.nextFloat() -0.5f;
//                    float randY = Util.rand.nextFloat() -0.5f;
//                    float randZ = Util.rand.nextFloat() -0.5f;
//                    ParticleUtil.spawnParticleSolarLine(world,
//                            beginPos.getX() + 0.5f + randX,beginPos.getY() + 0.5f + randY,beginPos.getZ() + 0.5f + randZ,
//                            getPos().getX()+0.5f, getPos().getY()+0.5f, getPos().getZ()+0.5f,
//                            rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 2, 100);
//                }
//
//            }
//
//            if(!SolarUtil.elementBlocks.containsKey(world.getBlockState(this.currentEntry.getValue()).getBlock()) || inventory.getStackInSlot(0).isEmpty() || inventory.getStackInSlot(1).isEmpty()){
//                this.elementBlocks = null;
//                this.currentEntry = null;
//                this.runTicks = 0;
//                return;
//            }
//
//            if(runTicks >= 40){
//                world.setBlockState(this.currentEntry.getValue(), Blocks.AIR.getDefaultState());
//                this.elementBlocks.remove(this.currentEntry.getKey());
//                runTicks = 0;
//                this.currentEntry = null;
//            }
//
//            if(elementBlocks.isEmpty()){
//                world.setBlockState(getPos(), ModBlocks.altar.getDefaultState());
//            }
//            runTicks++;
//        }
//    }

    //Get each tag blocks, search for blocks, add energy
    //Energy enums
}
