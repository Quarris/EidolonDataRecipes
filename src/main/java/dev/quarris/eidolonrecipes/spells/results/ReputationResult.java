package dev.quarris.eidolonrecipes.spells.results;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.utils.SpellUtil;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;

public class ReputationResult implements ISpellResult {

	public static final ResourceLocation ID = new ResourceLocation("eidolon", "reputation");

	private final int constant;
	private final float altarMultiplier;

	public ReputationResult(int constant, float altarMultiplier) {
		this.constant = constant;
		this.altarMultiplier = altarMultiplier;
	}

	@Override
	public ResourceLocation getId() {
		return ID;
	}

	@Override
	public void onCast(IWorld world, PlayerEntity caster) {
		SpellUtil.addReputation();
	}

	public static class Serializer implements ISpellResultSerializer<ReputationResult> {

		@Override
		public JsonObject serialize(Gson gson, ReputationResult obj) {
			JsonObject json = new JsonObject();
			json.addProperty("constant", obj.constant);
			json.addProperty("altar", obj.altarMultiplier);
			return json;
		}

		@Override
		public ReputationResult deserialize(JsonObject json) {
			int constant = JSONUtils.getInt(json, "constant");
			float altar = JSONUtils.getFloat(json, "altar");
			return new ReputationResult(constant, altar);
		}

		@Override
		public void write(PacketBuffer buf, ReputationResult obj) {
			buf.writeVarInt(obj.constant);
			buf.writeFloat(obj.altarMultiplier);
		}

		@Override
		public ReputationResult read(PacketBuffer buf) {
			int constant = buf.readVarInt();
			float altar = buf.readFloat();
			return new ReputationResult(constant, altar);
		}
	}
}
