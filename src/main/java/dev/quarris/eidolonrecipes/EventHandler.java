package dev.quarris.eidolonrecipes;

import dev.quarris.eidolonrecipes.registry.EidolonReflectedRegistries;
import dev.quarris.eidolonrecipes.registry.RecipeTypes;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.AddReloadListenerEvent;
import net.minecraftforge.event.OnDatapackSyncEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModRoot.ID)
public class EventHandler {

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {

    }

    @SubscribeEvent
    public static void onDataReload(OnDatapackSyncEvent event) {
        EidolonReflectedRegistries.onDataPackReloaded(event.getPlayerList().getServer().getRecipeManager());
    }
}
