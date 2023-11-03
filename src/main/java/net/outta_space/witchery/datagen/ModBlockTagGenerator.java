package net.outta_space.witchery.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.BlockCollisions;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator  extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WitcheryMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROWAN_LOG.get())
                .add(ModBlocks.ROWAN_WOOD.get())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ROWAN_PLANKS.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ALDER_LOG.get())
                .add(ModBlocks.ALDER_WOOD.get())
                .add(ModBlocks.STRIPPED_ALDER_LOG.get())
                .add(ModBlocks.STRIPPED_ALDER_WOOD.get());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ALDER_PLANKS.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.HAWTHORN_LOG.get())
                .add(ModBlocks.HAWTHORN_WOOD.get())
                .add(ModBlocks.STRIPPED_HAWTHORN_LOG.get())
                .add(ModBlocks.STRIPPED_HAWTHORN_WOOD.get());
        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.HAWTHORN_PLANKS.get());
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
