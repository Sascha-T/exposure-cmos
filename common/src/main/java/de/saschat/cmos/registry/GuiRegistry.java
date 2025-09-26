package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.gui.DigitizerMenu;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.menu.MenuRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.gui.screens.inventory.MenuAccess;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import de.saschat.cmos.gui.DigitizerScreen;

import java.util.HashMap;
import java.util.Map;

public class GuiRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<DigitizerMenu>> DIGITIZER_MENU = register(
            MENUS.register("digitizer_menu", () -> new MenuType<DigitizerMenu>(DigitizerMenu::new, FeatureFlagSet.of())),
            DigitizerScreen::new
    );


    public static <T extends AbstractContainerMenu, S extends Screen & MenuAccess<T>> RegistrySupplier<MenuType<T>> register(RegistrySupplier<MenuType<? extends T>> a, MenuRegistry.ScreenFactory<T, S> b) {
        registerScreenFactory(a, b);
        return (RegistrySupplier<MenuType<T>>)(Object) a;
    }

    public static void register() {
        MENUS.register();
        registerPlatform();
    }

    @ExpectPlatform
    public static void registerPlatform() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static <H extends AbstractContainerMenu, S extends Screen & MenuAccess<H>> void registerScreenFactory(RegistrySupplier<MenuType<? extends H>> type, MenuRegistry.ScreenFactory<H, S> factory) {
        throw new AssertionError();
    }

}
