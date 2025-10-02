package de.saschat.cmos.items.cmoscamera;

import io.github.mortuusars.exposure.client.camera.viewfinder.Viewfinder;
import io.github.mortuusars.exposure.world.camera.Camera;
import org.jetbrains.annotations.NotNull;

public class CmosCameraViewfinder extends Viewfinder {
    public CmosCameraViewfinder(@NotNull Camera camera) {
        super(camera);
    }
}
