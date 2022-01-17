package dev.quarris.eidolonrecipes;

import dev.quarris.eidolonrecipes.registry.RecipeTypes;
import dev.quarris.eidolonrecipes.spells.SpellManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModRoot.ID)
public class ModRoot {

    public static final String ID = "eidolonrecipes";
    public static final Logger LOGGER = LogManager.getLogger();

    public static ResourceLocation eidolonRes(String name) {
        return new ResourceLocation("eidolon", name);
    }

    public ModRoot() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RecipeTypes.RECIPE_SERIALIZERS.register(bus);
        SpellManager.init();
    }
}
