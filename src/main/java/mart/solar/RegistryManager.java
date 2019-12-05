package mart.solar;

import mart.solar.setup.ModBlocks;
import mart.solar.setup.ModParticles;
import mart.solar.tile.ITile;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.particles.ParticleType;
import net.minecraft.tileentity.TileEntityType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = Solar.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class RegistryManager {

    private static List<Block> blocks = new ArrayList<>();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        blocks.add(ModBlocks.ALTAR_BASE.get());
        blocks.add(ModBlocks.ALTAR.get());

    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
    }

    @SubscribeEvent
    public static void onTileRegistry(final RegistryEvent.Register<TileEntityType<?>> event){
        for(Block block : blocks){
            if(block instanceof ITile){
                event.getRegistry().register((TileEntityType<?>) TileEntityType.Builder.create(((ITile) block).getTile(), block).build(null)
                        .setRegistryName(new ResourceLocation(Solar.MODID, Objects.requireNonNull(block.getRegistryName()).getPath() + "_tile")));
                System.out.println("REGISTERINGNGNG");
            }
        }
    }

    @SubscribeEvent
    public static void registerParticles(RegistryEvent.Register<ParticleType<?>> evt) {
        evt.getRegistry().register(ModParticles.ENERGY);
    }

//  @SubscribeEvent
//  public void init(@Nonnull RegisterContentEvent event) {
//    LibRegistry.setActiveMod(Solar.MODID, Solar.CONTAINER);
//
//    ModBlocks.registerBlocks(event);
//
//    ModItems.registerItems(event);
//
//    ModEntities.registerMobs();
//    ModEntities.registerMobSpawn();
//    PacketHandler.registerMessages();
//  }
//
//  @SubscribeEvent
//  public void initRecipes(@Nonnull RegisterModRecipesEvent event) {
//    LibRegistry.setActiveMod(Solar.MODID, Solar.CONTAINER);
//
//    ModRecipes.initRecipes(event);
//  }
//
//  @SideOnly(Side.CLIENT)
//  @SubscribeEvent
//  public void onRegisterCustomModels(@Nonnull RegisterParticleEvent event) {
//    ModParticles.init();
//  }
//
//  @SubscribeEvent(priority = EventPriority.LOWEST)
//  public void registerOredict(@Nonnull RegistryEvent.Register<Item> event) {
//    LibRegistry.setActiveMod(Solar.MODID, Solar.CONTAINER);
//
//    ModItems.registerOredict();
//  }
}
