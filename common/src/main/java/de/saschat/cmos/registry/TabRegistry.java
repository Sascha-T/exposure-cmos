package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class TabRegistry {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> MY_TAB = TABS.register(
            "test_tab", // Tab ID
            () -> CreativeTabRegistry.create(
                    Component.translatable("category.cmosexposure"), // Tab Name
                    () -> new ItemStack(ItemRegistry.DIGITIZER_BLOCK_ITEM.get()) // Icon
            )
    );

    public static void register() {
        TABS.register();
    }
}
