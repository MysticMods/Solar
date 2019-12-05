package mart.solar.particle.energy;

import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import mart.solar.setup.ModParticles;
import net.minecraft.network.PacketBuffer;
import net.minecraft.particles.IParticleData;
import net.minecraft.particles.ParticleType;

import javax.annotation.Nonnull;
import java.util.Locale;

public class EnergyParticleData implements IParticleData {

    public final float size;
    public final float r, g, b;
    public final float goalX, goalY, goalZ;

    public EnergyParticleData(float size, float r, float g, float b, float goalX, float goalY, float goalZ) {
       this.size = size;
       this.r = r;
       this.g = g;
       this.b = b;
       this.goalX = goalX;
       this.goalY = goalY;
       this.goalZ = goalZ;
    }

    @Override
    public ParticleType<?> getType() {
        return ModParticles.ENERGY;
    }

    @Override
    public void write(PacketBuffer buf) {
        buf.writeFloat(size);
        buf.writeFloat(r);
        buf.writeFloat(g);
        buf.writeFloat(b);
        buf.writeFloat(goalX);
        buf.writeFloat(goalY);
        buf.writeFloat(goalZ);
    }

    @Nonnull
    @Override
    public String getParameters() {
        return String.format(Locale.ROOT, "%s %.2f %.2f %.2f %.2f %s %s %s",
                this.getType().getRegistryName(), this.size, this.r, this.g, this.b, this.goalX, this.goalY, this.goalZ);
    }

    public static final IDeserializer<EnergyParticleData> DESERIALIZER = new IDeserializer<EnergyParticleData>() {
        @Nonnull
        @Override
        public EnergyParticleData deserialize(@Nonnull ParticleType<EnergyParticleData> type, @Nonnull StringReader reader) throws CommandSyntaxException {
            reader.expect(' ');
            float size = reader.readFloat();
            reader.expect(' ');
            float r = reader.readFloat();
            reader.expect(' ');
            float g = reader.readFloat();
            reader.expect(' ');
            float b = reader.readFloat();
            reader.expect(' ');
            float goalX = reader.readFloat();
            reader.expect(' ');
            float goalY = reader.readFloat();
            reader.expect(' ');
            float goalZ = reader.readFloat();
            reader.expect(' ');

            return new EnergyParticleData(size, r, g, b, goalX, goalY, goalZ);
        }

        @Override
        public EnergyParticleData read(@Nonnull ParticleType<EnergyParticleData> type, PacketBuffer buf) {
            return new EnergyParticleData(buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readFloat());
        }
    };
}
