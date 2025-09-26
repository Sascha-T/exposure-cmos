package de.saschat.cmos.neoforge;

import dev.architectury.platform.forge.PlatformImpl;
import de.saschat.cmos.ExposureComputerMod;
import dev.architectury.platform.hooks.EventBusesHooks;
import net.minecraft.util.datafix.schemas.V3818_3;
import net.neoforged.fml.common.Mod;

@Mod(ExposureComputerMod.MOD_ID)
public final class ExposureComputerModNeoForge {
    public ExposureComputerModNeoForge() {
        // Run our common setup.
        ExposureComputerMod.init();
    }
}
