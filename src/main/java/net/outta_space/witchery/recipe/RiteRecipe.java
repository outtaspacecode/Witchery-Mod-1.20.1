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


public class RiteRecipe implements Recipe<SimpleContainer> {

    private final NonNullList<Ingredient> inputItems;
    private final ItemStack riteKey;
    private final ResourceLocation id;
    private int smallCircle = 0;
    private int mediumCircle = 0;
    private int largeCircle = 0;

    public RiteRecipe(ResourceLocation id, ItemStack riteKey, NonNullList<Ingredient> inputItems,
                      int smallCircle, int mediumCircle, int largeCircle) {
        this.inputItems = inputItems;
        this.riteKey = riteKey;
        this.id = id;
        this.smallCircle = smallCircle;
        this.mediumCircle = mediumCircle;
        this.largeCircle = largeCircle;
    }

    @Override
    public boolean matches(SimpleContainer pContainer, Level pLevel) {
        if(pLevel.isClientSide()) {
            return false;
        }


        boolean match = false;
        boolean[] matches = new boolean[inputItems.size()];
        int count = 0;

        for(int i = 0; i < pContainer.getContainerSize(); i++) {
            if(pContainer.getItem(i) != ItemStack.EMPTY) {
                count++;
            }
        }

        if(inputItems.size() == count) {
            for (int i = 0; i < inputItems.size(); i++) {
                for (int j = 0; j < pContainer.getContainerSize(); j++) {
                    if (pContainer.getItem(j).getCount() == 1) {
                        match = match || inputItems.get(i).test(pContainer.getItem(j));
                    }
                }
                matches[i] = match;
                match = false;
            }

            if(inputItems.size() > 1) {
                for (int i = 0; i < inputItems.size() - 1; i++) {
                    match = matches[i] && matches[i + 1];
                }
            } else {
                match = matches[0];
            }
        }

        return match;
    }

    public int[] getCircles() {
        return new int[] {smallCircle, mediumCircle, largeCircle};
    }

    public boolean matchesCircles(int small, int medium, int large) {
        return (smallCircle <= 0 || smallCircle == small)
            && (mediumCircle <= 0 || mediumCircle == medium)
            && (largeCircle <= 0 || largeCircle == large);
    }

    @Override
    public ItemStack assemble(SimpleContainer pContainer, RegistryAccess pRegistryAccess) {
        return riteKey.copy();
    }

    @Override
    public boolean canCraftInDimensions(int pWidth, int pHeight) {
        return true;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess pRegistryAccess) {
        return riteKey.copy();
    }

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

    public static class Type implements RecipeType<RiteRecipe> {
        private Type() { }
        public static final Type INSTANCE = new Type();
        public static final String ID = "rite";
    }

    public static class Serializer implements RecipeSerializer<RiteRecipe> {
        public static final Serializer INSTANCE = new Serializer();
        public static final ResourceLocation ID =
                new ResourceLocation(WitcheryMod.MOD_ID, "rite");

        @Override
        public RiteRecipe fromJson(ResourceLocation id, JsonObject json) {
            ItemStack riteKey = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "rite_key"));

            JsonArray ingredients = GsonHelper.getAsJsonArray(json, "ingredients");
            NonNullList<Ingredient> inputs = NonNullList.withSize(ingredients.size(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromJson(ingredients.get(i)));
            }

            int smallCircle = GsonHelper.getAsInt(json, "small_circle", 0);
            int mediumCircle = GsonHelper.getAsInt(json, "medium_circle", 0);
            int largeCircle = GsonHelper.getAsInt(json, "large_circle", 0);

            return new RiteRecipe(id, riteKey, inputs, smallCircle, mediumCircle, largeCircle);
        }

        @Override
        public @Nullable RiteRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            NonNullList<Ingredient> inputs = NonNullList.withSize(buf.readInt(), Ingredient.EMPTY);

            for(int i = 0; i < inputs.size(); i++) {
                inputs.set(i, Ingredient.fromNetwork(buf));
            }

            ItemStack riteKey = buf.readItem();

            int smallCircle = buf.readInt();
            int mediumCircle = buf.readInt();
            int largeCircle = buf.readInt();

            return new RiteRecipe(id, riteKey, inputs, smallCircle, mediumCircle, largeCircle);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, RiteRecipe recipe) {
            buf.writeInt(recipe.getIngredients().size());

            for(Ingredient ing : recipe.getIngredients()) {
                ing.toNetwork(buf);
            }

            buf.writeItemStack(recipe.getResultItem(null), false);

            buf.writeInt(recipe.smallCircle);
            buf.writeInt(recipe.mediumCircle);
            buf.writeInt(recipe.largeCircle);
        }
    }
}
