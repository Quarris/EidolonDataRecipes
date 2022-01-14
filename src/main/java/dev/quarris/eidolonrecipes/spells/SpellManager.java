package dev.quarris.eidolonrecipes.spells;

import dev.quarris.eidolonrecipes.spells.requirements.ISpellRequirementSerializer;
import dev.quarris.eidolonrecipes.spells.results.ISpellResultSerializer;
import dev.quarris.eidolonrecipes.spells.types.ISpellTypeSerializer;
import dev.quarris.eidolonrecipes.spells.types.ISpellType;
import net.minecraft.util.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public class SpellManager {

	private static final Map<ResourceLocation, ISpellType> TYPES = new HashMap<>();
	private static final Map<ResourceLocation, ISpellTypeSerializer> SERIALIZERS = new HashMap<>();
	private static final Map<ResourceLocation, ISpellRequirementSerializer> REQUIREMENTS = new HashMap<>();
	private static final Map<ResourceLocation, ISpellResultSerializer> RESULTS = new HashMap<>();

	public static void registerType() {

	}

	public static void registerResult(ResourceLocation id, ) {

	}

}
