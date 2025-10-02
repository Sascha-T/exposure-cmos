package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.blocks.DigitizerBlock;
import de.saschat.cmos.blocks.StandControllerBlock;
import de.saschat.cmos.blocks.tiles.DigitizerTile;
import de.saschat.cmos.blocks.tiles.StandControllerTile;
import de.saschat.cmos.blocks.tiles.WirelessReceiverTile;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.Set;

public class TileRegistry {
    public static final DeferredRegister<BlockEntityType<?>> TILES = DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE);

    public static final RegistrySupplier<BlockEntityType<DigitizerTile>> DIGITIZER_TILE = TILES.register("digitizer", () -> new BlockEntityType<>(
            DigitizerTile::new,
            Set.of(BlockRegistry.DIGITIZER_BLOCK.get()),
            null
    ));
    public static final RegistrySupplier<BlockEntityType<StandControllerTile>> STAND_CONTROLLER_TILE = TILES.register("stand_controller", () -> new BlockEntityType<>(
            StandControllerTile::new,
            Set.of(BlockRegistry.STAND_CONTROLLER_BLOCK.get()),
            null
    ));
    public static final RegistrySupplier<BlockEntityType<WirelessReceiverTile>> WIRELESS_RECEIVER_TILE = TILES.register("wireless_receiver", () -> new BlockEntityType<>(
            WirelessReceiverTile::new,
            Set.of(BlockRegistry.WIRELESS_RECEIVER_BLOCK.get()),
            null
    ));

    public static void register() {
        TILES.register();
    }
}
