package mart.solar.tile;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.network.MessageTEUpdate;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.util.ListUtil;
import epicsquid.mysticallib.util.Util;
import mart.solar.crafting.AltarCraftingManager;
import mart.solar.crafting.AltarCraftingRecipe;
import mart.solar.crafting.AltarCraftingRecipeRegistry;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.init.ModBlocks;
import mart.solar.particle.ParticleUtil;
import mart.solar.ritual.RitualBase;
import mart.solar.ritual.RitualRegistry;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;

public class TileEntityAltar extends TileBase implements ITickable {

  private AltarState altarState = AltarState.NONE;
  private int activeTicks = 0, intervalTicks = -1;

  //Menhir
  private BlockPos currentMenhirLocation = null;

  //Crafting
  private AltarCraftingRecipe currentCraftingRecipe = null;
  private List<ItemStack> craftingItems = new ArrayList<>();

  //Ritual
  private Map<IEnergyEnum, Integer> elementAmounts = new HashMap<>();
  private List<IEnergyEnum> elementTypes = new ArrayList<>();

  private AltarCraftingManager craftingManager;

  private List<BlockPos> menhirPositions = new ArrayList<>();

  public TileEntityAltar(){
    this.craftingManager = new AltarCraftingManager(this);
    menhirPositions.add(new BlockPos(-3, 0, -3));
    menhirPositions.add(new BlockPos(3, 0, -3));
    menhirPositions.add(new BlockPos(-3, 0, 3));
    menhirPositions.add(new BlockPos(3, 0, 3));
  }

  public ItemStackHandler inventory = new ItemStackHandler(1) {
    @Override
    protected void onContentsChanged(int slot) {
      TileEntityAltar.this.markDirty();
      if (!world.isRemote) {
        PacketHandler.INSTANCE.sendToAll(new MessageTEUpdate(TileEntityAltar.this.getUpdateTag()));
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
  public boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack heldItem = player.getHeldItem(hand);

    if(player.isSneaking() && altarState == AltarState.RITUAL){
      RitualBase ritual = RitualRegistry.getRitual(this.elementAmounts);
      if(ritual != null && !world.isRemote){
        if(ritual.canFire(world, getPos(), this)){
          ritual.fire(world, getPos(), this);
        }
      }
      altarState = AltarState.NONE;
      elementAmounts.clear();
      elementTypes.clear();
      activeTicks = 0;
      intervalTicks = -1;
      return true;
    }

    if(SolarUtil.addStackToInventory(0, inventory, heldItem, player, hand)){
      //Check for ritual
      if(SolarUtil.runes.contains(inventory.getStackInSlot(0).getItem())){
        //Throw rune out if something else is going on
        if(this.altarState != AltarState.RITUAL){
          if(!world.isRemote){
            ItemStack extracted = inventory.extractItem(0, inventory.getStackInSlot(0).getCount(), false);
            world.spawnEntity(new EntityItem(world, getPos().getX() + 0.5, getPos().getX() + 1, getPos().getZ() + 0.5, extracted));
          }
        }

        if(this.intervalTicks < 0){
          addItemToRitual();
          return true;
        }
        return true;
      }

      //Check for menhir crafting
      if(inventory.getStackInSlot(0).getItem() == ItemBlock.getItemFromBlock(Blocks.OBSIDIAN)){
        checkForMenhirs();
        return true;
      }

      //Check for altar crafting
      if(this.altarState == AltarState.NONE){
        AltarCraftingRecipe craftingRecipe = AltarCraftingRecipeRegistry.getRitual(inventory.getStackInSlot(0));
        if(craftingRecipe != null && craftingRecipe.canRun(world)){
          this.craftingManager.setCurrentCraftingRecipe(craftingRecipe);
          this.altarState = AltarState.CRAFTING;
          this.inventory.extractItem(0, 1, false);
          return true;
        }
      }

      return true;
    }


    if (heldItem.isEmpty() && !world.isRemote && hand == EnumHand.MAIN_HAND && !player.isSneaking() && altarState != AltarState.MENHIR) {
      if (!inventory.getStackInSlot(0).isEmpty()) {
        ItemStack extracted = inventory.extractItem(0, inventory.getStackInSlot(0).getCount(), false);
        world.spawnEntity(new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, extracted));
        return true;
      }
    }

    return super.activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
  }

  @Override
  public void update() {
    if (this.getWorld().isRemote) {
      RgbColor rgbColor;
      if(SolarUtil.isDay(getWorld())){
        rgbColor = RgbColorUtil.getRuneColor(EnumEnergy.SOLAR);
      } else {
        rgbColor = RgbColorUtil.getRuneColor(EnumEnergy.LUNAR);
      }
      float randX = getPos().getX()  + ((Util.rand.nextInt(200) -50) / 100f);
      float randZ = getPos().getZ() + ((Util.rand.nextInt(200) -50) / 100f);
      for(int i = 0; i < 1; i++){
        ParticleUtil.spawnParticleSolar(world, randX, getPos().getY() + 0.1f, randZ, 0,0,0,
            rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 1, 100);

      }
    }

    if(this.altarState == TileEntityAltar.AltarState.CRAFTING){
      this.craftingManager.tick();
    }

    menhirTick();
    ritualTick();
  }

  private void menhirTick(){
    if(this.altarState == AltarState.MENHIR){
      if(this.world.isRemote){
        RgbColor rgbColor = RgbColorUtil.getRuneColor(EnumEnergy.EARTH);
        for(int i = 0; i < 2; i++){
          float randX = Util.rand.nextFloat() -0.5f;
          float randY = Util.rand.nextFloat() -0.5f;
          float randZ = Util.rand.nextFloat() -0.5f;
          for(int j = 0; j < 3; j++){
            ParticleUtil.spawnParticleSolarLine(world,
                getPos().getX() + 0.5f + randX,getPos().getY() + 0.5f + randY,getPos().getZ() + 0.5f + randZ,
                this.currentMenhirLocation.getX()+0.5f, this.currentMenhirLocation.getY()+0.5f + j, this.currentMenhirLocation.getZ()+0.5f,
                rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 2, 100);
          }
        }
      }
      activeTicks++;
      if(activeTicks >= 80){
        activeTicks = 0;
        this.altarState = AltarState.NONE;


        for(int i = 0; i < 3; i++){
          if(world.getBlockState(this.currentMenhirLocation.add(0, i, 0)).getBlock() != ModBlocks.solar_infused_stone){
            this.currentMenhirLocation = null;
            this.inventory.extractItem(0, 1, false);
            return;
          }
        }

        world.setBlockState(this.currentMenhirLocation, Blocks.AIR.getDefaultState());
        world.setBlockState(this.currentMenhirLocation.add(0, 1, 0), Blocks.AIR.getDefaultState());
        world.setBlockState(this.currentMenhirLocation.add(0, 2, 0), Blocks.AIR.getDefaultState());
        world.setBlockState(this.currentMenhirLocation, ModBlocks.menhir.getStateFromMeta(0), 2);
        world.setBlockState(this.currentMenhirLocation.add(0, 1, 0), ModBlocks.menhir.getStateFromMeta(1), 2);
        world.setBlockState(this.currentMenhirLocation.add(0, 2, 0), ModBlocks.menhir.getStateFromMeta(2), 2);

        this.currentMenhirLocation = null;
        this.inventory.extractItem(0, 1, false);
      }
    }
  }

  private void ritualTick(){
    if(altarState == AltarState.RITUAL){
      this.activeTicks++;
      if(this.activeTicks > 60){
        if(this.world.isRemote){
          RgbColor color = RgbColorUtil.getRuneColor(this.elementTypes.get(Util.rand.nextInt(this.elementTypes.size())));
          ParticleUtil.spawnParticleSolar(world,
              getPos().getX() + 0.5f,getPos().getY() + 3f,getPos().getZ() + 0.5f,
              0, 0, 0,
              color.getRed(), color.getGreen(), color.getBlue(), 1, 3 + this.elementAmounts.size(), 20);
        }
      }

      if(this.intervalTicks >= 0){
        this.intervalTicks++;
      }

      if(this.intervalTicks > 60){
        addItemToRitual();
      }
    }
  }

  private void addElement(IEnergyEnum type){
    if(this.elementAmounts.get(type) == null || this.elementAmounts.get(type) == 0){
      this.elementAmounts.put(type, 1);
    }
    else{
      this.elementAmounts.put(type, this.elementAmounts.get(type) + 1);
    }

    if(!this.elementTypes.contains(type)){
      this.elementTypes.add(type);
    }
  }

  private void addItemToRitual(){
    if(SolarUtil.runes.contains(inventory.getStackInSlot(0).getItem())){
      IEnergyEnum type = SolarUtil.getTypeFromRune(inventory.getStackInSlot(0).getItem());
      addElement(type);
      shootParticle(RgbColorUtil.getRuneColor(type));
      this.inventory.extractItem(0, 1, false);
      this.intervalTicks = 0;
    }
    this.altarState = AltarState.RITUAL;
  }

  private void shootParticle(RgbColor color){
    if (world.isRemote){
      ParticleUtil.spawnParticleSolarLine(world,
          getPos().getX() + 0.5f,getPos().getY() + 0.5f,getPos().getZ() + 0.5f,
          getPos().getX() + 0.5f,getPos().getY() + 3.5f,getPos().getZ() + 0.5f,
          color.getRed(), color.getGreen(), color.getBlue(), 1, 3, 100);
    }
  }

  private void checkForMenhirs(){
    for (int x = -4; x < 5; x++) {
      for (int z = -4; z < 5; z++) {
       if(world.getBlockState(pos.add(x, 0, z)).getBlock() == ModBlocks.solar_infused_stone){
         if(world.getBlockState(pos.add(x,  1, z)).getBlock() == ModBlocks.solar_infused_stone
             && world.getBlockState(pos.add(x,  2, z)).getBlock() == ModBlocks.solar_infused_stone){
           currentMenhirLocation = pos.add(x, 0, z);
           this.altarState = AltarState.MENHIR;
           return;
         }
       }
      }
    }
  }

  public ItemStack getItem(){
    return this.inventory.getStackInSlot(0);
  }

  public List<BlockPos> getMenhirPositions() {
    return menhirPositions;
  }

  public AltarState getAltarState() {
    return altarState;
  }

  public void setAltarState(AltarState altarState) {
    this.altarState = altarState;
  }

  public enum AltarState{
    NONE,
    MENHIR,
    CRAFTING,
    RITUAL
  }

}
