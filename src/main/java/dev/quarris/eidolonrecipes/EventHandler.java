package dev.quarris.eidolonrecipes;

import dev.quarris.eidolonrecipes.registry.EidolonReflectedRegistries;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRoot.ID)
public class EventHandler {

    @SubscribeEvent
    public static void onDataReload(OnDatapackSyncEvent event) {
        EidolonReflectedRegistries.onDataPackReloaded(event.getPlayerList().getServer());

    }
}
