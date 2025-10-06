package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.gui.DigitizerMenu;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.inventory.MenuType;

public class GuiRegistry {
    public static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.MENU);
    public static final RegistrySupplier<MenuType<DigitizerMenu>> DIGITIZER_MENU =  MENUS.register("digitizer_menu", () -> new MenuType<DigitizerMenu>(DigitizerMenu::new, FeatureFlagSet.of()));

    public static void register() {
        MENUS.register();
    }


}
