package dev.quarris.eidolonrecipes.util;

import com.google.gson.JsonSyntaxException;
import net.minecraft.advancements.criterion.EntityTypePredicate;
import net.minecraft.entity.EntityType;
import net.minecraft.tags.TagCollectionManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.registries.ForgeRegistries;

public class EntityUtil {

    public static EntityTypePredicate deserializeFromString(String s) {
        if (s.startsWith("#")) {
            ResourceLocation tagName = new ResourceLocation(s.substring(1));
            return new EntityTypePredicate.TagPredicate(TagCollectionManager.getManager().getEntityTypeTags().getTagByID(tagName));
        } else {
            ResourceLocation entityName = new ResourceLocation(s);
            EntityType<?> entitytype = ForgeRegistries.ENTITIES.getValue(entityName);
            if (entitytype == null) {
                throw new JsonSyntaxException("Unknown entity type '" + entityName + "'");
            }
            return new EntityTypePredicate.TypePredicate(entitytype);
        }
    }
}

