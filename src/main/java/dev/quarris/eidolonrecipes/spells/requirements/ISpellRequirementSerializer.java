package dev.quarris.eidolonrecipes.spells.requirements;

import com.google.gson.JsonObject;
import net.minecraft.network.PacketBuffer;

public interface ISpellRequirementSerializer<T extends ISpellRequirement> {

	T deserialize(JsonObject json);

	T read(PacketBuffer buf);

	void write(PacketBuffer buf, T result);

}
