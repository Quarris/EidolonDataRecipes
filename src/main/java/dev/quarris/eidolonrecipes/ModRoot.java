package dev.quarris.eidolonrecipes;

import dev.quarris.eidolonrecipes.registry.EidolonReflectedRegistries;
import dev.quarris.eidolonrecipes.registry.RecipeTypes;
import elucent.eidolon.recipe.CrucibleRecipe;
import elucent.eidolon.recipe.CrucibleRegistry;
import elucent.eidolon.recipe.WorktableRecipe;
import elucent.eidolon.recipe.WorktableRegistry;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.event.lifecycle.FMLLoadCompleteEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;

@Mod(ModRoot.ID)
public class ModRoot {

    public static final String ID = "eidolonrecipes";
    public static final Logger LOGGER = LogManager.getLogger();

    public ModRoot() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(this::onLoad);
        RecipeTypes.RECIPE_SERIALIZERS.register(bus);
    }

    public void onLoad(FMLLoadCompleteEvent event) {
        EidolonReflectedRegistries.onLoadComplete();
    }
}
