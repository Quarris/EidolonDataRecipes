package dev.quarris.eidolonrecipes.registry;

import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import elucent.eidolon.spell.Spell;
import elucent.eidolon.spell.Spells;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;

import java.util.List;
import java.util.Map;

public class EidolonReflectedRegistries {

    public static final Map<ResourceLocation, CrucibleRecipe> CRUCIBLE_RECIPES = ObfuscationReflectionHelper.getPrivateValue(CrucibleRegistry.class, null, "recipes");

    public static final Map<ResourceLocation, WorktableRecipe> WORKTABLE_RECIPES = ObfuscationReflectionHelper.getPrivateValue(WorktableRegistry.class, null, "recipes");

    public static final Map<ResourceLocation, Spell> SPELL_MAP = ObfuscationReflectionHelper.getPrivateValue(Spells.class, null, "spellMap");

    public static final List<Spell> SPELLS = ObfuscationReflectionHelper.getPrivateValue(Spells.class, null, "spells");

    public static void onLoadComplete() {
        CRUCIBLE_RECIPES.clear();
        WORKTABLE_RECIPES.clear();

        // Remove Dark Spell in exchange for using Transmutation spell
        //SPELLS.remove(Spells.DARK_TOUCH);
        //SPELL_MAP.remove(Spells.DARK_TOUCH.getRegistryName());
    }

}
