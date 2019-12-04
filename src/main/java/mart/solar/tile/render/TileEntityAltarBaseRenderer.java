package mart.solar.tile.render;

import com.mojang.blaze3d.platform.GlStateManager;
import mart.solar.tile.AltarBaseTile;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraft.entity.item.ItemEntity;

public class TileEntityAltarBaseRenderer extends TileEntityRenderer<AltarBaseTile> {

    @Override
    public void render(AltarBaseTile te, double x, double y, double z, float partialTicks, int destroyStage) {
        te.getHandler().ifPresent(inventory ->{
            if(!inventory.getStackInSlot(0).isEmpty()){
                System.out.println(inventory.getStackInSlot(0));
                GlStateManager.pushMatrix();
                ItemEntity item = new ItemEntity(Minecraft.getInstance().world, x, y, z, inventory.getStackInSlot(0));
                GlStateManager.translatef((float)x + 0.0f, (float)y + 0.8125f, (float)z + 0.0f);
                //GlStateManager.rotatef(180, 0.0F, 1.0F, 1);
                Minecraft.getInstance().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
                GlStateManager.popMatrix();
            }
        });

        te.getHandler().ifPresent(inventory ->{
            if(!inventory.getStackInSlot(1).isEmpty()){
                GlStateManager.pushMatrix();
                ItemEntity item = new ItemEntity(Minecraft.getInstance().world, x, y, z, inventory.getStackInSlot(1));
                GlStateManager.translatef((float)x + 0.5f, (float)y + 0.8125f, (float)z + 0.2f);
                //GlStateManager.rotatef(180, 0.0F, 1.0F, 1);
                Minecraft.getInstance().getRenderManager().renderEntity(item, 0, 0, 0, 0, 0, true);
                GlStateManager.popMatrix();
            }
        });
        super.render(te, x, y, z, partialTicks, destroyStage);
    }
}