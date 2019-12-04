package mart.solar.tile;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import epicsquid.mysticallib.network.MessageTEUpdate;
import epicsquid.mysticallib.network.PacketHandler;
import epicsquid.mysticallib.tile.TileBase;
import epicsquid.mysticallib.util.Util;
import mart.solar.enums.EnumEnergy;
import mart.solar.enums.IEnergyEnum;
import mart.solar.init.ModItems;
import mart.solar.items.ItemPendant;
import mart.solar.particle.ParticleUtil;
import mart.solar.util.RgbColor;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.SolarUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class TileEntityMenhir extends TileBase implements ITickable{

  private IEnergyEnum type = null;

  @Override
  public boolean activate(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull IBlockState state, @Nonnull EntityPlayer player, @Nonnull EnumHand hand, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ) {
    ItemStack heldItem = player.getHeldItem(hand);
    if(heldItem.getItem() == ModItems.blank_rune){
      checkSurroundingBlocks(world, pos);
      chargeRune(player, heldItem, hand);
      return true;
    }

    if(heldItem.getItem() == ModItems.pendant){
      if(type == null){
        return true;
      }

      ItemPendant item = ItemPendant.getPendant(type);
      player.setHeldItem(hand, new ItemStack(item));
      this.type = null;
      checkSurroundingBlocks(world, pos);
      return true;
    }

    if(heldItem.getItem() instanceof ItemPendant){
      IEnergyEnum energy = ItemPendant.getEnergyType((ItemPendant) heldItem.getItem());
      this.type = energy;
      player.setHeldItem(hand, new ItemStack(ModItems.pendant));
      return true;
    }
    return super.activate(world, pos, state, player, hand, side, hitX, hitY, hitZ);
  }

  @Override
  public void update() {
    if(type == null){
      return;
    }
    if(this.getWorld().isRemote){
      RgbColor rgbColor = RgbColorUtil.getRuneColor(this.type);
      for(int i = 0; i < 5; i++){
        ParticleUtil.spawnParticleSolar(world, getPos().getX() + Util.rand.nextFloat(), getPos().getY() + (Util.rand.nextInt(300) / 100f), getPos().getZ() + Util.rand.nextFloat(), 0,0,0,
            rgbColor.getRed(), rgbColor.getGreen(), rgbColor.getBlue(), 1, 1, 100);

      }
    }
  }

  @Override
  public NBTTagCompound writeToNBT(NBTTagCompound tag) {
    super.writeToNBT(tag);
    if(type != null){
      tag.setString("type", this.type.getName());
    }
    return tag;
  }

  @Override
  public void readFromNBT(NBTTagCompound compound) {
    this.type = EnumEnergy.getFromName(compound.getString("type"));
    super.readFromNBT(compound);
  }

  private void chargeRune(EntityPlayer player, ItemStack heldItem, EnumHand hand){
    if(this.type == null){
      return;
    }
    heldItem.shrink(1);
    if(heldItem.getCount() == 0){
      player.setHeldItem(hand, ItemStack.EMPTY);
    }

    player.addItemStackToInventory(new ItemStack(SolarUtil.elementRunes.get(this.type)));
  }

  private void checkSurroundingBlocks(World world, BlockPos pos){
    Map<IEnergyEnum, Integer> elements = new HashMap<>();
    for(int x = -2; x < 3; x++){
      for(int y = -2; y < 3; y++){
        for(int z = -2; z < 3; z++){
          IEnergyEnum type = SolarUtil.elementBlocks.get(world.getBlockState(pos.add(x, y, z)).getBlock());
          if(type != null){
            elements.merge(type, 1, (a, b) -> a + b);
          }
        }
      }
    }

    Map.Entry<IEnergyEnum, Integer> highestType = null;
    for(Map.Entry<IEnergyEnum, Integer> entry : elements.entrySet()){
      if(entry.getValue() > 5){
        if(highestType == null || entry.getValue() > highestType.getValue()){
          highestType = entry;
        }
      }
    }

    if(highestType != null){
      this.type = highestType.getKey();
    }
    else{
      this.type = null;
    }

    if(!world.isRemote){
      markDirty();
      PacketHandler.INSTANCE.sendToAll(new MessageTEUpdate(TileEntityMenhir.this.getUpdateTag()));
    }
  }

  public IEnergyEnum getType() {
    return type;
  }
}
