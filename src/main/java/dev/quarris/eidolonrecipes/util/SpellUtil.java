package dev.quarris.eidolonrecipes.util;

import elucent.eidolon.capability.ReputationProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ResourceLocation;

public class SpellUtil {

	public static double getReputation(PlayerEntity player, ResourceLocation deity) {
		return player.world.getCapability(ReputationProvider.CAPABILITY)
				.map(cap -> cap.getReputation(player, deity))
				.orElse(0D);
	}

	public static void addReputation(PlayerEntity player, ResourceLocation deity, double amount) {
		player.world.getCapability(ReputationProvider.CAPABILITY)
				.ifPresent(cap -> cap.addReputation(player, deity, amount));
	}

}
