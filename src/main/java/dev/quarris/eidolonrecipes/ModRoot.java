package dev.quarris.eidolonrecipes;

import dev.quarris.eidolonrecipes.registry.RecipeTypes;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

@Mod(ModRoot.ID)
public class ModRoot {

    public static final String ID = "eidolonrecipes";
    public static final Logger LOGGER = LogManager.getLogger();

    public ModRoot() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        RecipeTypes.RECIPE_SERIALIZERS.register(bus);
    }
}
