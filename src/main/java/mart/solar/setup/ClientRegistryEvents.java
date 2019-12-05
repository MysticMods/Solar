package mart.solar.setup;

import mart.solar.Solar;
import mart.solar.particle.EnergyParticleType;
import mart.solar.tile.AltarBaseTile;
import mart.solar.tile.render.TileEntityAltarBaseRenderer;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(value= {Dist.CLIENT}, modid= Solar.MODID, bus= Mod.EventBusSubscriber.Bus.MOD)
public class ClientRegistryEvents
{
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        ClientRegistry.bindTileEntitySpecialRenderer(AltarBaseTile.class, new TileEntityAltarBaseRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAltar.class, new TileEntityAltarRenderer());
//        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityChargingPedestal.class, new TileEntityChargingPedestalRenderer());
    }

    @SubscribeEvent
    public static void registerFactories(ParticleFactoryRegisterEvent evt) {
        Minecraft.getInstance().particles.registerFactory(ModParticles.ENERGY, new EnergyParticleType.Factory());
    }



}