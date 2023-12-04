package net.outta_space.witchery.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModItemTagGenerator extends ItemTagsProvider {

    public ModItemTagGenerator(PackOutput pOutput, CompletableFuture<HolderLookup.Provider> pLookupProvider,
                               CompletableFuture<TagLookup<Block>> pBlockTags, @Nullable ExistingFileHelper existingFileHelper) {
        super(pOutput, pLookupProvider, pBlockTags, WitcheryMod.MOD_ID, existingFileHelper);

}

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(ItemTags.PLANKS)
                .add(ModBlocks.ROWAN_PLANKS.get().asItem())
                .add(ModBlocks.ALDER_PLANKS.get().asItem())
                .add(ModBlocks.HAWTHORN_PLANKS.get().asItem());

        this.tag(ItemTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROWAN_LOG.get().asItem())
                .add(ModBlocks.ROWAN_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get().asItem())
                .add(ModBlocks.ALDER_LOG.get().asItem())
                .add(ModBlocks.ALDER_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ALDER_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ALDER_WOOD.get().asItem())
                .add(ModBlocks.HAWTHORN_LOG.get().asItem())
                .add(ModBlocks.HAWTHORN_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_HAWTHORN_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_HAWTHORN_WOOD.get().asItem());

        this.tag(ItemTags.SAPLINGS)
                .add(ModBlocks.ROWAN_SAPLING.get().asItem())
                .add(ModBlocks.ALDER_SAPLING.get().asItem())
                .add(ModBlocks.HAWTHORN_SAPLING.get().asItem());

        this.tag(ModTags.Items.ROWAN_LOGS)
                .add(ModBlocks.ROWAN_LOG.get().asItem())
                .add(ModBlocks.ROWAN_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get().asItem());

        this.tag(ModTags.Items.ALDER_LOGS)
                .add(ModBlocks.ALDER_LOG.get().asItem())
                .add(ModBlocks.ALDER_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ALDER_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ALDER_WOOD.get().asItem());

        this.tag(ModTags.Items.HAWTHORN_LOGS)
                .add(ModBlocks.HAWTHORN_LOG.get().asItem())
                .add(ModBlocks.HAWTHORN_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_HAWTHORN_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_HAWTHORN_WOOD.get().asItem());

        this.tag(ItemTags.LOGS)
                .add(ModBlocks.ROWAN_LOG.get().asItem())
                .add(ModBlocks.ROWAN_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get().asItem())
                .add(ModBlocks.ALDER_LOG.get().asItem())
                .add(ModBlocks.ALDER_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_ALDER_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_ALDER_WOOD.get().asItem())
                .add(ModBlocks.HAWTHORN_LOG.get().asItem())
                .add(ModBlocks.HAWTHORN_WOOD.get().asItem())
                .add(ModBlocks.STRIPPED_HAWTHORN_LOG.get().asItem())
                .add(ModBlocks.STRIPPED_HAWTHORN_WOOD.get().asItem());

        this.tag(ItemTags.LEAVES)
                .add(ModBlocks.ROWAN_LEAVES.get().asItem())
                .add(ModBlocks.ALDER_LEAVES.get().asItem())
                .add(ModBlocks.HAWTHORN_LEAVES.get().asItem());

        this.tag(ItemTags.VILLAGER_PLANTABLE_SEEDS)
                .add(ModItems.BELLADONNA_SEEDS.get())
                .add(ModItems.MANDRAKE_SEEDS.get())
                .add(ModItems.SNOWBELL_SEEDS.get())
                .add(ModItems.WOLFSBANE_SEEDS.get())
                .add(ModItems.WORMWOOD_SEEDS.get());

    }
}
