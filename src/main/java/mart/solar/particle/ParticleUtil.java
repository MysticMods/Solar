package mart.solar.particle;

import java.util.Random;

import epicsquid.mysticallib.LibRegistry;
import epicsquid.mysticallib.MysticalLib;
import epicsquid.mysticallib.proxy.ClientProxy;
import mart.solar.init.ModParticles;
import net.minecraft.client.Minecraft;
import net.minecraft.world.World;

public class ParticleUtil {
  public static Random random = new Random();
  public static int counter = 0;

  public static void spawnParticleGlow(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale,
      int lifetime) {
    if (MysticalLib.proxy instanceof ClientProxy) {
      counter += random.nextInt(3);
      if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
        ClientProxy.particleRenderer.spawnParticle(world, LibRegistry.PARTICLE_GLOW, x, y, z, vx, vy, vz, lifetime, r, g, b, a, scale, 1);
      }
    }
  }

  public static void spawnParticleSolar(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale,
      int lifetime) {
    if (MysticalLib.proxy instanceof ClientProxy) {
      counter += random.nextInt(3);
      if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
        ClientProxy.particleRenderer.spawnParticle(world, ModParticles.PARTICLE_SOLAR, x, y, z, vx, vy, vz, lifetime, r, g, b, a, scale);
      }
    }
  }

  public static void spawnParticleSolarLine(World world, float x, float y, float z, float vx, float vy, float vz, float r, float g, float b, float a, float scale,
      int lifetime) {
    if (MysticalLib.proxy instanceof ClientProxy) {
      counter += random.nextInt(3);
      if (counter % (Minecraft.getMinecraft().gameSettings.particleSetting == 0 ? 1 : 2 * Minecraft.getMinecraft().gameSettings.particleSetting) == 0) {
        ClientProxy.particleRenderer.spawnParticle(world, ModParticles.PARTICLE_SOLAR_LINE, x, y, z, vx, vy, vz, lifetime, r, g, b, a, scale);
      }
    }
  }
}
