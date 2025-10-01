package de.saschat.cmos.mixin.impl;

import de.saschat.cmos.mixin.duck.CameraStandEntityDuck;
import io.github.mortuusars.exposure.network.packet.serverbound.CameraStandTurnC2SP;
import io.github.mortuusars.exposure.world.entity.CameraStandEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(value = CameraStandTurnC2SP.class, remap = false)
public class CameraStandTurnC2SPMixin {

    @Shadow
    @Final
    private double xRot;

    @Shadow
    @Final
    private double yRot;

    @Shadow
    @Final
    private int entityId;

    @Redirect(at = @At(value = "INVOKE", target = "Lio/github/mortuusars/exposure/world/entity/CameraStandEntity;turn(DD)V"), method = "handle", remap = false)
    public void a(CameraStandEntity instance, double a, double e) {
        instance.turn(a, e);
        ((CameraStandEntityDuck) instance).reportRot();
    }
}
