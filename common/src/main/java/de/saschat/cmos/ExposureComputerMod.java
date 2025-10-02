package de.saschat.cmos;

import com.mojang.logging.LogUtils;
import de.saschat.cmos.registry.*;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import io.github.mortuusars.exposure.client.camera.viewfinder.ViewfinderRegistry;
import net.minecraft.client.Minecraft;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.block.entity.ChestBlockEntity;
import org.slf4j.Logger;

public final class ExposureComputerMod {
    private static final Logger LOGGER = LogUtils.getLogger();
    public static final String MOD_ID = "cmosexposure";
    public static MinecraftServer SERVER;

    public static void init() {
        // Write common init code here.

        BlockRegistry.register();
        TileRegistry.register();
        ItemRegistry.register();
        PeripheralRegistry.register();
        TabRegistry.register();
        GuiRegistry.register();
        ComponentRegistry.register();

        LifecycleEvent.SERVER_STARTING.register(event -> ExposureComputerMod.SERVER = event);

    }

}
