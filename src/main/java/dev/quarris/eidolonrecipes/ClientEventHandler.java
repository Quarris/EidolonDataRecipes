package dev.quarris.eidolonrecipes;

import dev.quarris.eidolonrecipes.registry.EidolonReflectedRegistries;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRoot.ID, value = Dist.CLIENT)
public class ClientEventHandler {

    @SubscribeEvent
    public static void on(RecipesUpdatedEvent event) {
        System.out.println(Minecraft.getInstance().world.getTags().getItemTags().getRegisteredTags().size());
        EidolonReflectedRegistries.onDataPackReloaded(event.getRecipeManager());
    }
}
