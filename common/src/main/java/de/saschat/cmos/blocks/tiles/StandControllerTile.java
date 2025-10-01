package de.saschat.cmos.blocks.tiles;

import de.saschat.cmos.blocks.peripheral.DigitizerPeripheral;
import de.saschat.cmos.blocks.peripheral.StandControllerPeripheral;
import de.saschat.cmos.mixin.duck.CameraStandEntityDuck;
import de.saschat.cmos.registry.TileRegistry;
import de.saschat.cmos.util.WeakContainer;
import io.github.mortuusars.exposure.world.entity.CameraStandEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.entity.EntityTypeTest;
import net.minecraft.world.phys.AABB;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class StandControllerTile extends BlockEntity {
    public WeakContainer<StandControllerPeripheral> peripherals = new WeakContainer<>();
    public boolean events = false;

    public StandControllerTile(BlockPos blockPos, BlockState blockState) {
        super(TileRegistry.STAND_CONTROLLER_TILE.get(), blockPos, blockState);

    }
    public CameraStandEntity attached = null;

    public CameraStandEntity getStandEntity() {
        if(attached != null && !attached.isAlive()) {
            ((CameraStandEntityDuck) attached).detach(this);
            attached = null;
        }

        if(attached == null) {
            BlockPos block = getBlockPos().above();

            List<CameraStandEntity> entities = level.getEntities(EntityTypeTest.forClass(CameraStandEntity.class), new AABB(block.getX(), block.getY(), block.getZ(), block.getX() + 1, block.getY() + 1, block.getZ() + 1), (a) -> true);
            if (!entities.isEmpty()) {
                attached = entities.get(0);

                ((CameraStandEntityDuck) attached).attach(this);
                return attached;
            }
            return null;
        } else return attached;
    }

    public void updateRot() {
        if(events)
            peripherals.operate((x) -> x.updateRotation(getStandEntity().xRotO, getStandEntity().yRotO));
    }
}
