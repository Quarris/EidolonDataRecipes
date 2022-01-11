package dev.quarris.eidolonrecipes.mixin;

import elucent.eidolon.util.StackUtil;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(StackUtil.class)
public class StackUtilMixin {

    @Inject(method = "ingredientFromObject", at = @At(value = "RETURN", ordinal = 2), cancellable = true)
    private static void copyItemStackFromObject(Object object, CallbackInfoReturnable<Ingredient> cir) {
        cir.setReturnValue(Ingredient.fromStacks(((ItemStack)object).copy()));
    }

}
