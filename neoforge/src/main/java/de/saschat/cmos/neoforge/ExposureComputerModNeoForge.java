package de.saschat.cmos.neoforge;

import de.saschat.cmos.ExposureComputerModClient;
import de.saschat.cmos.registry.neoforge.ScreenRegistryImpl;
import dev.architectury.platform.forge.PlatformImpl;
import de.saschat.cmos.ExposureComputerMod;
import dev.architectury.platform.hooks.EventBusesHooks;
import net.minecraft.util.datafix.schemas.V3818_3;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLClientSetupEvent;

@Mod(ExposureComputerMod.MOD_ID)
public final class ExposureComputerModNeoForge {
    public ExposureComputerModNeoForge() {
        // Run our common setup.
        ExposureComputerMod.init();

        EventBusesHooks.getModEventBus(ExposureComputerMod.MOD_ID).get().addListener(ScreenRegistryImpl::registerScreen);
    }

    @EventBusSubscriber(modid = ExposureComputerMod.MOD_ID, value = Dist.CLIENT)
    public static class ModBus {
        @SubscribeEvent
        public static void clientSetup(FMLClientSetupEvent event) {
            ExposureComputerModClient.init();
        }

    }
}
