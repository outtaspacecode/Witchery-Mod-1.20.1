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
import net.outta_space.witchery.datagen.custom.WitchOvenRecipeBuilder;
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
//        oreSmelting(pWriter, WITCHERY_SMELTABLE_SAPLINGS, RecipeCategory.MISC, ModItems.WOOD_ASH.get(), 0.1f, 200, "wood_ash");

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

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.BONE_NEEDLE.get(), 8)
                .requires(Items.BONE)
                .requires(Items.FLINT)
                .unlockedBy("has_bone", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.BONE).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WAYSTONE.get())
                .requires(Items.FLINT)
                .requires(ModItems.BONE_NEEDLE.get())
                .unlockedBy("has_bone_needle", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.BONE_NEEDLE.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.WORMY_APPLE.get())
                .requires(Items.APPLE)
                .requires(Items.ROTTEN_FLESH)
                .requires(Items.SUGAR)
                .unlockedBy("has_apple", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(Items.APPLE).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ATTUNED_STONE.get())
                .pattern("A")
                .pattern("B")
                .pattern("C")
                .define('A', ModItems.WHIFF_OF_MAGIC.get())
                .define('B', Items.DIAMOND)
                .define('C', Items.LAVA_BUCKET)
                .unlockedBy("has_diamond", inventoryTrigger(ItemPredicate.Builder.item().of(Items.DIAMOND).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, Items.GUNPOWDER, 5)
                .requires(ModItems.CREEPER_HEART.get())
                .unlockedBy("has_creeper_heart", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.CREEPER_HEART.get()).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.WITCH_OVEN.get())
                .pattern(" A ")
                .pattern("BBB")
                .pattern("BAB")
                .define('A', Items.IRON_BARS)
                .define('B', Items.IRON_INGOT)
                .unlockedBy("has_iron_ingot", inventoryTrigger(ItemPredicate.Builder.item().of(Items.IRON_INGOT).build()))
                .save(pWriter);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.RITUAL_CHALK.get())
                .pattern("ABA")
                .pattern("ACA")
                .pattern("ACA")
                .define('A', ModItems.WOOD_ASH.get())
                .define('B', ModItems.TEAR_OF_THE_GODDESS.get())
                .define('C', ModItems.GYPSUM.get())
                .unlockedBy("has_tear_of_the_goddess", inventoryTrigger(ItemPredicate.Builder.item().of(ModItems.TEAR_OF_THE_GODDESS.get()).build()))
                .save(pWriter);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.QUICKLIME.get())
                .requires(ModItems.WOOD_ASH.get())
                .unlockedBy("has_wood_ash", inventoryTrigger(ItemPredicate.Builder.item()
                        .of(ModItems.WOOD_ASH.get()).build()))
                .save(pWriter);

        new WitchOvenRecipeBuilder(Blocks.OAK_SAPLING, ModItems.EXHALE_OF_THE_HORNED_ONE.get())
                .unlockedBy("has_oak_sapling", has(Blocks.OAK_SAPLING))
                .save(pWriter);
        new WitchOvenRecipeBuilder(Blocks.SPRUCE_SAPLING, ModItems.HINT_OF_REBIRTH.get())
                .unlockedBy("has_spruce_sapling", has(Blocks.SPRUCE_SAPLING))
                .save(pWriter);
        new WitchOvenRecipeBuilder(Blocks.BIRCH_SAPLING, ModItems.BREATH_OF_THE_GODDESS.get())
                .unlockedBy("has_birch_sapling", has(Blocks.BIRCH_SAPLING))
                .save(pWriter);
        new WitchOvenRecipeBuilder(Blocks.JUNGLE_SAPLING, ModItems.FOUL_FUME.get())
                .unlockedBy("has_jungle_sapling", has(Blocks.JUNGLE_SAPLING))
                .save(pWriter);
        new WitchOvenRecipeBuilder(ModBlocks.ROWAN_SAPLING.get(), ModItems.WHIFF_OF_MAGIC.get())
                .unlockedBy("has_rowan_sapling", has(ModBlocks.ROWAN_SAPLING.get()))
                .save(pWriter);
        new WitchOvenRecipeBuilder(ModBlocks.ALDER_SAPLING.get(), ModItems.REEK_OF_MISFORTUNE.get())
                .unlockedBy("has_alder_sapling", has(ModBlocks.ALDER_SAPLING.get()))
                .save(pWriter);
        new WitchOvenRecipeBuilder(ModBlocks.HAWTHORN_SAPLING.get(), ModItems.ODOUR_OF_PURITY.get())
                .unlockedBy("has_hawthorn_sapling", has(ModBlocks.HAWTHORN_SAPLING.get()))
                .save(pWriter);
    }
}
