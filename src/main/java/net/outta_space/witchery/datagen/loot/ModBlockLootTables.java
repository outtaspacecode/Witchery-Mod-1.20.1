package net.outta_space.witchery.datagen.loot;

import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.BelladonnaCropBlock;
import net.outta_space.witchery.block.custom.MandrakeCropBlock;
import net.outta_space.witchery.block.custom.WaterArtichokeCropBlock;
import net.outta_space.witchery.item.ModItems;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {super(Set.of(), FeatureFlags.REGISTRY.allFlags());}

    @Override
    protected void generate() {

        /***********************************************************************************
         * Basic block loot tables
         ************************************************************************************/
        this.dropSelf(ModBlocks.WITCH_CAULDRON.get());

        /***********************************************************************************
         * Crop block loot tables
         ************************************************************************************/

        ////////////////
        // BELLADONNA //
        ////////////////
        LootItemCondition.Builder lootitemcondition$builder1 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.BELLADONNA_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(BelladonnaCropBlock.AGE, 4));
        this.add(ModBlocks.BELLADONNA_CROP.get(), this.createCropDrops(ModBlocks.BELLADONNA_CROP.get(),
                ModItems.BELLADONNA_FLOWER.get(), ModItems.BELLADONNA_SEEDS.get(), lootitemcondition$builder1));

        //////////////
        // MANDRAKE //
        //////////////
        LootItemCondition.Builder lootitemcondition$builder2 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.MANDRAKE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(MandrakeCropBlock.AGE, 4));
        this.add(ModBlocks.MANDRAKE_CROP.get(), this.createCropDrops(ModBlocks.MANDRAKE_CROP.get(),
                ModItems.MANDRAKE_ROOT.get(), ModItems.MANDRAKE_SEEDS.get(), lootitemcondition$builder2));

        //////////////
        // MANDRAKE //
        //////////////
        LootItemCondition.Builder lootitemcondition$builder3 = LootItemBlockStatePropertyCondition.hasBlockStateProperties(ModBlocks.WATER_ARTICHOKE_CROP.get())
                .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(WaterArtichokeCropBlock.AGE, 4));
        this.add(ModBlocks.WATER_ARTICHOKE_CROP.get(), this.createCropDrops(ModBlocks.WATER_ARTICHOKE_CROP.get(),
                ModItems.WATER_ARTICHOKE.get(), ModItems.WATER_ARTICHOKE_SEEDS.get(), lootitemcondition$builder3));




        /***********************************************************************************
         * End of crop block loot tables
         ************************************************************************************/



        /***********************************************************************************
         * Tree and wood block loot tables
         ************************************************************************************/

        ///////////
        // ROWAN //
        ///////////
        this.dropSelf(ModBlocks.ROWAN_SAPLING.get());
        this.dropSelf(ModBlocks.ROWAN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ROWAN_LOG.get());
        this.dropSelf(ModBlocks.ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.ROWAN_PLANKS.get());
        this.add(ModBlocks.ROWAN_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ROWAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        ///////////
        // ALDER //
        ///////////
        this.dropSelf(ModBlocks.ALDER_SAPLING.get());
        this.dropSelf(ModBlocks.ALDER_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ALDER_LOG.get());
        this.dropSelf(ModBlocks.ALDER_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ALDER_WOOD.get());
        this.dropSelf(ModBlocks.ALDER_PLANKS.get());
        this.add(ModBlocks.ALDER_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ALDER_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        //////////////
        // HAWTHORN //
        //////////////
        this.dropSelf(ModBlocks.HAWTHORN_SAPLING.get());
        this.dropSelf(ModBlocks.HAWTHORN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_HAWTHORN_LOG.get());
        this.dropSelf(ModBlocks.HAWTHORN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_HAWTHORN_WOOD.get());
        this.dropSelf(ModBlocks.HAWTHORN_PLANKS.get());
        this.add(ModBlocks.HAWTHORN_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.HAWTHORN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        /***********************************************************************************
         * End of tree and wood block loot tables
         ************************************************************************************/

    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
