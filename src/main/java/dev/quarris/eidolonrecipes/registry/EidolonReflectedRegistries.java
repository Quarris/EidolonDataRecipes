package dev.quarris.eidolonrecipes.registry;

import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EidolonReflectedRegistries {

    public static final Map<ResourceLocation, CrucibleRecipe> CRUCIBLE_RECIPES = ObfuscationReflectionHelper.getPrivateValue(CrucibleRegistry.class, null, "recipes");

    public static final Map<ResourceLocation, WorktableRecipe> WORKTABLE_RECIPES = ObfuscationReflectionHelper.getPrivateValue(WorktableRegistry.class, null, "recipes");

    public static void onDataPackReloaded(MinecraftServer server) {
        EidolonReflectedRegistries.CRUCIBLE_RECIPES.clear();
        EidolonReflectedRegistries.CRUCIBLE_RECIPES.putAll(getRecipes(server, RecipeTypes.CRUCIBLE));

        EidolonReflectedRegistries.WORKTABLE_RECIPES.clear();
        EidolonReflectedRegistries.WORKTABLE_RECIPES.putAll(getRecipes(server, RecipeTypes.WORKTABLE));
    }

    private static <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, T> getRecipes(MinecraftServer server, IRecipeType<T> type) {
        return server.getRecipeManager().getRecipesForType(type).stream().collect(Collectors.toMap(IRecipe::getId, Function.identity()));
    }

}
