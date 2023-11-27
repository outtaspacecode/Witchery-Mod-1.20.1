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
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.recipe.DistilleryRecipe;

public class DistilleryCategory implements IRecipeCategory<DistilleryRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(WitcheryMod.MOD_ID, "distillery");
    public static final ResourceLocation TEXTURE = new ResourceLocation(WitcheryMod.MOD_ID,
            "textures/gui/distillery_gui.png");

    public static final RecipeType<DistilleryRecipe> DISTILLERY_TYPE =
            new RecipeType<>(UID, DistilleryRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public DistilleryCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 32, 13, 115, 60);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.DISTILLERY.get()));
    }

    @Override
    public RecipeType<DistilleryRecipe> getRecipeType() {
        return DISTILLERY_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Distillery");
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
    public void setRecipe(IRecipeLayoutBuilder builder, DistilleryRecipe recipe, IFocusGroup focuses) {
        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        builder.addSlot(RecipeIngredientRole.INPUT, 16, 3).addIngredients(ingredients.get(0));
        if(ingredients.size() > 1) {
            builder.addSlot(RecipeIngredientRole.INPUT, 16, 21).addIngredients(ingredients.get(1));
        } else {
            builder.addSlot(RecipeIngredientRole.INPUT, 16, 21).addIngredients(Ingredient.EMPTY);
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 16, 41).addItemStack(new ItemStack(ModItems.CLAY_VESSEL.get(), recipe.getVesselCount()));

        NonNullList<ItemStack> outputs = recipe.getResultItemList();
        builder.addSlot(RecipeIngredientRole.OUTPUT, 78, 3).addItemStack(outputs.get(0));
        if(outputs.size() > 1) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 3).addItemStack(outputs.get(1));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 3).addItemStack(ItemStack.EMPTY);
        }
        if(outputs.size() > 2) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 78, 21).addItemStack(outputs.get(2));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 78, 21).addItemStack(ItemStack.EMPTY);
        }
        if(outputs.size() > 3) {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 21).addItemStack(outputs.get(3));
        } else {
            builder.addSlot(RecipeIngredientRole.OUTPUT, 96, 21).addItemStack(ItemStack.EMPTY);
        }
    }
}
