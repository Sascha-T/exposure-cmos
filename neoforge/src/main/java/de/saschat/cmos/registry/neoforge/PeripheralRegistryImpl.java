package de.saschat.cmos.registry.neoforge;

import com.mojang.logging.LogUtils;
import dan200.computercraft.api.ForgeComputerCraftAPI;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.PeripheralCapability;
import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.registry.PeripheralRegistry;
import dev.architectury.platform.hooks.EventBusesHooks;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.Block;
import net.neoforged.fml.loading.moddiscovery.locators.JarInJarDependencyLocator;
import net.neoforged.neoforge.capabilities.BlockCapability;
import net.neoforged.neoforge.capabilities.RegisterCapabilitiesEvent;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class PeripheralRegistryImpl {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static void registerPlatform() {
        EventBusesHooks.getModEventBus(ExposureComputerMod.MOD_ID).get().addListener(PeripheralRegistryImpl::registerCap);
    }

    public static void registerCap(RegisterCapabilitiesEvent event) {
        for (Map.Entry<RegistrySupplier<? extends Block>, PeripheralRegistry.PeripheralEntry> pair : PeripheralRegistry.freeze().entrySet()) {
            LOGGER.info("Registering block entity capability for {}", pair.getValue().peripheral().getName());
            event.registerBlockEntity(PeripheralCapability.get(), pair.getValue().tile().get(), pair.getValue().supplier()::get);
        }
    }

}
