package dev.quarris.eidolonrecipes.spells.type;

import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.data.ISerializer;
import net.minecraft.util.ResourceLocation;

public interface ISpellSerializer<T extends ISpell> extends ISerializer<JsonObject, T> {

    @Override
    default JsonObject serialize(T spell) {
        JsonObject json = new JsonObject();
        json.addProperty("type", this.getId().toString());
        this.serialize(json, spell);
        return json;
    }

    void serialize(JsonObject json, T spell);

    ResourceLocation getId();

}
