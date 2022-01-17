package dev.quarris.eidolonrecipes.registry;

import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import elucent.eidolon.spell.Spell;
import elucent.eidolon.spell.Spells;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.RecipeManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class EidolonReflectedRegistries {

    public static final Map<ResourceLocation, CrucibleRecipe> CRUCIBLE_RECIPES = ObfuscationReflectionHelper.getPrivateValue(CrucibleRegistry.class, null, "recipes");

    public static final Map<ResourceLocation, WorktableRecipe> WORKTABLE_RECIPES = ObfuscationReflectionHelper.getPrivateValue(WorktableRegistry.class, null, "recipes");

    public static final List<Spell> SPELLS = ObfuscationReflectionHelper.getPrivateValue(Spells.class, null, "spells");

    public static final Map<ResourceLocation, Spell> SPELL_MAP = ObfuscationReflectionHelper.getPrivateValue(Spells.class, null, "spellMap");

    public static void onDataPackReloaded(RecipeManager manager) {
        CRUCIBLE_RECIPES.clear();
        CRUCIBLE_RECIPES.putAll(getRecipes(manager, RecipeTypes.CRUCIBLE));

        WORKTABLE_RECIPES.clear();
        WORKTABLE_RECIPES.putAll(getRecipes(manager, RecipeTypes.WORKTABLE));

        SPELL_MAP.clear();
        SPELLS.clear();

        SPELL_MAP.putAll(getRecipes(manager, RecipeTypes.SPELL));
        SPELLS.addAll(SPELL_MAP.values());

    }

    private static <C extends IInventory, T extends IRecipe<C>> Map<ResourceLocation, T> getRecipes(RecipeManager manager, IRecipeType<T> type) {
        return manager.getRecipesForType(type).stream().collect(Collectors.toMap(IRecipe::getId, Function.identity()));
    }

}
