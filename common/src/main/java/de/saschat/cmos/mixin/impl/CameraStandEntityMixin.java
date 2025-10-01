package de.saschat.cmos.mixin.impl;

import de.saschat.cmos.blocks.tiles.StandControllerTile;
import de.saschat.cmos.mixin.duck.CameraStandEntityDuck;
import io.github.mortuusars.exposure.world.entity.CameraStandEntity;
import net.minecraft.sounds.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.LinkedList;
import java.util.List;


@Mixin(value = CameraStandEntity.class, remap = false)
public abstract class CameraStandEntityMixin implements CameraStandEntityDuck {

    @Shadow(remap = false)
    protected abstract void showRepairingParticles();

    @Override
    public void playRepair() {
        CameraStandEntity x = (CameraStandEntity)(Object) this;
        x.playSound(SoundEvents.SMITHING_TABLE_USE, 0.9f, 1.3f);
        showRepairingParticles();
    }

    @Unique
    private List<StandControllerTile> tiles = new LinkedList<>();

    @Override
    public void reportRot() {
        for (StandControllerTile tile : tiles) {
            tile.updateRot();
        }
    }

    @Override
    public void attach(StandControllerTile tile) {
        tiles.add(tile);
    }
    @Override
    public void detach(StandControllerTile tile) {
        tiles.remove(tile);
    }
}
