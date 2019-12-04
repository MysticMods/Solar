package mart.solar.tile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.network.MessageTEUpdate;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.util.Util;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.init.ModBlocks;
import mart.solar.particle.ParticleUtil;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.oredict.OreDictionary;

public class TileEntityAltarBase extends TileBase implements ITickable {

  private Map<IEnergyEnum, BlockPos> elementBlocks = null;
  private Map.Entry<IEnergyEnum, BlockPos> currentEntry = null;
  private int runTicks = 0;

  public ItemStackHandler inventory = new ItemStackHandler(2) {
    @Override
    protected void onContentsChanged(int slot) {
      TileEntityAltarBase.this.markDirty();
      if (!world.isRemote) {
        PacketHandler.INSTANCE.sendToAll(new MessageTEUpdate(TileEntityAltarBase.this.getUpdateTag()));
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
    if (heldItem.getItem() == Items.GOLD_INGOT) {
      if(SolarUtil.addStackToInventory(0, inventory, heldItem, player, hand)){
        return true;
      }
    }

    //Checking for the silver
    List<ItemStack> silverItems = OreDictionary.getOres("ingotSilver");
    for (ItemStack s : silverItems) {
      if (s.getItem() == heldItem.getItem()) {
        if(SolarUtil.addStackToInventory(1, inventory, heldItem, player, hand)){
          return true;
        }
      }
    }

    //if hand is empty retrieve the items.
    if (heldItem.isEmpty() && !world.isRemote && hand == EnumHand.MAIN_HAND && !player.isSneaking() && this.elementBlocks == null) {
      for (int i = 1; i >= 0; i--) {
        if (!inventory.getStackInSlot(i).isEmpty()) {
          ItemStack extracted = inventory.extractItem(i, inventory.getStackInSlot(i).getCount(), false);
          world.spawnEntity(new EntityItem(world, player.posX, player.posY + 0.5, player.posZ, extracted));
          return true;
        }
      }
    }

    if (player.isSneaking()) {
      if (inventory.getStackInSlot(0).isEmpty() || inventory.getStackInSlot(1).isEmpty()) {
        return true;
      }
      Map<IEnergyEnum, BlockPos> elementBlocks = new HashMap<>();
      for (int x = -3; x < 4; x++) {
        for (int y = -1; y < 2; y++) {
          for (int z = -3; z < 4; z++) {
            IEnergyEnum type = SolarUtil.elementBlocks.get(world.getBlockState(pos.add(x, y, z)).getBlock());
            if (type != null) {
              elementBlocks.put(type, pos.add(x, y, z));
            }
          }
        }
      }

      if (EnumEnergy.values().length == elementBlocks.size()) {
        this.elementBlocks = elementBlocks;
      }
    }

    return super.activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
  }

  @Override
  public void update() {
    if (this.elementBlocks != null) {
      if (this.currentEntry == null) {
        this.currentEntry = elementBlocks.entrySet().iterator().next();
      }

      if(world.isRemote){
        BlockPos beginPos = this.currentEntry.getValue();
        RgbColor rgbColor = RgbColorUtil.getRuneColor(this.currentEntry.getKey());
        for(int i = 0; i < 6; i++){
          float randX = Util.rand.nextFloat() -0.5f;
          float randY = Util.rand.nextFloat() -0.5f;
          float randZ = Util.rand.nextFloat() -0.5f;
          ParticleUtil.spawnParticleSolarLine(world,
              beginPos.getX() + 0.5f + randX,beginPos.getY() + 0.5f + randY,beginPos.getZ() + 0.5f + randZ,
              getPos().getX()+0.5f, getPos().getY()+0.5f, getPos().getZ()+0.5f,
              rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 2, 100);
        }

      }

      if(!SolarUtil.elementBlocks.containsKey(world.getBlockState(this.currentEntry.getValue()).getBlock()) || inventory.getStackInSlot(0).isEmpty() || inventory.getStackInSlot(1).isEmpty()){
        this.elementBlocks = null;
        this.currentEntry = null;
        this.runTicks = 0;
        return;
      }

      if(runTicks >= 40){
        world.setBlockState(this.currentEntry.getValue(), Blocks.AIR.getDefaultState());
        this.elementBlocks.remove(this.currentEntry.getKey());
        runTicks = 0;
        this.currentEntry = null;
      }

      if(elementBlocks.isEmpty()){
        world.setBlockState(getPos(), ModBlocks.altar.getDefaultState());
      }
      runTicks++;
    }
  }
}
