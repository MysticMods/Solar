package mart.solar.network;

import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {

    public static void registerMessages() {
        epicsquid.mysticallib.network.PacketHandler.registerMessage(EnergyCapabilityMessage.MessageHolder.class, EnergyCapabilityMessage.class, Side.CLIENT);
    }
}
