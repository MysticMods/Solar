package mart.solar.crafting;

import java.util.HashMap;
import java.util.Map;

import mart.solar.enums.EnumEnergy;
import mart.solar.init.ModBlocks;
import mart.solar.init.ModItems;
import mart.solar.util.RgbColorUtil;
import mart.solar.util.TimeEnum;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class AltarCraftingRecipeRegistry {
  private static Map<String, AltarCraftingRecipe> ritualRegistry = new HashMap<>();

  public static AltarCraftingRecipe ancient_lens, celestial_hoe, infused_silver;

  public static AltarCraftingRecipe getRitual(ItemStack triggerItem) {
    for (int i = 0; i < ritualRegistry.size(); i++) {
      AltarCraftingRecipe recipe = ritualRegistry.values().toArray(new AltarCraftingRecipe[ritualRegistry.size()])[i];
      if (recipe.getTriggerItem().isItemEqual(triggerItem)) {
        return recipe;
      }
    }
    return null;
  }

  public static void init() {
    ritualRegistry.put("ancient_lens", ancient_lens = new AltarCraftingRecipe(
        new ItemStack(Blocks.GLASS), new ItemStack(ModBlocks.ancient_lens), TimeEnum.DAY,
        new ItemStack(Items.GOLD_NUGGET), new ItemStack(Items.GOLD_NUGGET), new ItemStack(Items.GOLD_NUGGET))
        .addColor(Items.GOLD_NUGGET, RgbColorUtil.SOLAR).addColor(ItemBlock.getItemFromBlock(Blocks.GLASS), RgbColorUtil.WIND)
    );

    ritualRegistry.put("celestial_hoe", celestial_hoe = new AltarCraftingRecipe(
      new ItemStack(Items.GOLDEN_HOE), new ItemStack(ModItems.celestial_hoe), TimeEnum.BOTH
    ).addEnergy(EnumEnergy.SOLAR, 1).addEnergy(EnumEnergy.LUNAR, 1).addEnergy(EnumEnergy.EARTH, 1));


  }
}
