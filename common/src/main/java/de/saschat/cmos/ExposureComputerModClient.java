package de.saschat.cmos;

import de.saschat.cmos.items.cmoscamera.CmosCameraViewfinder;
import de.saschat.cmos.registry.GuiRegistry;
import de.saschat.cmos.registry.ItemRegistry;
import de.saschat.cmos.registry.ScreenRegistry;
import io.github.mortuusars.exposure.client.camera.viewfinder.ViewfinderRegistry;
import io.github.mortuusars.exposure.client.capture.template.CameraCaptureTemplate;
import io.github.mortuusars.exposure.client.capture.template.CaptureTemplates;
import net.minecraft.resources.ResourceLocation;

public class ExposureComputerModClient {
    public static void init() {
        ViewfinderRegistry.register(ItemRegistry.CMOS_CAMERA_ITEM.get(), CmosCameraViewfinder::new);
        CaptureTemplates.register(ItemRegistry.CMOS_CAMERA_ITEM.getId(), new CameraCaptureTemplate());
    }
}
