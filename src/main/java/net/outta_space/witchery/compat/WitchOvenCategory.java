package net.outta_space.witchery.compat;

import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.client.Minecraft;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.recipe.DistilleryRecipe;
import net.outta_space.witchery.recipe.WitchOvenRecipe;

import java.util.List;
import java.util.Optional;

public class WitchOvenCategory implements IRecipeCategory<WitchOvenRecipe> {

    public static final ResourceLocation UID = new ResourceLocation(WitcheryMod.MOD_ID, "witch_oven");
    public static final ResourceLocation TEXTURE = new ResourceLocation(WitcheryMod.MOD_ID,
            "textures/gui/witch_oven_gui.png");

    public static final RecipeType<WitchOvenRecipe> WITCH_OVEN_TYPE =
            new RecipeType<>(UID, WitchOvenRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public WitchOvenCategory(IGuiHelper helper) {
        this.background = helper.createDrawable(TEXTURE, 53, 14, 86, 58);
        this.icon = helper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.WITCH_OVEN.get()));
    }

    @Override
    public RecipeType<WitchOvenRecipe> getRecipeType() {
        return WITCH_OVEN_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.literal("Witch Oven");
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
    public void setRecipe(IRecipeLayoutBuilder builder, WitchOvenRecipe recipe, IFocusGroup focuses) {
        SimpleContainer inventory = new SimpleContainer(1);
        inventory.setItem(0, recipe.getIngredients().get(0).getItems()[0]);
        Optional<SmeltingRecipe> smeltingRecipe = Minecraft.getInstance().level.getRecipeManager().getRecipeFor(net.minecraft.world.item.crafting.RecipeType.SMELTING, inventory, Minecraft.getInstance().level);

        NonNullList<Ingredient> ingredients = recipe.getIngredients();
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 3).addIngredients(ingredients.get(0));

        builder.addSlot(RecipeIngredientRole.INPUT, 30, 39).addItemStack(new ItemStack(ModItems.CLAY_VESSEL.get(), 1));
        builder.addSlot(RecipeIngredientRole.INPUT, 3, 39).addIngredients(Ingredient.EMPTY);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 65, 39).addItemStack(recipe.getResultItem(null));
        builder.addSlot(RecipeIngredientRole.OUTPUT, 65, 7).addItemStack(smeltingRecipe.get().getResultItem(null));
    }
}
