package mart.solar.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TileEntityAltarBaseRenderer extends TileEntitySpecialRenderer<TileEntityAltarBase> {

  @Override
  public void render(TileEntityAltarBase te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
    if(!te.inventory.getStackInSlot(0).isEmpty()){
      GlStateManager.pushMatrix();
      EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, te.inventory.getStackInSlot(0));
      item.hoverStart = 0;
      GlStateManager.translate(x + 0.5, y + 0.8125, z + 0.25);
      GlStateManager.rotate(180, 0.0F, 1.0F, 1);
      Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
      GlStateManager.popMatrix();
    }
    if(!te.inventory.getStackInSlot(1).isEmpty()){
      GlStateManager.pushMatrix();
      EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, te.inventory.getStackInSlot(1));
      item.hoverStart = 0;
      GlStateManager.translate(x + 0.5, y + 0.8125, z - 0.2);
      GlStateManager.rotate(180, 0.0F, 1.0F, 1);
      Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
      GlStateManager.popMatrix();
    }
    super.render(te, x, y, z, partialTicks, destroyStage, alpha);
  }
}
