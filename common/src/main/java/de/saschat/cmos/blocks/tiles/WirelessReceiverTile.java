package de.saschat.cmos.blocks.tiles;

import de.saschat.cmos.blocks.peripheral.StandControllerPeripheral;
import de.saschat.cmos.blocks.peripheral.WirelessReceiverPeripheral;
import de.saschat.cmos.registry.TileRegistry;
import de.saschat.cmos.util.WeakContainer;
import io.github.mortuusars.exposure.world.camera.frame.Frame;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class WirelessReceiverTile extends BlockEntity {
    public WeakContainer<WirelessReceiverPeripheral> peripherals = new WeakContainer<>();
    public WirelessReceiverTile(BlockPos blockPos, BlockState blockState) {
        super(TileRegistry.WIRELESS_RECEIVER_TILE.get(), blockPos, blockState);
    }

    public void receiveFrame(Frame frame) {
        peripherals.operate(x -> x.receive(frame));
    }
}
