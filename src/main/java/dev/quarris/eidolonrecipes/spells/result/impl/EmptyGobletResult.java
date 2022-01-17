package dev.quarris.eidolonrecipes.spells.result.impl;

import com.google.gson.JsonObject;
import dev.quarris.eidolonrecipes.ModRoot;
import dev.quarris.eidolonrecipes.spells.SpellInfo;
import dev.quarris.eidolonrecipes.spells.SpellRecipeWrapper;
import dev.quarris.eidolonrecipes.spells.result.ISpellResult;
import dev.quarris.eidolonrecipes.spells.result.ISpellResultSerializer;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EmptyGobletResult implements ISpellResult {
    public static final ResourceLocation ID = ModRoot.eidolonRes("empty_goblet");

    @Override
    public void onCast(SpellRecipeWrapper spell, World world, BlockPos pos, PlayerEntity caster, SpellInfo spellInfo) {
        spellInfo.goblet.ifPresent(goblet -> goblet.setEntityType(null));
    }

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    public static class Serializer implements ISpellResultSerializer<EmptyGobletResult> {

        @Override
        public void serialize(JsonObject json, EmptyGobletResult result) {

        }

        @Override
        public EmptyGobletResult deserialize(JsonObject json) {
            return new EmptyGobletResult();
        }

        @Override
        public void write(PacketBuffer buf, EmptyGobletResult obj) {

        }

        @Override
        public EmptyGobletResult read(PacketBuffer buf) {
            return new EmptyGobletResult();
        }

        @Override
        public ResourceLocation getId() {
            return ID;
        }
    }
}
