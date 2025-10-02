package de.saschat.cmos.registry;

import dan200.computercraft.api.peripheral.IPeripheral;
import de.saschat.cmos.blocks.peripheral.DigitizerPeripheral;
import de.saschat.cmos.blocks.peripheral.StandControllerPeripheral;
import de.saschat.cmos.blocks.peripheral.WirelessReceiverPeripheral;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class PeripheralRegistry {
    private static final Map<RegistrySupplier<? extends Block>, PeripheralEntry> MAP = new HashMap<>();
    private static boolean frozen = false;
    public static void register(RegistrySupplier<? extends Block> block, RegistrySupplier<? extends BlockEntityType> blockEntity, Class<?> clazz, PeripheralSupplier supplier) {
        if(frozen) throw new RuntimeException("Frozen registry register attempt");
        MAP.put(block, new PeripheralEntry(clazz, supplier, blockEntity));
    }

    static {
        register(BlockRegistry.DIGITIZER_BLOCK, TileRegistry.DIGITIZER_TILE, DigitizerPeripheral.class, DigitizerPeripheral::new);
        register(BlockRegistry.STAND_CONTROLLER_BLOCK, TileRegistry.STAND_CONTROLLER_TILE, StandControllerPeripheral.class, StandControllerPeripheral::new);
        register(BlockRegistry.WIRELESS_RECEIVER_BLOCK, TileRegistry.WIRELESS_RECEIVER_TILE, WirelessReceiverPeripheral.class, WirelessReceiverPeripheral::new);
    }
    public static Map<RegistrySupplier<? extends Block>, PeripheralEntry> freeze() {
        frozen = true;
        return Collections.unmodifiableMap(MAP);
    }

    public static void register() {
        registerPlatform();
    }
    @ExpectPlatform
    private static void registerPlatform() {
        throw new AssertionError();
    }
    public interface PeripheralSupplier {
        IPeripheral get(BlockEntity ent, Direction dir);
    }
    public record PeripheralEntry(Class<?> peripheral, PeripheralSupplier supplier, RegistrySupplier<? extends BlockEntityType> tile) {

    }
}
