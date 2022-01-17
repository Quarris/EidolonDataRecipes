package dev.quarris.eidolonrecipes.spells.result;

import dev.quarris.eidolonrecipes.spells.SpellInfo;
import dev.quarris.eidolonrecipes.spells.SpellRecipeWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpellResult {

	void onCast(SpellRecipeWrapper spell, World world, BlockPos pos, PlayerEntity caster, SpellInfo spellInfo);

    ResourceLocation getId();

}
