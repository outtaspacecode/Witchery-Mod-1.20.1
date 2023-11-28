package net.outta_space.witchery.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.recipe.WitchCauldronRecipe;

public class WitchCauldronCategory implements IRecipeCategory<WitchCauldronRecipe> {
    public static final ResourceLocation UID = new ResourceLocation(WitcheryMod.MOD_ID, "witch_cauldron");
    public static final ResourceLocation TEXTURE = new ResourceLocation(WitcheryMod.MOD_ID,
            "textures/gui/witch_cauldron_gui.png");

    public static final RecipeType<WitchCauldronRecipe> WITCH_CAULDRON_TYPE =
            new RecipeType<>(UID, WitchCauldronRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public WitchCauldronCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 10, 10, 140, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WITCH_CAULDRON.get()));
    }

    @Override
    public RecipeType<WitchCauldronRecipe> getRecipeType() {
        return WITCH_CAULDRON_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Witch Cauldron");
    }

    @Override
    public IDrawable getBackground() {
        return this.background;
    }

    @Override
    public IDrawable getIcon() {
        return this.icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, WitchCauldronRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        builder.addSlot(RecipeIngredientRole.INPUT, 4, 4).addIngredients(ingredients.get(0));
        if(ingredients.size() > 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 22, 4).addIngredients(ingredients.get(1));
        }
        if(ingredients.size() > 2) {
            builder.addSlot(RecipeIngredientRole.INPUT, 40, 4).addIngredients(ingredients.get(2));
        }
        if(ingredients.size() > 3) {
            builder.addSlot(RecipeIngredientRole.INPUT, 58, 4).addIngredients(ingredients.get(3));
        }
        if(ingredients.size() > 4) {
            builder.addSlot(RecipeIngredientRole.INPUT, 76, 4).addIngredients(ingredients.get(4));
        }

        builder.addSlot(RecipeIngredientRole.OUTPUT, 115, 33).addItemStack(recipe.getResultItem(null));
    }
}
