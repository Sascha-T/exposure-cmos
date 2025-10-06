package de.saschat.cmos.mixin.impl;

import de.saschat.cmos.items.CmosCameraItem;
import de.saschat.cmos.registry.ComponentRegistry;
import de.saschat.cmos.util.Location;
import io.github.mortuusars.exposure.world.entity.CameraHolder;
import io.github.mortuusars.exposure.world.item.camera.CameraItem;
import io.github.mortuusars.exposure.world.level.storage.ExposureIdentifier;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(value = CameraItem.class, remap = false)
public class CameraItemMixin {
    @Unique
    private String identifier;

    @Inject(at = @At(value = "HEAD"), method = "takePhoto", remap = false)
    public void pretext(CameraHolder holder, ServerPlayer executingPlayer, ItemStack stack, CallbackInfo ci) {
        if(((Object) this) instanceof CmosCameraItem) {
            Location q = stack.get(ComponentRegistry.LOCATION.get());
            identifier = "cmos_" + q.hashCode();
        }
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lio/github/mortuusars/exposure/world/level/storage/ExposureIdentifier;createId(Lnet/minecraft/world/entity/Entity;[Ljava/lang/String;)Ljava/lang/String;"), method = "takePhoto", remap = false)
    public String getId(Entity entity, String[] middleParts) {
        if(((Object) this) instanceof CmosCameraItem && identifier != null) {
            return identifier;
        }
        return ExposureIdentifier.createId(entity, middleParts);
    }
}
