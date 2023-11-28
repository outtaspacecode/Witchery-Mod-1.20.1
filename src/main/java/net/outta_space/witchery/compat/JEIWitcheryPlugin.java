package net.outta_space.witchery.compat;


import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IGuiHandlerRegistration;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeManager;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.recipe.DistilleryRecipe;
import net.outta_space.witchery.recipe.WitchCauldronRecipe;
import net.outta_space.witchery.recipe.WitchOvenRecipe;
import net.outta_space.witchery.screen.DistilleryScreen;
import net.outta_space.witchery.screen.WitchOvenScreen;

import java.util.List;

@JeiPlugin
public class JEIWitcheryPlugin implements IModPlugin {


    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(WitcheryMod.MOD_ID, "jei_plugin");
    }


    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new DistilleryCategory(
                registration.getJeiHelpers().getGuiHelper()
        ));

        registration.addRecipeCategories(new WitchOvenCategory(
                registration.getJeiHelpers().getGuiHelper()
        ));

        registration.addRecipeCategories(new WitchCauldronCategory(
                registration.getJeiHelpers().getGuiHelper()
        ));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        RecipeManager recipeManager = Minecraft.getInstance().level.getRecipeManager();
        List<DistilleryRecipe> distilleryRecipes = recipeManager.getAllRecipesFor(DistilleryRecipe.Type.INSTANCE);
        registration.addRecipes(DistilleryCategory.DISTILLERY_TYPE, distilleryRecipes);
        List<WitchOvenRecipe> witchOvenRecipes = recipeManager.getAllRecipesFor(WitchOvenRecipe.Type.INSTANCE);
        registration.addRecipes(WitchOvenCategory.WITCH_OVEN_TYPE, witchOvenRecipes);
        List<WitchCauldronRecipe> witchCauldronRecipes = recipeManager.getAllRecipesFor(WitchCauldronRecipe.Type.INSTANCE);
        registration.addRecipes(WitchCauldronCategory.WITCH_CAULDRON_TYPE, witchCauldronRecipes);
    }

    @Override
    public void registerGuiHandlers(IGuiHandlerRegistration registration) {
        registration.addRecipeClickArea(DistilleryScreen.class, 68, 17, 37, 31,
                DistilleryCategory.DISTILLERY_TYPE);
        registration.addRecipeClickArea(WitchOvenScreen.class, 80, 21, 22, 15,
                WitchOvenCategory.WITCH_OVEN_TYPE);
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.DISTILLERY.get().asItem()), DistilleryCategory.DISTILLERY_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WITCH_OVEN.get().asItem()), WitchOvenCategory.WITCH_OVEN_TYPE);
        registration.addRecipeCatalyst(new ItemStack(ModBlocks.WITCH_CAULDRON.get().asItem()), WitchCauldronCategory.WITCH_CAULDRON_TYPE);
    }
}
