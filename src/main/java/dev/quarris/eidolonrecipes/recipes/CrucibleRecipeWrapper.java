package dev.quarris.eidolonrecipes.recipes;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.registry.RecipeTypes;
import dev.quarris.eidolonrecipes.utils.ItemUtil;
import elucent.eidolon.recipe.CrucibleRecipe;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistryEntry;

import java.util.ArrayList;
import java.util.List;

public class CrucibleRecipeWrapper extends CrucibleRecipe implements IRecipe<IInventory> {

    public CrucibleRecipeWrapper(ItemStack result) {
        super(result);
    }

    @Override
    public boolean matches(IInventory inv, World worldIn) {
        return false;
    }

    @Override
    public ItemStack getCraftingResult(IInventory inv) {
        return ItemStack.EMPTY;
    }

    @Override
    public boolean canFit(int width, int height) {
        return false;
    }

    @Override
    public ItemStack getRecipeOutput() {
        return ItemStack.EMPTY;
    }

    @Override
    public ResourceLocation getId() {
        return this.getRegistryName();
    }

    @Override
    public IRecipeSerializer<?> getSerializer() {
        return RecipeTypes.CRUCIBLE_SERIALIZER.get();
    }

    @Override
    public IRecipeType<?> getType() {
        return RecipeTypes.CRUCIBLE;
    }

    public static final class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<CrucibleRecipeWrapper> {

        @Override
        public CrucibleRecipeWrapper read(ResourceLocation recipeId, JsonObject json) {
            ItemStack result = CraftingHelper.getItemStack(JSONUtils.getJsonObject(json, "result"), true);
            CrucibleRecipeWrapper recipe = new CrucibleRecipeWrapper(result);
            for (JsonElement jsonStep : JSONUtils.getJsonArray(json, "steps")) {
                List<Object> matches = new ArrayList<>();
                for (JsonElement jsonIngredient : JSONUtils.getJsonArray(JSONUtils.getJsonObject(jsonStep, "step"), "ingredients")) {
                    Object ingredient = ItemUtil.deserializeRecipeIngredient(jsonIngredient.getAsJsonObject());
                    if (ingredient instanceof ItemStack) {
                        ItemStack item = (ItemStack) ingredient;
                        for (int i = 0; i < item.getCount(); i++) {
                            ItemStack copy = item.copy();
                            copy.setCount(1);
                            matches.add(copy);
                        }
                    } else {
                        matches.add(ingredient);
                    }
                }
                int stirs = JSONUtils.getInt(jsonStep.getAsJsonObject(), "stirs", 0);
                recipe.addStirringStep(stirs, matches.toArray());
            }
            recipe.setRegistryName(recipeId);
            return recipe;
        }

        @Override
        public void write(PacketBuffer buffer, CrucibleRecipeWrapper recipe) {
            buffer.writeItemStack(recipe.getResult());
            buffer.writeVarInt(recipe.getSteps().size());
            for (Step step : recipe.getSteps()) {
                writeStep(buffer, step);
            }
        }

        @Override
        public CrucibleRecipeWrapper read(ResourceLocation recipeId, PacketBuffer buffer) {
            CrucibleRecipeWrapper recipe = new CrucibleRecipeWrapper(buffer.readItemStack());
            int steps = buffer.readVarInt();
            for (int i = 0; i < steps; i++) {
                readStep(buffer, recipe);
            }

            recipe.setRegistryName(recipeId);
            return recipe;
        }

        public static void writeStep(PacketBuffer buffer, Step step) {
            buffer.writeVarInt(step.stirs);
            buffer.writeVarInt(step.matches.size());
            for (Object obj : step.matches) {
                ItemUtil.writeRecipeIngredient(obj, buffer);
            }
        }

        public static void readStep(PacketBuffer buffer, CrucibleRecipeWrapper recipe) {
            int stirs = buffer.readVarInt();
            int steps = buffer.readVarInt();
            List<Object> matches = new ArrayList<>();
            for (int i = 0; i < steps; i++) {
                Object ingredient = ItemUtil.readRecipeIngredient(buffer);
                if (ingredient != null) {
                    matches.add(ingredient);
                }
            }
            recipe.addStirringStep(stirs, matches);
        }
    }
}
