package mart.solar.tile;

import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.item.ItemEntity;

public class TileEntityAltarBaseRenderer extends TileEntityRenderer<TileEntityAltarBase> {

  @Override
  public void render(TileEntityAltarBase te, double x, double y, double z, float partialTicks, int destroyStage) {
    if(!te.inventory.getStackInSlot(0).isEmpty()){
      GlStateManager.pushMatrix();
      ItemEntity item = new ItemEntity(Minecraft.getInstance().world, x, y, z, te.inventory.getStackInSlot(0));
      GlStateManager.translatef((float)x + 0.5f, (float)y + 0.8125f, (float)z + 0.25f);
      GlStateManager.rotatef(180, 0.0F, 1.0F, 1);
      Minecraft.getInstance().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
      GlStateManager.popMatrix();
    }
    if(!te.inventory.getStackInSlot(1).isEmpty()){
      GlStateManager.pushMatrix();
      ItemEntity item = new ItemEntity(Minecraft.getInstance().world, x, y, z, te.inventory.getStackInSlot(1));
      GlStateManager.translatef((float)x + 0.5f, (float)y + 0.8125f, (float)z - 0.2f);
      GlStateManager.rotatef(180, 0.0F, 1.0F, 1);
      Minecraft.getInstance().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
      GlStateManager.popMatrix();
    }
    super.render(te, x, y, z, partialTicks, destroyStage);
  }

}
