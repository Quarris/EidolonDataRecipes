package dev.quarris.eidolonrecipes.spells.type.impl;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.ModRoot;
import dev.quarris.eidolonrecipes.spells.SpellInfo;
import dev.quarris.eidolonrecipes.spells.SpellRecipeWrapper;
import dev.quarris.eidolonrecipes.spells.type.ISpell;
import dev.quarris.eidolonrecipes.spells.type.ISpellSerializer;
import dev.quarris.eidolonrecipes.util.ItemUtil;
import elucent.eidolon.network.MagicBurstEffectPacket;
import elucent.eidolon.network.Networking;
import elucent.eidolon.spell.Signs;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;
import net.minecraftforge.common.crafting.CraftingHelper;

import java.util.ArrayList;
import java.util.List;

public class TransmutationSpell implements ISpell {
    public static final ResourceLocation ID = ModRoot.eidolonRes("transmutation");

    private final List<TransmutationRecipe> transmutations;

    public TransmutationSpell(List<TransmutationRecipe> transmutations) {
        this.transmutations = transmutations;
    }

    @Override
    public boolean canCast(SpellRecipeWrapper spell, World world, BlockPos pos, PlayerEntity caster, SpellInfo spellInfo) {
        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(caster.getEyePosition(0.0F), caster.getEyePosition(0.0F).add(caster.getLookVec().scale(4.0D)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, caster));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : caster.getEyePosition(0.0F).add(caster.getLookVec().scale(4.0D));
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5D, v.y - 1.5D, v.z - 1.5D, v.x + 1.5D, v.y + 1.5D, v.z + 1.5D));

        for (TransmutationRecipe recipe : this.transmutations) {
            if (recipe.match(items, null)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public void onCast(SpellRecipeWrapper spell, World world, BlockPos pos, PlayerEntity caster, SpellInfo spellInfo) {
        RayTraceResult ray = world.rayTraceBlocks(new RayTraceContext(caster.getEyePosition(0.0F), caster.getEyePosition(0.0F).add(caster.getLookVec().scale(4.0D)), RayTraceContext.BlockMode.OUTLINE, RayTraceContext.FluidMode.NONE, caster));
        Vector3d v = ray.getType() == RayTraceResult.Type.BLOCK ? ray.getHitVec() : caster.getEyePosition(0.0F).add(caster.getLookVec().scale(4.0D));
        List<ItemEntity> items = world.getEntitiesWithinAABB(ItemEntity.class, new AxisAlignedBB(v.x - 1.5D, v.y - 1.5D, v.z - 1.5D, v.x + 1.5D, v.y + 1.5D, v.z + 1.5D));

        TransmutationRecipe transmutation = null;
        List<ItemEntity> matched = new ArrayList<>();
        for (TransmutationRecipe recipe : this.transmutations) {
            if (recipe.match(items, matched)) {
                transmutation = recipe;
            }
        }

        if (transmutation == null) {
            return;
        }

        if (!world.isRemote) {
            for (ItemEntity item : matched) {
                Vector3d p = item.getPositionVec();
                item.remove();
                Networking.sendToTracking(world, item.getPosition(), new MagicBurstEffectPacket(p.x, p.y, p.z, Signs.WICKED_SIGN.getColor(), Signs.BLOOD_SIGN.getColor()));
            }

            for (ItemStack item : transmutation.results) {
                ItemEntity entity = new ItemEntity(world, v.x, v.y, v.z, item.copy());
                entity.setDefaultPickupDelay();
                world.addEntity(entity);
            }

            Networking.sendToTracking(world, new BlockPos(v), new MagicBurstEffectPacket(v.x, v.y, v.z, Signs.WICKED_SIGN.getColor(), Signs.BLOOD_SIGN.getColor()));
        } else {
            world.playSound(caster, caster.getPosition(), SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE, SoundCategory.NEUTRAL, 1.0F, 0.6F + world.rand.nextFloat() * 0.2F);
        }
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class TransmutationRecipe {

        private final List<Object> ingredients;
        private final List<ItemStack> results;

        public TransmutationRecipe(List<Object> ingredients, List<ItemStack> results) {
            this.ingredients = ingredients;
            this.results = results;
        }

        public boolean match(List<ItemEntity> items, List<ItemEntity> matched) {
            items = new ArrayList<>(items);
            List<Object> matchList = new ArrayList<>(this.ingredients);
            for (int i = 0; i < matchList.size(); i++) {
                Object match = matchList.get(i);
                for (int j = 0; j < items.size(); j++) {
                    ItemEntity item = items.get(j);
                    if (ItemUtil.matchesIngredient(match, item.getItem())) {
                        if (matched != null) {
                            matched.add(item);
                        }
                        matchList.remove(i);
                        items.remove(j);
                        break;
                    }
                }
            }

            return matchList.isEmpty();
        }

        public List<ItemEntity> filterMatched(List<ItemEntity> items) {
            List<ItemEntity> matched = new ArrayList<>();
            for (Object ingredient : this.ingredients) {
                for (int i = 0; i < items.size(); i++) {
                    ItemEntity e = items.get(i);
                    if (ItemUtil.matchesIngredient(ingredient, e.getItem())) {
                        matched.add(e);
                        items.remove(i);
                        break;
                    }
                }
            }
            return matched;
        }


        public JsonObject toJson() {
            JsonArray ingredientArray = new JsonArray();
            for (Object ingredient : this.ingredients) {
                ingredientArray.add(ItemUtil.serializeRecipeIngredient(ingredient));
            }

            JsonArray resultArray = new JsonArray();
            for (ItemStack result : this.results) {
                resultArray.add(ItemUtil.serializeRecipeIngredient(result));
            }

            JsonObject json = new JsonObject();
            json.add("ingredients", ingredientArray);
            json.add("results", resultArray);

            return json;
        }

        public static TransmutationRecipe fromJson(JsonObject json) {
            List<Object> ingredients = new ArrayList<>();
            JsonArray ingredientArray = JSONUtils.getJsonArray(json, "ingredients", new JsonArray());
            ingredientArray.forEach(ingredientJson -> {
                ingredients.add(ItemUtil.deserializeRecipeIngredient((JsonObject) ingredientJson));
            });

            List<ItemStack> results = new ArrayList<>();
            JsonArray resultArray = JSONUtils.getJsonArray(json, "results", new JsonArray());
            resultArray.forEach(resultJson -> {
                results.add(CraftingHelper.getItemStack((JsonObject) resultJson, true));
            });

            return new TransmutationRecipe(ingredients, results);
        }

        public void write(PacketBuffer buf) {
            buf.writeVarInt(this.ingredients.size());
            for (Object ingredient : this.ingredients) {
                ItemUtil.writeRecipeIngredient(ingredient, buf);
            }

            buf.writeVarInt(this.results.size());
            for (ItemStack result : this.results) {
                buf.writeItemStack(result);
            }
        }

        public static TransmutationRecipe fromPacketBuffer(PacketBuffer buf) {
            List<Object> ingredients = new ArrayList<>();
            int size = buf.readVarInt();
            for (int i = 0; i < size; i++) {
                ingredients.add(ItemUtil.readRecipeIngredient(buf));
            }

            List<ItemStack> results = new ArrayList<>();
            size = buf.readVarInt();
            for (int i = 0; i < size; i++) {
                results.add(buf.readItemStack());
            }

            return new TransmutationRecipe(ingredients, results);
        }
    }

    public static class Serializer implements ISpellSerializer<TransmutationSpell> {

        @Override
        public void serialize(JsonObject json, TransmutationSpell spell) {
            JsonArray transmutations = new JsonArray();
            for (TransmutationRecipe recipe : spell.transmutations) {
                transmutations.add(recipe.toJson());
            }
            json.add("transmutations", transmutations);
        }

        @Override
        public TransmutationSpell deserialize(JsonObject json) {
            List<TransmutationRecipe> recipes = new ArrayList<>();
            JsonArray transmutations = JSONUtils.getJsonArray(json, "transmutations");
            transmutations.forEach(tr -> {
                JsonObject trJson = (JsonObject) tr;
                recipes.add(TransmutationRecipe.fromJson(trJson));
            });
            return new TransmutationSpell(recipes);
        }

        @Override
        public void write(PacketBuffer buf, TransmutationSpell spell) {
            buf.writeVarInt(spell.transmutations.size());
            for (TransmutationRecipe recipe : spell.transmutations) {
                recipe.write(buf);
            }
        }

        @Override
        public TransmutationSpell read(PacketBuffer buf) {
            List<TransmutationRecipe> recipes = new ArrayList<>();
            int size = buf.readVarInt();
            for (int i = 0; i < size; i++) {
                recipes.add(TransmutationRecipe.fromPacketBuffer(buf));
            }
            return new TransmutationSpell(recipes);
        }

        @Override
        public ResourceLocation getId() {
            return ID;
        }
    }
}
