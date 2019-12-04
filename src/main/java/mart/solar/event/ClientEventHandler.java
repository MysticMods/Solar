package mart.solar.event;

import baubles.api.BaublesApi;
import mart.solar.Solar;
import mart.solar.capability.energycapability.EnergyCapabilityProvider;
import mart.solar.capability.energycapability.IEnergyCapability;
import mart.solar.init.ModItems;
import mart.solar.items.ItemSolarRing;
import mart.solar.items.interfaces.IEnergyItem;
import mart.solar.util.RgbColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.opengl.GL11;

@Mod.EventBusSubscriber(modid = Solar.MODID)
public class ClientEventHandler {

    private final static ResourceLocation solarRing = new ResourceLocation(Solar.MODID, "textures/items/solar_ring.png");
    private final static ResourceLocation lunarRing = new ResourceLocation(Solar.MODID, "textures/items/lunar_ring.png");

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public static void onPreRender(RenderGameOverlayEvent.Pre e) {
        Minecraft mc = Minecraft.getMinecraft();
        EntityPlayerSP player = mc.player;
        if (player == null)
            return;    // just in case

        if (e.getType() == RenderGameOverlayEvent.ElementType.HOTBAR) {
            int slot = BaublesApi.isBaubleEquipped(player, ModItems.solar_ring);
            if(slot != -1){
                ItemStack ring = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
                IEnergyItem item = (IEnergyItem) ring.getItem();
                IEnergyCapability capability = ring.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);


                float drawHeight = capability.getEnergyAmount() / item.getMaxCharge();
                renderRingOverlay(mc, e, solarRing, drawHeight, 0);
            }

            slot = BaublesApi.isBaubleEquipped(player, ModItems.lunar_ring);
            if(slot != -1){
                ItemStack ring = BaublesApi.getBaublesHandler(player).getStackInSlot(slot);
                IEnergyItem item = (IEnergyItem) ring.getItem();
                IEnergyCapability capability = ring.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);

                float drawHeight = capability.getEnergyAmount() / item.getMaxCharge();
                renderRingOverlay(mc, e, lunarRing, drawHeight, 1);
            }

            if(player.getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof IEnergyItem){
                ItemStack stack = player.getHeldItem(EnumHand.MAIN_HAND);
                ResourceLocation itemLocation = new ResourceLocation(stack.getItem().getRegistryName().getResourceDomain(), "textures/items/"+ stack.getItem().getRegistryName().getResourcePath() + ".png");

                IEnergyItem item = (IEnergyItem) stack.getItem();
                IEnergyCapability capability = stack.getCapability(EnergyCapabilityProvider.ENERGY_CAPABILITY, null);

                float drawHeight = capability.getEnergyAmount() / item.getMaxCharge();

                renderRingOverlay(mc, e, itemLocation, drawHeight, 2);
            }
        }
    }

    private static void renderRingOverlay(Minecraft mc, RenderGameOverlayEvent.Pre e, ResourceLocation location, float drawHeight, int type){
        float screenWidth = e.getResolution().getScaledWidth();
        float screenHeight = e.getResolution().getScaledHeight();

        float normalWidth = 960;
        float normalHeight = 501;

        float scaleX = screenWidth / normalWidth;
        float scaleY = screenHeight / normalHeight;

        float x = (screenWidth / 2) + 92;
        float y = (screenHeight);

        if(type == 1){
            x += 22;
        }
        else if(type == 2){
            x += 44;
        }

        float width = 16 / scaleX;
        float height = 16 / scaleY;

        Tessellator.getInstance().getBuffer();
        // save the current state of OpenGL to restore it later
        GlStateManager.pushMatrix();
        GlStateManager.disableDepth();
        GlStateManager.depthMask(false);

        // set color to black
        GlStateManager.color(255, 255, 255, 0.5f);
        GlStateManager.translate(x, y, -90);
        GlStateManager.scale(scaleX, scaleY, 1);
        GlStateManager.scale(1.4, 1.4, 1);
        mc.renderEngine.bindTexture(location);
        drawSprite(width, height, 1);
        GlStateManager.color(255, 255, 255, 1f);
        drawSprite(width, height, drawHeight);

        // restore the state of OpenGL
        GlStateManager.depthMask(true);
        GlStateManager.enableDepth();

        GlStateManager.popMatrix();
    }

    private static void drawSprite(float width, float height, float drawHeight){
        //System.out.println(drawHeight);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();

        //Draw overlay
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);

        double texHeight = 1-drawHeight;
        float drawPositionHeight = height * drawHeight;

        //LOW-LEFT
        bufferbuilder.pos(0, 0, 0).tex(0.0D, 1D).endVertex();
        //LOW RIGHT
        bufferbuilder.pos(width, 0, 0).tex(1.0D, 1.0D).endVertex();
        //HIGH RIGHT
        bufferbuilder.pos(width, -drawPositionHeight, 0).tex(1.0D, texHeight).endVertex();
        //HIGH LEFT
        bufferbuilder.pos(0, -drawPositionHeight, 0).tex(0.0D, texHeight).endVertex();
        GlStateManager.alphaFunc(GL11.GL_GREATER, 0.1f);
        tessellator.draw();
    }
}