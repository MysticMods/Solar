package mart.solar.tile.render;

import com.mojang.blaze3d.platform.GlStateManager;
import mart.solar.tile.AltarTile;
import mart.solar.util.SolarUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.model.ItemCameraTransforms;
import net.minecraft.client.renderer.tileentity.TileEntityRenderer;
import net.minecraftforge.items.CapabilityItemHandler;

import java.util.HashMap;
import java.util.Map;

public class AltarRenderer extends TileEntityRenderer<AltarTile> {

    private ItemRenderer renderer = Minecraft.getInstance().getItemRenderer();
    private Map<Integer, Float> itemPositions = new HashMap<>();

    private static final float SPEED = (float)(Math.PI) * 2 * 2 / 180;

    private int radius = 1;

    public AltarRenderer(){
        itemPositions.put(0, 0f);
        itemPositions.put(1, (float) Math.PI);
        itemPositions.put(2, (float) Math.PI / 3);
        itemPositions.put(3, (float) Math.PI + (float)(Math.PI / 3));
        itemPositions.put(4, (float) Math.PI / 3 * 2);
        itemPositions.put(5, (float) Math.PI / 3 * 5);
    }

    @Override
    public void render(AltarTile te, double x, double y, double z, float partialTicks, int destroyStage) {
        te.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY).ifPresent(inventory ->{
            int slots = SolarUtil.getFilledSlots(inventory);

            for(int i = 0; i < slots; i++){
                GlStateManager.pushMatrix();
                GlStateManager.translatef(
                        (float)x + 0.5f + (float)(Math.cos(((te.getWorld().getGameTime()+partialTicks) * SPEED) + itemPositions.get(i)) * te.getItemRadius()),
                        (float)y + 1 + ((te.getInitTicks() + partialTicks) / 40),
                        (float)z + 0.5f + (float)(Math.sin(((te.getWorld().getGameTime()+partialTicks) * SPEED) + itemPositions.get(i)) * te.getItemRadius())
                );

                if(te.getState() != AltarTile.AltarState.INIT){
                    GlStateManager.translatef(0, -(partialTicks / 40), 0);
                }

                GlStateManager.scalef(0.3f, 0.3f, 0.3f);
                renderer.renderItem(inventory.getStackInSlot(i), ItemCameraTransforms.TransformType.FIXED);
                GlStateManager.popMatrix();
            }

        });

        super.render(te, x, y, z, partialTicks, destroyStage);
    }
}
