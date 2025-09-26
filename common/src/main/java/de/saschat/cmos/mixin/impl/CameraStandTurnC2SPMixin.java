package de.saschat.cmos.mixin.impl;

import de.saschat.cmos.mixin.duck.CameraStandEntityDuck;
import io.github.mortuusars.exposure.network.packet.clientbound.CameraStandSetRotationsS2CP;
import io.github.mortuusars.exposure.network.packet.serverbound.CameraStandTurnC2SP;
import io.github.mortuusars.exposure.world.entity.CameraStandEntity;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = CameraStandTurnC2SP.class, remap = false)
public class CameraStandTurnC2SPMixin {

    @Shadow @Final private double xRot;

    @Shadow @Final private double yRot;

    @Shadow @Final private int entityId;

    @Inject(at = @At(value = "INVOKE", target = "Lio/github/mortuusars/exposure/world/entity/CameraStandEntity;turn(DD)V"), method = "handle", remap = false)
    public void a(PacketFlow direction, Player player, CallbackInfoReturnable<Boolean> cir) {
        if(player.level().getEntity(entityId) instanceof CameraStandEntity stand) {
            ((CameraStandEntityDuck) stand).updateRot(xRot, yRot);
        }
    }
}
