package mart.solar.tile;

import epicsquid.mysticallib.util.Util;
import mart.solar.energy.IEnergyEnum;
import mart.solar.particle.energy.EnergyParticleData;
import mart.solar.ritual.CraftingRitual;
import mart.solar.ritual.Ritual;
import mart.solar.setup.ModRituals;
import mart.solar.setup.ModTiles;
import mart.solar.tile.base.TileBase;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.NBTUtil;
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
import java.util.HashMap;
import java.util.Map;

public class AltarTile extends TileBase implements ITickableTileEntity {

    private float initTicks = 0;
    private float siphonTicks = 0;
    private float itemRadius = 1;
    private float energySize = 0.2f;

    private Ritual currentRitual = null;
    private AltarState state = AltarState.NONE;
    private BlockPos siphonedBlock = null;

    private LazyOptional<ItemStackHandler> handler = LazyOptional.of(this::createItemstackHandler);

    public AltarTile() {
        super(ModTiles.ALTAR_TILE);
    }

    public boolean activate(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if(player.isSneaking()){
            this.handler.ifPresent(inventory -> {
                Ritual ritual = ModRituals.getRitual(SolarUtil.getItemsFromHandler(inventory));
                if(ritual != null){
                    this.currentRitual = ritual;
                    this.state = AltarState.INIT;
                }
            });
            return true;
        }

        if(insertItemInHandler(this.handler, player, hand)){
            return true;
        }

        if(retrieveItemFromHandler(this.handler, player, hand)){
            return true;
        }

        return false;
    }

    @Override
    public void tick() {
        if(this.currentRitual == null){
            return;
        }

        if(this.state == AltarState.INIT){
            if(this.initTicks >= 80){
                this.state = AltarState.SEARCH;
            }

            this.initTicks++;
            return;
        }

        if(world.isRemote){
            EnergyParticleData staticParticleData = new EnergyParticleData(energySize, 1, 255 ,255, 255, getPos().getX() +0.5f, getPos().getY()+ 3f, getPos().getZ()+0.5f);
            this.world.addParticle(staticParticleData, false, getPos().getX() +0.5f, getPos().getY()+ 3f, getPos().getZ()+0.5f, 0, 0, 0);

            if(state == AltarState.SIPHON){
                Block energyBlock = this.world.getBlockState(this.siphonedBlock).getBlock();
                RgbColor rgbColor = RgbColorUtil.getEnergyColor(SolarUtil.getElementalAllBlocksAsMap().get(energyBlock));
                for(int i = 0; i < 6; i++){
                    float randX = Util.rand.nextFloat() -0.5f;
                    float randY = Util.rand.nextFloat() -0.5f;
                    float randZ = Util.rand.nextFloat() -0.5f;
                    if(rgbColor != null){
                        EnergyParticleData siphonData = new EnergyParticleData(0.1f, 60, rgbColor.getRed(),rgbColor.getGreen(), rgbColor.getBlue(),
                                getPos().getX()+0.5f, getPos().getY()+0.5f, getPos().getZ()+0.5f);
                        world.addParticle(siphonData, false, this.siphonedBlock.getX() + 0.5f + randX,this.siphonedBlock.getY() + .5f + randY,this.siphonedBlock.getZ() + 0.5f + randZ, 0, 0, 0);
                    }
                }
            }

            if(state == AltarState.ENDING){
                int energy = Util.rand.nextInt(this.currentRitual.getRitualEnergy().size());
                RgbColor rgbColor = RgbColorUtil.getEnergyColor(this.currentRitual.getEnergyList().get(energy));
                for(int i = 0; i < 2; i++){
                    float randX = Util.rand.nextFloat() -0.5f;
                    float randY = Util.rand.nextFloat() -0.5f;
                    float randZ = Util.rand.nextFloat() -0.5f;
                    if(rgbColor != null){
                        EnergyParticleData siphonData = new EnergyParticleData(0.1f, 60, rgbColor.getRed(),rgbColor.getGreen(), rgbColor.getBlue(),
                                getPos().getX()+0.5f, getPos().getY()+3.5f, getPos().getZ()+0.5f);
                        world.addParticle(siphonData, false, this.pos.getX() + 0.5f + randX,this.pos.getY() + .5f + randY,this.pos.getZ() + 0.5f + randZ, 0, 0, 0);
                    }
                }
            }

            if(state == AltarState.FINISHED){
                for(int i = 0; i < 20; i++){
                    float randX = ((Util.rand.nextInt(600) -300) / 100f);
                    float randY = ((Util.rand.nextInt(600) -300) / 100f);
                    float randZ = ((Util.rand.nextInt(600) -300) / 100f);
                    int energy = Util.rand.nextInt(this.currentRitual.getRitualEnergy().size());
                    RgbColor rgbColor = RgbColorUtil.getEnergyColor(this.currentRitual.getEnergyList().get(energy));
                    EnergyParticleData siphonData = new EnergyParticleData(0.2f, 60, rgbColor.getRed(),rgbColor.getGreen(), rgbColor.getBlue(),
                            getPos().getX() + 0.5f + randX, getPos().getY() + 3.5f  + randY, getPos().getZ() + 0.5f  + randZ);
                    world.addParticle(siphonData, false, this.pos.getX() + 0.5f,this.pos.getY() + 3.5f,this.pos.getZ() + 0.5f, 0, 0, 0);
                }
            }
        }
        else{
            if(this.state == AltarState.SEARCH && this.siphonedBlock == null){
                if(this.world.getGameTime() % 20 == 0 && this.siphonTicks == 0){
                    Map<Block, IEnergyEnum> blockIEnergyEnumMap = SolarUtil.getElementalAllBlocksAsMap();
                    Map<IEnergyEnum, BlockPos> foundEnergyBlockMap = new HashMap<>();

                    for (int x = -3; x < 4; x++) {
                        for (int y = -1; y < 2; y++) {
                            for (int z = -3; z < 4; z++) {
                                Block b = this.world.getBlockState(this.pos.add(x, y, z)).getBlock();
                                if(blockIEnergyEnumMap.containsKey(b)){
                                    foundEnergyBlockMap.put(blockIEnergyEnumMap.get(b), this.pos.add(x, y, z));
                                }
                            }
                        }
                    }

                    for(Map.Entry<IEnergyEnum, Integer> entry : this.currentRitual.getRitualEnergy().entrySet()){
                        if(entry.getValue() > 0){
                            BlockPos nextBlock = foundEnergyBlockMap.get(entry.getKey());
                            if(nextBlock != null){
                                this.siphonedBlock = nextBlock;
                                this.state = AltarState.SIPHON;
                                syncTileEntity();
                                return;
                            }
                        }
                    }
                }
            }


            if(this.state == AltarState.SIPHON){
                if(this.siphonTicks++ >= 20){
                    Block energyBlock = this.world.getBlockState(this.siphonedBlock).getBlock();
                    this.currentRitual.decreaseEnergy(SolarUtil.getElementalAllBlocksAsMap().get(energyBlock));

                    world.setBlockState(this.siphonedBlock, Blocks.AIR.getDefaultState(), 3);
                    this.siphonedBlock = null;
                    this.siphonTicks = 0;
                    this.energySize += 0.1f;

                    if(this.currentRitual.hasAllEnergy()){
                        this.state = AltarState.ENDING;
                    }
                    else{
                        this.state = AltarState.SEARCH;
                    }
                    syncTileEntity();
                    return;
                }
            }
        }

        if(state == AltarState.ENDING){
            this.itemRadius -= (1f/100f);
            if(itemRadius <= 0.2f){
                this.state = AltarState.FINISHED;
                emptyItemHandler(this.handler);
                syncTileEntity();
                return;
            }
        }

        if(state == AltarState.FINISHED){
            System.out.println("finish");
            if(this.currentRitual instanceof CraftingRitual){
                CraftingRitual ritual = (CraftingRitual) this.currentRitual;

                if(!world.isRemote){
                    world.addEntity(new ItemEntity(world, pos.getX() + 0.5, pos.getY() + 3.5f, pos.getZ(), new ItemStack(ritual.getOutput())));
                }

                this.state = AltarState.NONE;
                this.currentRitual = null;
                this.energySize = 0.2f;
                this.itemRadius = 1f;
                this.initTicks = 0;
                syncTileEntity();
            }
        }

    }

    @Override
    public void read(CompoundNBT tag) {
        CompoundNBT invTag = tag.getCompound("inv");
        handler.ifPresent(h -> ((INBTSerializable<CompoundNBT>) h).deserializeNBT(invTag));

        this.siphonTicks = tag.getFloat("siphonTicks");
        this.state = AltarState.valueOf(tag.getString("state"));
        this.energySize = tag.getFloat("energySize");
        this.initTicks = tag.getFloat("initTicks");

        if(tag.get("siphonedBlock") != null){
            this.siphonedBlock = NBTUtil.readBlockPos((CompoundNBT) tag.get("siphonedBlock"));
        }

        super.read(tag);
    }

    @Override
    public CompoundNBT write(CompoundNBT tag) {
        handler.ifPresent(h -> {
            CompoundNBT compound = ((INBTSerializable<CompoundNBT>) h).serializeNBT();
            tag.put("inv", compound);
        });

        tag.putString("state", this.state.name());
        tag.putFloat("siphonTicks", this.siphonTicks);
        tag.putFloat("energySize", this.energySize);
        tag.putFloat("initTicks", this.initTicks);

        if(this.siphonedBlock != null){
            tag.put("siphonedBlock", NBTUtil.writeBlockPos(this.siphonedBlock));
        }
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


    public float getItemRadius() {
        return itemRadius;
    }

    public enum AltarState{
        NONE,
        INIT,
        SEARCH,
        SIPHON,
        ENDING,
        FINISHED;
    }
}
