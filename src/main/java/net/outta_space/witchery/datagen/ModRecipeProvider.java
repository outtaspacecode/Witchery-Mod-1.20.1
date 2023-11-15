package net.outta_space.witchery.datagen;

import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.core.Holder;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.Potions;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.fml.common.Mod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private static final List<ItemLike> WITCHERY_SMELTABLE_SAPLINGS = List.of(ModBlocks.ROWAN_SAPLING.get(),
            ModBlocks.ALDER_SAPLING.get(), ModBlocks.HAWTHORN_SAPLING.get(), Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING,
            Blocks.BIRCH_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.DARK_OAK_SAPLING, Blocks.CHERRY_SAPLING);

    public ModRecipeProvider(PackOutput pOutput) {
        super(pOutput);
    }

    @Override
    protected void buildRecipes(Consumer<FinishedRecipe> pWriter) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RAW_CLAY_VESSEL.get(), 4)
                .pattern(" A ")
                .pattern("AAA")
                .define('A', Items.CLAY_BALL)
                .unlockedBy("has_clay_ball", inventoryTrigger(ItemPredicate.Builder.item().of(Items.CLAY_BALL).build()))
                .save(pWriter);

        oreSmelting(pWriter, List.of(ModItems.RAW_CLAY_VESSEL.get()), RecipeCategory.MISC, ModItems.CLAY_VESSEL.get(), 0.3f, 200, "clay_vessel");
        oreSmelting(pWriter, WITCHERY_SMELTABLE_SAPLINGS, RecipeCategory.MISC, ModItems.WOOD_ASH.get(), 0.1f, 200, "wood_ash");

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.ANOINTING_PASTE.get())
                .requires(ModItems.WATER_ARTICHOKE_SEEDS.get())
                .requires(ModItems.MANDRAKE_SEEDS.get())
                .requires(ModItems.BELLADONNA_SEEDS.get())
                .requires(ModItems.SNOWBELL_SEEDS.get())
                .unlockedBy("has_belladonna_seeds", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.BELLADONNA_SEEDS.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.BROOM.get())
                .pattern(" A ")
                .pattern(" A ")
                .pattern("BBB")
                .define('A', Items.STICK)
                .define('B', ModBlocks.HAWTHORN_SAPLING.get())
                .unlockedBy("has_hawthorn_sapling", inventoryTrigger(ItemPredicate.Builder.item().of(ModBlocks.HAWTHORN_SAPLING.get()).build()))
                .save(pWriter);


        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.DISTILLERY.get())
                .pattern("ABA")
                .pattern("BBB")
                .pattern("CDC")
                .define('A', ModItems.CLAY_VESSEL.get())
                .define('B', Items.IRON_INGOT)
                .define('C', Items.GOLD_INGOT)
                .define('D', ModItems.ATTUNED_STONE.get())
                .unlockedBy("has_attuned_stone", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.ATTUNED_STONE.get()).build()))
                .save(pWriter);
    }
}
