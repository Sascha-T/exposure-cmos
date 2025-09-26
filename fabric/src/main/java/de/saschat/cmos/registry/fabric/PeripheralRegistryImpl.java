package de.saschat.cmos.registry.fabric;

import dan200.computercraft.api.peripheral.PeripheralLookup;
import de.saschat.cmos.registry.PeripheralRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.world.level.block.Block;

import java.util.Map;

public class PeripheralRegistryImpl {
    public static void registerPlatform() {
        for (Map.Entry<RegistrySupplier<? extends Block>, PeripheralRegistry.PeripheralEntry> pair : PeripheralRegistry.freeze().entrySet()) {
            PeripheralLookup.get().registerForBlockEntity(pair.getValue().supplier()::get, pair.getValue().tile().get());
        }
    }
}
