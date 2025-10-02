package de.saschat.cmos.fabric.client;

import de.saschat.cmos.ExposureComputerModClient;
import net.fabricmc.api.ClientModInitializer;

public final class ExposureComputerModFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        // This entrypoint is suitable for setting up client-specific logic, such as rendering.
        ExposureComputerModClient.init();
    }
}
