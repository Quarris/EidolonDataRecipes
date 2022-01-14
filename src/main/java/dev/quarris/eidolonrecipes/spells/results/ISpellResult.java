package dev.quarris.eidolonrecipes.spells.results;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.IWorld;

public interface ISpellResult {

	ResourceLocation getId();

	void onCast(IWorld world, PlayerEntity caster);


}
