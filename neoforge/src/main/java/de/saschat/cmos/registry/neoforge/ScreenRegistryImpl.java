package de.saschat.cmos.registry.neoforge;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.registry.ScreenRegistry;
import dev.architectury.platform.hooks.EventBusesHooks;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

public class ScreenRegistryImpl {
    private static List<Consumer<RegisterMenuScreensEvent>> consumers = new ArrayList<>();

    public static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(RegistrySupplier<MenuType<? extends H>> type, MenuRegistry.ScreenFactory<H, S> factory) {
        consumers.add(event -> {
            System.out.println("Registering screen factory");
            event.register(type.get(), factory::create);
        });
    }
    public static void registerScreen(RegisterMenuScreensEvent event) {
        ScreenRegistry.register();

        for (Consumer<RegisterMenuScreensEvent> consumer : consumers) {
            consumer.accept(event);
        }
    }
}
