package dev.quarris.eidolonrecipes.spells.requirement;

import dev.quarris.eidolonrecipes.spells.SpellInfo;
import dev.quarris.eidolonrecipes.spells.SpellRecipeWrapper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface ISpellRequirement {

    boolean canCast(SpellRecipeWrapper spell, World world, BlockPos pos, PlayerEntity caster, SpellInfo spellInfo);

    ResourceLocation getId();
}
