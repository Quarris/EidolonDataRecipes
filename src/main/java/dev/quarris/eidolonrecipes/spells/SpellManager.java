package dev.quarris.eidolonrecipes.spells;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.client.resources.JsonReloadListener;
import net.minecraft.profiler.IProfiler;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

public class SpellManager extends JsonReloadListener {
    private static final Gson GSON = (new GsonBuilder()).setPrettyPrinting().disableHtmlEscaping().create();
    public static final SpellManager INST = new SpellManager();



    private SpellManager() {
        super(GSON, "spells");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> entries, IResourceManager resourceManagerIn, IProfiler profilerIn) {
        entries.forEach((name, element) -> {
            JsonObject spellJson = element.getAsJsonObject();
            ResourceLocation type = new ResourceLocation(JSONUtils.getString(spellJson, "type"));

        });
    }
}
