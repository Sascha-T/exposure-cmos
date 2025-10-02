package de.saschat.cmos.util;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import io.netty.buffer.ByteBuf;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.Level;

public record Location(BlockPos pos, ResourceKey<Level> level) {
    public static final Codec<Location> CODEC = RecordCodecBuilder.create(instance ->
            instance.group(
                    BlockPos.CODEC.fieldOf("pos").forGetter(Location::pos),
                    ResourceKey.codec(Registries.DIMENSION).fieldOf("level").forGetter(Location::level)
            ).apply(instance, Location::new)
    );
    public static final StreamCodec<? super RegistryFriendlyByteBuf, Location> STREAM_CODEC = StreamCodec.composite(
            BlockPos.STREAM_CODEC, Location::pos,
            ResourceKey.streamCodec(Registries.DIMENSION), Location::level,
            Location::new
    );
}
