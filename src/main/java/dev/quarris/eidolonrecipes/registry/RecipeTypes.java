package dev.quarris.eidolonrecipes.registry;

import dev.quarris.eidolonrecipes.recipes.CrucibleRecipeWrapper;
import dev.quarris.eidolonrecipes.recipes.WorktableRecipeWrapper;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class RecipeTypes {

    public static final DeferredRegister<IRecipeSerializer<?>> RECIPE_SERIALIZERS = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, "eidolon");

    public static final IRecipeType<CrucibleRecipeWrapper> CRUCIBLE = IRecipeType.register("eidolon:crucible");
    public static final IRecipeType<WorktableRecipeWrapper> WORKTABLE = IRecipeType.register("eidolon:worktable");

    public static final RegistryObject<CrucibleRecipeWrapper.Serializer> CRUCIBLE_SERIALIZER = RECIPE_SERIALIZERS.register("crucible", CrucibleRecipeWrapper.Serializer::new);
    public static final RegistryObject<WorktableRecipeWrapper.Serializer> WORKTABLE_SERIALIZER = RECIPE_SERIALIZERS.register("worktable", WorktableRecipeWrapper.Serializer::new);
}
