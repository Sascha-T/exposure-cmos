package de.saschat.cmos.blocks.peripheral;

import dan200.computercraft.api.lua.LuaFunction;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import de.saschat.cmos.blocks.tiles.StandControllerTile;
import de.saschat.cmos.mixin.duck.CameraStandEntityDuck;
import io.github.mortuusars.exposure.world.camera.component.FlashMode;
import io.github.mortuusars.exposure.world.camera.component.FocalRange;
import io.github.mortuusars.exposure.world.camera.component.ShutterSpeed;
import io.github.mortuusars.exposure.world.entity.CameraOperator;
import io.github.mortuusars.exposure.world.entity.CameraStandEntity;
import io.github.mortuusars.exposure.world.item.camera.CameraItem;
import io.github.mortuusars.exposure.world.item.camera.CameraSettings;
import io.github.mortuusars.exposure.world.item.camera.ShutterState;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.jspecify.annotations.Nullable;

import java.util.*;


// todo 1. javadoc all, 2. check fabric, 3. create "event attach ability" events for stand controller on found/lost stand controller, 3a. do this over tick%20 events, 4. fix rotation values
public class StandControllerPeripheral implements IPeripheral {
    StandControllerTile tile;

    public StandControllerPeripheral(BlockEntity ent, Direction dir) {
        this.tile = (StandControllerTile) ent;
        tile.peripherals.add(this);
    }

    public List<IComputerAccess> computers = new LinkedList<>();

    @Override
    public void attach(IComputerAccess computer) {
        IPeripheral.super.attach(computer);
        computers.add(computer);
    }

    @Override
    public void detach(IComputerAccess computer) {
        IPeripheral.super.detach(computer);
        computers.remove(computer);
    }

    private void closeCamera() {
        CameraOperator operator;
        if (tile.getStandEntity().getCamera().getItem() instanceof CameraItem x) {
            if ((operator = tile.getStandEntity().operator()) != null) {
                operator.removeActiveExposureCamera();
                x.deactivate(operator.asOperatorEntity(), tile.getStandEntity().getCamera());
            }
        }
    }


    @LuaFunction
    public final boolean isPresent() {
        return tile.getStandEntity() != null;
    }

    @LuaFunction
    public final boolean isMalfunctioned() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null) {
            return standEntity.isMalfunctioned();
        } else return false;
    }

    @LuaFunction
    public final double getYaw() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null) {
            return standEntity.getYRot();
        } else return -1;
    }

    @LuaFunction
    public final double getPitch() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null) {
            return standEntity.getXRot();
        } else return -1;
    }

    @LuaFunction
    public final boolean setYaw(double yaw) {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null) {
            closeCamera();
            standEntity.setYRot((float) yaw % 360);
            standEntity.syncRotationToClients();
            return true;
        } else return false;
    }

    @LuaFunction
    public final boolean setPitch(double pitch) {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null) {
            closeCamera();
            standEntity.setXRot((float) pitch % 360);
            standEntity.syncRotationToClients();
            return true;
        } else return false;
    }


    @LuaFunction
    public final boolean trigger() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            closeCamera();
            if (item.canTakePhoto(standEntity, standEntity.getCamera())) {
                standEntity.release();
                return true;
            }
        }
        return false;
    }

    @LuaFunction
    public final boolean canTrigger() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            closeCamera();
            if (item.canTakePhoto(standEntity, standEntity.getCamera())) {
                return true;
            }
        }
        return false;
    }

    @LuaFunction
    public final Map<Integer, String> getAvailableShutterSpeeds() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            Map<Integer, String> speeds = new HashMap<>();
            int i = 1;
            for (ShutterSpeed availableShutterSpeed : item.getAvailableShutterSpeeds()) {
                speeds.put(i, availableShutterSpeed.getNotation());
                i++;
            }
            return speeds;
        }
        return null;
    }

    @LuaFunction
    public final String getShutterSpeed() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            ShutterState state = item.getShutter().getState(standEntity.getCamera());
            return state.shutterSpeed().getNotation();
        }
        return null;
    }

    @LuaFunction
    public final boolean setShutterSpeed(String shutterSpeed) {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            ShutterSpeed select = null;
            for (ShutterSpeed availableShutterSpeed : item.getAvailableShutterSpeeds()) {
                if (availableShutterSpeed.getNotation().equals(shutterSpeed)) {
                    select = availableShutterSpeed;
                }
            }
            if (select != null) {
                closeCamera();
                CameraSettings.SHUTTER_SPEED.set(standEntity.getCamera(), select);
                return true;
            }
        }
        return false;
    }

    @LuaFunction
    public final Map<Integer, Integer> getFocalRange() {
        ServerLevel level = (ServerLevel) tile.getLevel();
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            FocalRange focalRange = item.getFocalRange(level.registryAccess(), standEntity.getCamera());
            return Map.of(1, focalRange.min(), 2, focalRange.max());
        }
        return null;
    }
    @LuaFunction
    public final double getZoom() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            return CameraSettings.ZOOM.get(standEntity.getCamera());
        }
        return -1;
    }
    @LuaFunction
    public final boolean setZoom(double zoom) {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            closeCamera();
            CameraSettings.ZOOM.set(standEntity.getCamera(), Math.min(Math.max((float) zoom, 0), 1));
            return true;
        }
        return false;
    }

    @LuaFunction
    public final boolean setFlash(int flash) {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            closeCamera();
            int idx = ((int) Math.abs(flash)) % FlashMode.values().length;
            CameraSettings.FLASH_MODE.set(standEntity.getCamera(), FlashMode.values()[idx]);
            return true;
        }
        return false;
    }
    @LuaFunction
    public final String getFlash() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null && !standEntity.getCamera().isEmpty() && standEntity.getCamera().getItem() instanceof CameraItem item) {
            return CameraSettings.FLASH_MODE.get(standEntity.getCamera()).name();
        }
        return null;
    }

    @LuaFunction
    public final boolean fix() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if (standEntity != null) {
            if (standEntity.isMalfunctioned()) {
                ((CameraStandEntityDuck) standEntity).playRepair();
            }
            standEntity.setMalfunctioned(false);
            return true;
        }
        return false;
    }

    @LuaFunction
    public final boolean attachEvents() {
        CameraStandEntity standEntity = tile.getStandEntity();
        if(standEntity != null) {
            tile.events = true;
            return true;
        }
        return false;
    }

    @LuaFunction
    public final void detachEvents() {
        tile.events = false;
    }

    @Override
    public String getType() {
        return "stand_controller";
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

    public void updateRotation(double xRot, double yRot) {
        for (IComputerAccess computer : computers) {
            yRot = yRot % 360;
            if(yRot < 0)
                yRot += 360;
            computer.queueEvent("stand_controller_rotation", computer.getAttachmentName(), yRot % 360, xRot);
        }
    }
}
