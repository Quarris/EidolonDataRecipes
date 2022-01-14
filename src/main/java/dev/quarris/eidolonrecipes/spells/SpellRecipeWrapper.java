package dev.quarris.eidolonrecipes.spells;

import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.registry.RecipeTypes;
import dev.quarris.eidolonrecipes.spells.types.ISpellTypeSerializer;
import dev.quarris.eidolonrecipes.spells.types.ISpellType;
import elucent.eidolon.spell.Spell;
import elucent.eidolon.spell.StaticSpell;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistryEntry;

import javax.annotation.Nullable;

public class SpellRecipeWrapper extends StaticSpell implements IRecipe<IInventory> {

	public SpellRecipeWrapper(ResourceLocation deity, ) {
	}

	public ResourceLocation getDeity() {
		return null;
	}

	public ISpellType getSpellType() {
		return null;
	}

	public ISpellTypeSerializer getSpellSerializer() {
		return null;
	}

	@Override
	public ResourceLocation getId() {
		return ;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return RecipeTypes.SPELL_SERIALIZER.get();
	}

	@Override
	public IRecipeType<?> getType() {
		return RecipeTypes.SPELL;
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
	public boolean canCast(World world, BlockPos blockPos, PlayerEntity playerEntity) {
		return false;
	}

	@Override
	public void cast(World world, BlockPos blockPos, PlayerEntity playerEntity) {

	}

	public static class Serializer extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<SpellRecipeWrapper> {

		@Override
		public SpellRecipeWrapper read(ResourceLocation recipeId, JsonObject json) {
			return null;
		}

		@Nullable
		@Override
		public SpellRecipeWrapper read(ResourceLocation recipeId, PacketBuffer buffer) {
			return null;
		}

		@Override
		public void write(PacketBuffer buffer, SpellRecipeWrapper recipe) {

		}
	}
}
