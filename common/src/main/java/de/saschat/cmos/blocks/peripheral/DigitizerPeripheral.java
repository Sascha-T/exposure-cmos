package de.saschat.cmos.blocks.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.lua.MethodResult;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IDynamicPeripheral;
import dan200.computercraft.api.peripheral.IPeripheral;
import de.saschat.cmos.blocks.tiles.DigitizerTile;
import de.saschat.cmos.util.CCUtil;
import de.saschat.cmos.util.ExtraDataTizer;
import io.github.mortuusars.exposure.Exposure;
import io.github.mortuusars.exposure.ExposureServer;
import io.github.mortuusars.exposure.util.ExtraData;
import io.github.mortuusars.exposure.world.camera.frame.EntityInFrame;
import io.github.mortuusars.exposure.world.camera.frame.Frame;
import io.github.mortuusars.exposure.world.item.PhotographItem;
import io.github.mortuusars.exposure.world.level.storage.ExposureData;
import io.github.mortuusars.exposure.world.level.storage.RequestedPalettedExposure;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;

import java.nio.ByteBuffer;
import java.util.*;

public class DigitizerPeripheral implements IPeripheral {
    DigitizerTile tile;
    public DigitizerPeripheral(BlockEntity ent, Direction dir) {
        this.tile = (DigitizerTile) ent;
    }

    public List<IComputerAccess> computers = new LinkedList<>();

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        computers.add(computer);
        tile.peripherals.add(this);
    }

    @Override
    public void detach(IComputerAccess computer) {
        IPeripheral.super.detach(computer);
        computers.remove(computer);
        tile.peripherals.remove(this);
    }

    private Frame getFrame() {
        if(!isPresent()) return null;
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        return p.getFrame(tile.getPhotograph());
    }

    @LuaFunction
    public final boolean isPresent() {
        return tile.getPhotograph() != null;

    }
    @LuaFunction
    public final Object[] getIdentifier() {
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        Frame frame;
        if((frame = getFrame()) != null) {
            String data = frame.identifier().getId().orElse(null);
            return new Object[] {data != null, data};
        }
        return new Object[] {false, null};
    }
    @LuaFunction
    public final Object[] getPhotographer() {
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        Frame frame;
        if((frame = getFrame()) != null) {
            String data = frame.photographer().uuid().toString();
            return new Object[] {!frame.photographer().isEmpty(), data, frame.photographer().name()};
        }
        return new Object[] {false, null, null};
    }


    @LuaFunction(value = {"getType"})
    public final Object[] getTypeLua() {
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        Frame frame;
        if((frame = getFrame()) != null) {
            String data = frame.type().getSerializedName();
            return new Object[] {true, data};
        }
        return new Object[] {false, null };
    }

    @LuaFunction
    public final Object[] getEntities() {
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        Frame frame;
        if((frame = getFrame()) != null) {
            var fr = frame.entitiesInFrame();
            Object[] ret = new Object[fr.size() + 1];
            ret[0] = true;

            for (int i = 0; i < fr.size(); i++) {
                ret[i + 1] = buildEntity(fr.get(i));
            }

            return ret;
        }
        return new Object[] {false};
    }

    @LuaFunction
    public final Object[] getExtraData() {
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        Frame frame;
        if((frame = getFrame()) != null) {
            return new Object[] {true, ExtraDataTizer.tizerize(frame)};
        }
        return new Object[] {false, null};
    }

    @LuaFunction
    public final Object[] getData() {
        PhotographItem p = Exposure.Items.PHOTOGRAPH.get();
        Frame frame;
        if((frame = getFrame()) != null) {
            RequestedPalettedExposure load = ExposureServer.exposureRepository().load(frame.identifier().getId().get());
            ExposureData data = load.getData().get();
            ByteBuffer retData = ByteBuffer.wrap(data.getPixels());


            Map<Integer, Integer> size = new HashMap<>();
            size.put(1, data.getWidth());
            size.put(2, data.getHeight());

            return new Object[] {true, data.getPaletteId(), size, retData};
        }
        return new Object[] {false, null};
    }

    private Map<String, Object> buildEntity(EntityInFrame entityInFrame) {
        Map<String, Object> ret = new HashMap<>();
        ret.put("id", entityInFrame.id().toString());
        ret.put("name", entityInFrame.name());
        ret.put("distance", entityInFrame.distance());

        ret.put("pos", CCUtil.tizPos(entityInFrame.pos()));
        return ret;
    }



    @Override
    public String getType() {
        return "digitizer";
    }

    @Override
    public boolean equals(@Nullable IPeripheral iPeripheral) {
        return Objects.equals(this, iPeripheral);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) return true;
        if (obj == null || obj.getClass() != this.getClass()) return false;
        var that = (DigitizerPeripheral) obj;
        return Objects.equals(this.tile, that.tile);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tile);
    }

    public void informEmpty() {
        for (IComputerAccess computer : computers) {
            computer.queueEvent("digitizer_empty", computer.getAttachmentName());
        }
    }
    public void informNew() {
        for (IComputerAccess computer : computers) {
            computer.queueEvent("digitizer_new", computer.getAttachmentName());
        }
    }
}
