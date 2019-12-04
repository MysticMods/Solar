package mart.solar.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.RenderItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.item.ItemBlock;

public class TileEntityAltarRenderer extends TileEntitySpecialRenderer<TileEntityAltar> {

  @Override
  public void render(TileEntityAltar te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    if(!te.inventory.getStackInSlot(0).isEmpty()){
      GlStateManager.pushMatrix();
      GlStateManager.translate(x, y, z);
      RenderItem itemRenderer = Minecraft.getMinecraft().getRenderItem();
      GlStateManager.translate(0.5, 1.1, 0.5);
      GlStateManager.scale(0.5f, 0.5f, 0.5f);
      if(!(te.inventory.getStackInSlot(0).getItem() instanceof ItemBlock)){
        GlStateManager.rotate(180, 0.0F, 1.0F, 1);
      }
      itemRenderer.renderItem(te.inventory.getStackInSlot(0), ItemCameraTransforms.TransformType.FIXED);
      GlStateManager.popMatrix();
    }
    super.render(te, x, y, z, partialTicks, destroyStage, alpha);
  }
}