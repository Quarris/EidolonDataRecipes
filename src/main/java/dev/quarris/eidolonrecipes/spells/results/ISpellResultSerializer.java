package dev.quarris.eidolonrecipes.spells.results;

import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.data.ISerializer;
import net.minecraft.network.PacketBuffer;

public interface ISpellResultSerializer<T extends ISpellResult> extends ISerializer<JsonObject, T> {

}
