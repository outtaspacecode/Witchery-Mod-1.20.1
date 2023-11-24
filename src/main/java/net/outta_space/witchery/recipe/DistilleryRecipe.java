package net.outta_space.witchery.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.WitcheryMod;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class DistilleryRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack output;
    private final ResourceLocation id;
    private final int vesselCount;

    public DistilleryRecipe(ResourceLocation id, ItemStack output, NonNullList<Ingredient> inputItems, int vesselCount) {
        this.inputItems = inputItems;
        this.output = output;
        this.id = id;
        this.vesselCount = vesselCount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }

        if(inputItems.get(1).isEmpty()) {
            return inputItems.get(0).test(pContainer.getItem(1))
                || inputItems.get(0).test(pContainer.getItem(2));
        }

        return ((inputItems.get(0).test(pContainer.getItem(1))
                && inputItems.get(1).test(pContainer.getItem(2)))

                || (inputItems.get(0).test(pContainer.getItem(2))
                && inputItems.get(1).test(pContainer.getItem(1))))

                && vesselCount <= pContainer.getItem(0).getCount();
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return output.copy();
    }

    public int getVesselCount() { return vesselCount; }

    @Override
    public NonNullList<Ingredient> getIngredients() {
        return this.inputItems;
    }

    @Override
    public ResourceLocation getId() {
        return this.id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return null;
    }

    @Override
    public RecipeType<?> getType() {
        return Type.INSTANCE;
    }


    public static class Type implements RecipeType<DistilleryRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "distillery";
    }

    public static class Serializer implements RecipeSerializer<DistilleryRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(WitcheryMod.MOD_ID, "distillery");

        @Override
        public DistilleryRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack output = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "output"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(2, Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int vesselCount = GsonHelper.getAsInt(json, "vesselCount", 0);

            return new DistilleryRecipe(id, output, inputs, vesselCount);
        }

        @Override
        public @Nullable DistilleryRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack output = buf.readItem();

            int vesselCount = buf.readInt();
            return new DistilleryRecipe(id, output, inputs, vesselCount);
        }


        @Override
        public void toNetwork(FriendlyByteBuf buf, DistilleryRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeItemStack(recipe.getResultItem(null), false);

            buf.writeInt(recipe.vesselCount);
        }
    }

}
