package net.outta_space.witchery.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.minecraftforge.common.data.GlobalLootModifierProvider;
import net.minecraftforge.common.loot.LootTableIdCondition;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.loot.AddItemModifier;
import net.outta_space.witchery.WitcheryMod;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output) {
        super(output, WitcheryMod.MOD_ID);
    }
    public static final float SEED_CHANCE = 0.15f;

    @Override
    protected void start() {

        add("belladonna_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.BELLADONNA_SEEDS.get()));
        add("belladonna_seeds_from_fern", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.BELLADONNA_SEEDS.get()));
        add("belladonna_seeds_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.BELLADONNA_SEEDS.get()));

        add("mandrake_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.MANDRAKE_SEEDS.get()));
        add("mandrake_seeds_from_fern", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.MANDRAKE_SEEDS.get()));
        add("mandrake_seeds_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.MANDRAKE_SEEDS.get()));

        add("water_artichoke_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WATER_ARTICHOKE_SEEDS.get()));
        add("water_artichoke_seeds_from_fern", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WATER_ARTICHOKE_SEEDS.get()));
        add("water_artichoke_seeds_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WATER_ARTICHOKE_SEEDS.get()));

        add("snowbell_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.SNOWBELL_SEEDS.get()));
        add("snowbell_seeds_from_fern", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.SNOWBELL_SEEDS.get()));
        add("snowbell_seeds_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.SNOWBELL_SEEDS.get()));

        add("wolfsbane_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WOLFSBANE_SEEDS.get()));
        add("wolfsbane_seeds_from_fern", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WOLFSBANE_SEEDS.get()));
        add("wolfsbane_seeds_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WOLFSBANE_SEEDS.get()));

        add("wormwood_seeds_from_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WORMWOOD_SEEDS.get()));
        add("wormwood_seeds_from_fern", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.FERN).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WORMWOOD_SEEDS.get()));
        add("wormwood_seeds_from_tall_grass", new AddItemModifier(new LootItemCondition[]{
                LootItemBlockStatePropertyCondition.hasBlockStateProperties(Blocks.TALL_GRASS).build(),
                LootItemRandomChanceCondition.randomChance(SEED_CHANCE).build()
        }, ModItems.WORMWOOD_SEEDS.get()));



        add("creeper_heart_from_creeper", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/creeper")).build(),
                LootItemRandomChanceCondition.randomChance(0.18f).build()
        }, ModItems.CREEPER_HEART.get()));
        add("tongue_from_dog", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/wolf")).build(),
                LootItemRandomChanceCondition.randomChance(0.4f).build()
        }, ModItems.TONGUE_OF_DOG.get()));
        add("wool_from_bat", new AddItemModifier(new LootItemCondition[]{
                new LootTableIdCondition.Builder(new ResourceLocation("entities/bat")).build(),
                LootItemRandomChanceCondition.randomChance(0.5f).build()
        }, ModItems.WOOL_OF_BAT.get()));


    }
}
