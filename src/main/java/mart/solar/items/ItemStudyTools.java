package mart.solar.items;

import epicsquid.mysticallib.item.ItemBase;
import mart.solar.Solar;
import mart.solar.init.ModItems;
import mart.solar.util.SolarUtil;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

public class ItemStudyTools extends ItemBase {

    public ItemStudyTools(@Nonnull String name) {
        super(name);
        setMaxStackSize(1);

    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add("I should use this to study the day and night");
        tooltip.add("- Right click to study");
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        NBTTagCompound tag = stack.getTagCompound();
        if(tag == null){
            tag = new NBTTagCompound();
        }
        if(SolarUtil.isDay(worldIn)){
            if(!tag.hasKey("day")){
                tag.setBoolean("day", true);
            }
        }
        else{
            if(!tag.hasKey("night")){
                tag.setBoolean("night", true);
            }
        }
        stack.setTagCompound(tag);

        if(tag.hasKey("day") && tag.hasKey("night")){
            ItemStack book = new ItemStack(ModItems.guide_book);
            NBTTagCompound bookTag = new NBTTagCompound();
            bookTag.setString("patchouli:book", "solar:journal");
            book.setTagCompound(bookTag);
            playerIn.setHeldItem(handIn, book );

        }
        return super.onItemRightClick(worldIn, playerIn, handIn);
    }


}
