package de.saschat.cmos.blocks.peripheral;

import dan200.computercraft.api.peripheral.AttachedComputerSet;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import de.saschat.cmos.blocks.tiles.WirelessReceiverTile;
import io.github.mortuusars.exposure.ExposureServer;
import io.github.mortuusars.exposure.world.camera.frame.Frame;
import io.github.mortuusars.exposure.world.level.storage.ExposureData;
import io.github.mortuusars.exposure.world.level.storage.RequestedPalettedExposure;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class WirelessReceiverPeripheral implements IPeripheral {
    WirelessReceiverTile tile;
    public AttachedComputerSet set = new AttachedComputerSet();

    public WirelessReceiverPeripheral(BlockEntity ent, Direction dir) {
        this.tile = (WirelessReceiverTile) ent;
        tile.peripherals.add(this);
    }


    @Override
    public String getType() {
        return "wireless_receiver";
    }

    @Override
    public void attach(IComputerAccess computer) {
        set.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        set.remove(computer);
    }


    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return Objects.equals(this, iPeripheral);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (StandControllerPeripheral) obj;
        return Objects.equals(this.tile, that.tile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tile);
    }

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    public void receive(Frame frame) {
        scheduler.schedule(() -> {
            RequestedPalettedExposure load = ExposureServer.exposureRepository().load(frame.identifier().getId().get());
            ExposureData data = load.getData().get();
            ByteBuffer retData = ByteBuffer.wrap(data.getPixels());


            Map<Integer, Integer> size = new HashMap<>();
            size.put(1, data.getWidth());
            size.put(2, data.getHeight());

            set.queueEvent("wireless_frame", data.getPaletteId(), size, retData);
        }, 1, TimeUnit.SECONDS);
    }
}
