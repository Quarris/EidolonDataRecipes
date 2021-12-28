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

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Mod.EventBusSubscriber(modid = ModRoot.ID)
public class EventHandler {

    @SubscribeEvent
    public static void addReloadListeners(AddReloadListenerEvent event) {

    }

    @SubscribeEvent
    public static void onDataReload(OnDatapackSyncEvent event) {
        EidolonReflectedRegistries.CRUCIBLE_RECIPES.clear();
        EidolonReflectedRegistries.CRUCIBLE_RECIPES.putAll(getRecipes(event, RecipeTypes.CRUCIBLE));

        EidolonReflectedRegistries.WORKTABLE_RECIPES.clear();
        EidolonReflectedRegistries.WORKTABLE_RECIPES.putAll(getRecipes(event, RecipeTypes.WORKTABLE));
    }

    private static <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, T> getRecipes(OnDatapackSyncEvent event, IRecipeType<T> type) {
        return event.getPlayerList().getServer().getRecipeManager().getRecipesForType(type).stream().collect(Collectors.toMap(IRecipe::getId, Function.identity()));
    }

}
