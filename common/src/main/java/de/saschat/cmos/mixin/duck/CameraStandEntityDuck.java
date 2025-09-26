package de.saschat.cmos.mixin.duck;

import de.saschat.cmos.blocks.tiles.StandControllerTile;

public interface CameraStandEntityDuck {
    void playRepair();

    void attach(StandControllerTile tile);
    void detach(StandControllerTile tile);
    void updateRot(double xRot, double yRot);
}
