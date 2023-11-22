package net.outta_space.witchery.datagen;

import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeBlockTagsProvider;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.cropblock.SnowBellCropBlock;
import net.outta_space.witchery.util.ModTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator  extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider,
                                @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, WitcheryMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.WITCH_CAULDRON.get())
                .add(ModBlocks.WITCH_OVEN.get())
                .add(ModBlocks.DISTILLERY.get())
                .add(ModBlocks.ALTAR_BLOCK.get());

        this.tag(ModTags.Blocks.MINEABLE_WITH_BROOM)
                .add(ModBlocks.HEART_GLYPH.get())
                .add(ModBlocks.WHITE_CIRCLE_GLYPH.get())
                .add(ModBlocks.INFERNAL_CIRCLE_GLYPH.get())
                .add(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get());

        this.tag(BlockTags.LOGS_THAT_BURN)
                .add(ModBlocks.ROWAN_LOG.get())
                .add(ModBlocks.ROWAN_WOOD.get())
                .add(ModBlocks.STRIPPED_ROWAN_LOG.get())
                .add(ModBlocks.STRIPPED_ROWAN_WOOD.get())
                .add(ModBlocks.ALDER_LOG.get())
                .add(ModBlocks.ALDER_WOOD.get())
                .add(ModBlocks.STRIPPED_ALDER_LOG.get())
                .add(ModBlocks.STRIPPED_ALDER_WOOD.get())
                .add(ModBlocks.HAWTHORN_LOG.get())
                .add(ModBlocks.HAWTHORN_WOOD.get())
                .add(ModBlocks.STRIPPED_HAWTHORN_LOG.get())
                .add(ModBlocks.STRIPPED_HAWTHORN_WOOD.get());

        this.tag(BlockTags.PLANKS)
                .add(ModBlocks.ROWAN_PLANKS.get())
                .add(ModBlocks.ALDER_PLANKS.get())
                .add(ModBlocks.HAWTHORN_PLANKS.get());

        this.tag(BlockTags.CROPS)
                .add(ModBlocks.BELLADONNA_CROP.get())
                .add(ModBlocks.MANDRAKE_CROP.get())
                .add(ModBlocks.SNOWBELL_CROP.get())
                .add(ModBlocks.WATER_ARTICHOKE_CROP.get())
                .add(ModBlocks.WOLFSBANE_CROP.get())
                .add(ModBlocks.WORMWOOD_CROP.get());

        this.tag(BlockTags.SAPLINGS)
                .add(ModBlocks.ROWAN_SAPLING.get())
                .add(ModBlocks.ALDER_SAPLING.get())
                .add(ModBlocks.HAWTHORN_SAPLING.get());
    }

    @Override
    public String getName() {
        return "Block Tags";
    }
}
