package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.util.Location;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;

public class ComponentRegistry {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT = DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.DATA_COMPONENT_TYPE);

    public static final RegistrySupplier<DataComponentType<Location>> LOCATION = DATA_COMPONENT.register(ResourceLocation.fromNamespaceAndPath(ExposureComputerMod.MOD_ID, "receiver_location"), () -> {
        return DataComponentType.<Location>builder().networkSynchronized(Location.STREAM_CODEC).persistent(Location.CODEC).build();
    });

    public static void register() {
        DATA_COMPONENT.register();
    }

}
