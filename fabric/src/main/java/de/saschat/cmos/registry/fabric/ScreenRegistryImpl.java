package de.saschat.cmos.registry.fabric;

import de.saschat.cmos.registry.ScreenRegistry;
import dev.architectury.event.events.client.ClientLifecycleEvent;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

import java.util.ArrayList;
import java.util.List;

public class ScreenRegistryImpl {
    private static List<Runnable> runnables = new ArrayList<>();

    public static void registerPlatform() {
        ClientLifecycleEvent.CLIENT_SETUP.register(ScreenRegistryImpl::clientSetup);
    }

    private static void clientSetup(Minecraft minecraft) {
        ScreenRegistry.register();

        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }

    public static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(RegistrySupplier<MenuType<? extends H>> type, MenuRegistry.ScreenFactory<H, S> factory) {
        runnables.add(() -> {
            MenuScreens.register(type.get(), factory::create);
        });
    }
}
