package mart.solar.tile.render;

import com.mojang.blaze3d.platform.GlStateManager;
import mart.solar.tile.AltarBaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.ItemStackTileEntityRenderer;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public class TileEntityAltarBaseRenderer extends TileEntityRenderer<AltarBaseTile> {

   private ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();

    @Override
    public void render(AltarBaseTile te, double x, double y, double z, float partialTicks, int destroyStage) {
        te.getHandler().ifPresent(inventory ->{
            if(!inventory.getStackInSlot(0).isEmpty()){
                GlStateManager.pushMatrix();
                GlStateManager.translatef((float)x + 0.5f, (float)y + 0.8125f, (float)z + 0.25f);
                GlStateManager.rotatef(180, 0.0F, 1.0F, 1);
                GlStateManager.scalef(0.5f, 0.5f, 0.5f);
                renderer.renderItem(inventory.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }
        });

        te.getHandler().ifPresent(inventory ->{
            if(!inventory.getStackInSlot(1).isEmpty()){
                GlStateManager.pushMatrix();
                GlStateManager.translatef((float)x + 0.5f, (float)y + 0.8125f, (float)z + 0.75f);
                GlStateManager.rotatef(180, 0.0F, 1.0F, 1);
                GlStateManager.scalef(0.5f, 0.5f, 0.5f);
                renderer.renderItem(inventory.getStackInSlot(1), ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }
        });
        super.render(te, x, y, z, partialTicks, destroyStage);
    }
}