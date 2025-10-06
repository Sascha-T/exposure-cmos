package de.saschat.cmos.registry;

import de.saschat.cmos.gui.DigitizerMenu;
import de.saschat.cmos.gui.DigitizerScreen;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ScreenRegistry {
    public static void register() {
        ScreenRegistry.registerScreenFactory(GuiRegistry.DIGITIZER_MENU, DigitizerScreen::new);
    }

    @ExpectPlatform
    public static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(RegistrySupplier<MenuType<H>> type, MenuRegistry.ScreenFactory<H, S> factory) {
        throw new AssertionError();
    }
}
