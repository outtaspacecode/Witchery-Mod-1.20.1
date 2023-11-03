package net.outta_space.witchery.datagen.loot;

import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.world.flag.FeatureFlagSet;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.block.ModBlocks;

import java.util.Set;

public class ModBlockLootTables extends BlockLootSubProvider {
    public ModBlockLootTables() {super(Set.of(), FeatureFlags.REGISTRY.allFlags());}

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.ROWAN_SAPLING.get());
        this.dropSelf(ModBlocks.ROWAN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ROWAN_LOG.get());
        this.dropSelf(ModBlocks.ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ROWAN_WOOD.get());
        this.dropSelf(ModBlocks.ROWAN_PLANKS.get());
        this.add(ModBlocks.ROWAN_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ROWAN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(ModBlocks.ALDER_SAPLING.get());
        this.dropSelf(ModBlocks.ALDER_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_ALDER_LOG.get());
        this.dropSelf(ModBlocks.ALDER_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_ALDER_WOOD.get());
        this.dropSelf(ModBlocks.ALDER_PLANKS.get());
        this.add(ModBlocks.ALDER_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.ALDER_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));

        this.dropSelf(ModBlocks.HAWTHORN_SAPLING.get());
        this.dropSelf(ModBlocks.HAWTHORN_LOG.get());
        this.dropSelf(ModBlocks.STRIPPED_HAWTHORN_LOG.get());
        this.dropSelf(ModBlocks.HAWTHORN_WOOD.get());
        this.dropSelf(ModBlocks.STRIPPED_HAWTHORN_WOOD.get());
        this.dropSelf(ModBlocks.HAWTHORN_PLANKS.get());
        this.add(ModBlocks.HAWTHORN_LEAVES.get(), block ->
                createLeavesDrops(block, ModBlocks.HAWTHORN_SAPLING.get(), NORMAL_LEAVES_SAPLING_CHANCES));
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
