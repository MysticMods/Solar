package mart.solar.tile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;

public class TileEntityChargingPedestalRenderer extends TileEntitySpecialRenderer<TileEntityChargingPedestal> {

    @Override
    public void render(TileEntityChargingPedestal te, double x, double y, double z, float partialTicks, int destroyStage, float alpha) {
        if(!te.inventory.getStackInSlot(0).isEmpty()){
            GlStateManager.pushMatrix();
            EntityItem item = new EntityItem(Minecraft.getMinecraft().world, x, y, z, te.inventory.getStackInSlot(0));
            item.hoverStart = 0;
            GlStateManager.translate(x + 0.5, y + 0.9375, z );
            GlStateManager.rotate(180, 0.0F, 1.0F, 1);
            Minecraft.getMinecraft().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
            GlStateManager.popMatrix();
        }
        super.render(te, x, y, z, partialTicks, destroyStage, alpha);
    }
}