package net.outta_space.witchery.recipe;

import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZER =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, WitcheryMod.MOD_ID);


    public static final RegistryObject<RecipeSerializer<WitchOvenRecipe>> WITCH_OVEN_SERIALIZER =
            SERIALIZER.register("witch_oven", () -> WitchOvenRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<DistilleryRecipe>> DISTILLERY_SERIALIZER =
            SERIALIZER.register("distillery", () -> DistilleryRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<WitchCauldronRecipe>> WITCH_CAULDRON_SERIALIZER =
            SERIALIZER.register("witch_cauldron", () -> WitchCauldronRecipe.Serializer.INSTANCE);

    public static final RegistryObject<RecipeSerializer<RiteRecipe>> RITE_SERIALIZER =
            SERIALIZER.register("rite", () -> RiteRecipe.Serializer.INSTANCE);


    public static void register(IEventBus eventBus) {
        SERIALIZER.register(eventBus);
    }
}
