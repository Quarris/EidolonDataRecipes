package dev.quarris.eidolonrecipes.util;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import dev.quarris.eidolonrecipes.ModRoot;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tags.ITag;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.crafting.CraftingHelper;
import net.minecraftforge.registries.ForgeRegistries;

public class ItemUtil {

    public static JsonObject serializeRecipeIngredient(Object ingredient) {
        JsonObject json = new JsonObject();
        if (ingredient instanceof ItemStack) {
            ItemStack item = (ItemStack) ingredient;
            json.addProperty("item", item.getItem().getRegistryName().toString());
            if (item.getCount() > 1) {
                json.addProperty("count", item.getCount());
            }
            if (item.hasTag()) {
                json.addProperty("nbt", item.getTag().toString());
            }
        } else if (ingredient instanceof Item) {
            json.addProperty("item", ((Item) ingredient).getRegistryName().toString());
        } else if (ingredient instanceof Block) {
            json.addProperty("item", ((Block) ingredient).asItem().getRegistryName().toString());
        } else if (ingredient instanceof ITag) {
            json.addProperty("tag", TagCollectionManager.getManager().getItemTags().getDirectIdFromTag((ITag<Item>) ingredient).toString());
        } else {
            ModRoot.LOGGER.warn("Unknown step match for writing to buffer {}", ingredient);
        }

        return json;
    }

    public static Object deserializeRecipeIngredient(JsonObject json) {
        if (json.has("tag")) {
            return TagCollectionManager.getManager().getItemTags().get(new ResourceLocation(json.get("tag").getAsString()));
        } else if (json.has("item")) {
            if (!json.has("count") && !json.has("nbt")) {
                Item item = ForgeRegistries.ITEMS.getValue(new ResourceLocation(json.get("item").getAsString()));
                if (item == null) {
                    throw new JsonParseException("Invalid item name " + json.get("item").getAsString());
                }
            }
            return CraftingHelper.getItemStack(json, true);
        }

        throw new JsonParseException("Recipe Ingredient must contain either a 'tag' or an 'item'");
    }

    public static void writeRecipeIngredient(Object ingredient, PacketBuffer buffer) {
        if (ingredient instanceof ItemStack) {
            buffer.writeVarInt(1);
            buffer.writeItemStack((ItemStack) ingredient);
        } else if (ingredient instanceof Item) {
            buffer.writeVarInt(2);
            buffer.writeResourceLocation(((Item) ingredient).getRegistryName());
        } else if (ingredient instanceof Block) {
            buffer.writeVarInt(3);
            buffer.writeResourceLocation(((Block) ingredient).getRegistryName());
        } else if (ingredient instanceof ITag) {
            buffer.writeVarInt(4);
            buffer.writeResourceLocation(TagCollectionManager.getManager().getItemTags().getDirectIdFromTag((ITag<Item>) ingredient));
        } else {
            ModRoot.LOGGER.warn("Unknown step match for writing to buffer {}", ingredient);
        }
    }

    public static Object readRecipeIngredient(PacketBuffer buffer) {
        int type = buffer.readVarInt();
        switch (type) {
            case 1: {
                return buffer.readItemStack();
            }
            case 2: {
                return ForgeRegistries.ITEMS.getValue(buffer.readResourceLocation());
            }
            case 3: {
                return ForgeRegistries.BLOCKS.getValue(buffer.readResourceLocation());
            }
            case 4: {
                return TagCollectionManager.getManager().getItemTags().get(buffer.readResourceLocation());
            }
            default: {
                ModRoot.LOGGER.warn("Unknown recipe ingredient type '{}' while reading a buffer", type);
                return null;
            }
        }
    }

    public static boolean matchesIngredient(Object match, ItemStack input) {
        if (match instanceof ItemStack) {
            ItemStack stack = (ItemStack) match;
            if (ItemStack.areItemsEqual(stack, input) && ItemStack.areItemStackTagsEqual(stack, input) && input.getCount() >= stack.getCount()) {
                return true;
            }
        } else if (match instanceof Item) {
            if ((Item)match == input.getItem()) {
                return true;
            }
        } else if (match instanceof Block) {
            if (((Block)match).asItem() == input.getItem()) {
                return true;
            }
        } else if (match instanceof ITag && ((ITag)match).contains(input.getItem())) {
            return true;
        }

        return false;
    }

}
