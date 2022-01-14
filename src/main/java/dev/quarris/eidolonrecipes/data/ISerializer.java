package dev.quarris.eidolonrecipes.data;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import net.minecraft.network.PacketBuffer;

public interface ISerializer<D extends JsonElement, T> {

	D serialize(Gson gson, T obj);

	T deserialize(D json);

	void write(PacketBuffer buf, T obj);

	T read(PacketBuffer buf);

}
