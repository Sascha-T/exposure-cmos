package de.saschat.cmos.registry.neoforge;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.registry.GuiRegistry;
import dev.architectury.platform.hooks.EventBusesHooks;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class GuiRegistryImpl {
    private static List<Consumer<RegisterMenuScreensEvent>> consumers = new ArrayList<>();

    public static void registerPlatform() {
        EventBusesHooks.getModEventBus(ExposureComputerMod.MOD_ID).get().addListener(GuiRegistryImpl::registerScreen);
    }

    public static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(RegistrySupplier<MenuType<? extends H>> type, MenuRegistry.ScreenFactory<H, S> factory) {
        consumers.add(event -> {
            event.register(type.get(), factory::create);
        });
    }
    private static void registerScreen(RegisterMenuScreensEvent event) {
        for (Consumer<RegisterMenuScreensEvent> consumer : consumers) {
            consumer.accept(event);
        }
    }
}
