package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.items.CmosCameraItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ItemRegistry {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.ITEM);

    public static final RegistrySupplier<BlockItem> DIGITIZER_BLOCK_ITEM = ITEMS.register("digitizer", () -> new BlockItem(BlockRegistry.DIGITIZER_BLOCK.get(),
            new Item.Properties().arch$tab(TabRegistry.MY_TAB)));
    public static final RegistrySupplier<BlockItem> STAND_CONTROLLER_BLOCK_ITEM = ITEMS.register("stand_controller", () -> new BlockItem(BlockRegistry.STAND_CONTROLLER_BLOCK.get(),
            new Item.Properties().arch$tab(TabRegistry.MY_TAB)));
    public static final RegistrySupplier<BlockItem> WIRELESS_RECEIVER_BLOCK_ITEM = ITEMS.register("wireless_receiver", () -> new BlockItem(BlockRegistry.WIRELESS_RECEIVER_BLOCK.get(),
            new Item.Properties().arch$tab(TabRegistry.MY_TAB)));
    public static final RegistrySupplier<CmosCameraItem> CMOS_CAMERA_ITEM = ITEMS.register("cmos_camera", () -> new CmosCameraItem(
            new Item.Properties().arch$tab(TabRegistry.MY_TAB)));

    public static void register() {
        ITEMS.register();
    }
}
