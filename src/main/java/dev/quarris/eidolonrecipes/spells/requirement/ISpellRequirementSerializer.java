package dev.quarris.eidolonrecipes.spells.requirement;

import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.data.ISerializer;
import net.minecraft.util.ResourceLocation;

public interface ISpellRequirementSerializer<T extends ISpellRequirement> extends ISerializer<JsonObject, T> {

    @Override
    default JsonObject serialize(T requirement) {
        JsonObject json = new JsonObject();
        json.addProperty("type", this.getId().toString());
        this.serialize(json, requirement);
        return json;
    }

    void serialize(JsonObject json, T requirement);

    ResourceLocation getId();

}
