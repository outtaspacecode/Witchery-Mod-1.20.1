package net.outta_space.witchery.recipe;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.WitcheryMod;
import org.jetbrains.annotations.Nullable;

public class DistilleryRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final NonNullList<ItemStack> outputs;
    private final ResourceLocation id;
    private final int vesselCount;

    public DistilleryRecipe(ResourceLocation id, NonNullList<ItemStack> outputs, NonNullList<Ingredient> inputItems, int vesselCount) {
        this.inputItems = inputItems;
        this.outputs = outputs;
        this.id = id;
        this.vesselCount = vesselCount;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }


        if(inputItems.size() == 1) {
            return ((inputItems.get(0).test(pContainer.getItem(1))
                    && pContainer.getItem(2).isEmpty())

                || (inputItems.get(0).test(pContainer.getItem(2))
                    && pContainer.getItem(1).isEmpty()))

                    && vesselCount <= pContainer.getItem(0).getCount();
        }



        return ((inputItems.get(0).test(pContainer.getItem(1))
                && inputItems.get(1).test(pContainer.getItem(2)))

                || (inputItems.get(0).test(pContainer.getItem(2))
                && inputItems.get(1).test(pContainer.getItem(1))))

                && vesselCount <= pContainer.getItem(0).getCount();
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return outputs.get(0).copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return outputs.get(0).copy();
    }

    public NonNullList<ItemStack> getResultItemList() {
        return this.outputs;
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
        return Serializer.INSTANCE;
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

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            JsonArray outputItems = GsonHelper.getAsJsonArray(json, "outputs");
            NonNullList<ItemStack> outputs = NonNullList.withSize(outputItems.size(), ItemStack.EMPTY);

            for(int i = 0; i < outputs.size(); i++) {
                outputs.set(i, ShapedRecipe.itemStackFromJson(outputItems.get(i).getAsJsonObject()));
            }

            int vesselCount = GsonHelper.getAsInt(json, "vesselCount", 0);

            return new DistilleryRecipe(id, outputs, inputs, vesselCount);
        }

        @Override
        public @Nullable DistilleryRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            NonNullList<ItemStack> outputs = NonNullList.withSize(buf.readInt(), ItemStack.EMPTY);

            for(int i = 0; i < outputs.size(); i++) {
                outputs.set(i, buf.readItem());
            }

            int vesselCount = buf.readInt();
            return new DistilleryRecipe(id, outputs, inputs, vesselCount);
        }


        @Override
        public void toNetwork(FriendlyByteBuf buf, DistilleryRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeInt(recipe.getResultItemList().size());

            for(int i = 0; i < recipe.getResultItemList().size(); i++) {
                buf.writeItemStack(recipe.getResultItemList().get(i), false);
            }

            buf.writeInt(recipe.vesselCount);
        }
    }

}
