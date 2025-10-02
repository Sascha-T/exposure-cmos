package de.saschat.cmos.registry;

import de.saschat.cmos.ExposureComputerMod;
import de.saschat.cmos.blocks.DigitizerBlock;
import de.saschat.cmos.blocks.StandControllerBlock;
import de.saschat.cmos.blocks.WirelessReceiverBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class BlockRegistry {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ExposureComputerMod.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<DigitizerBlock> DIGITIZER_BLOCK = BLOCKS.register("digitizer", () -> new DigitizerBlock(
            BlockBehaviour.Properties.of()
    ));
    public static final RegistrySupplier<StandControllerBlock> STAND_CONTROLLER_BLOCK = BLOCKS.register("stand_controller", () -> new StandControllerBlock(
            BlockBehaviour.Properties.of()
    ));
    public static final RegistrySupplier<WirelessReceiverBlock> WIRELESS_RECEIVER_BLOCK = BLOCKS.register("wireless_receiver", () -> new WirelessReceiverBlock(
            BlockBehaviour.Properties.of()
    ));

    public static void register() {
        BLOCKS.register();
    }
}
