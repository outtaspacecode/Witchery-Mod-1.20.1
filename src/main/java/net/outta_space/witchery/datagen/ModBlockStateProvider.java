package net.outta_space.witchery.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WitcheryMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        logBlock((RotatedPillarBlock) ModBlocks.ROWAN_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.ROWAN_WOOD.get(), blockTexture(ModBlocks.ROWAN_LOG.get()), blockTexture(ModBlocks.ROWAN_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ROWAN_LOG.get(), new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_rowan_log"),
                new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_rowan_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ROWAN_WOOD.get(), new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_rowan_log"),
                new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_rowan_log"));
        blockItem(ModBlocks.ROWAN_LOG);
        blockItem(ModBlocks.ROWAN_WOOD);
        blockItem(ModBlocks.STRIPPED_ROWAN_LOG);
        blockItem(ModBlocks.STRIPPED_ROWAN_WOOD);
        blockWithItem(ModBlocks.ROWAN_PLANKS);
        blockWithItem(ModBlocks.ROWAN_LEAVES);
        saplingBlock(ModBlocks.ROWAN_SAPLING);

        logBlock((RotatedPillarBlock) ModBlocks.ALDER_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.ALDER_WOOD.get(), blockTexture(ModBlocks.ALDER_LOG.get()), blockTexture(ModBlocks.ALDER_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ALDER_LOG.get(), new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_alder_log"),
                new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_alder_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_ALDER_WOOD.get(), new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_alder_log"),
                new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_alder_log"));
        blockItem(ModBlocks.ALDER_LOG);
        blockItem(ModBlocks.ALDER_WOOD);
        blockItem(ModBlocks.STRIPPED_ALDER_LOG);
        blockItem(ModBlocks.STRIPPED_ALDER_WOOD);
        blockWithItem(ModBlocks.ALDER_PLANKS);
        blockWithItem(ModBlocks.ALDER_LEAVES);
        saplingBlock(ModBlocks.ALDER_SAPLING);

        logBlock((RotatedPillarBlock) ModBlocks.HAWTHORN_LOG.get());
        axisBlock((RotatedPillarBlock) ModBlocks.HAWTHORN_WOOD.get(), blockTexture(ModBlocks.HAWTHORN_LOG.get()), blockTexture(ModBlocks.HAWTHORN_LOG.get()));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_HAWTHORN_LOG.get(), new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_hawthorn_log"),
                new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_hawthorn_log_top"));
        axisBlock((RotatedPillarBlock) ModBlocks.STRIPPED_HAWTHORN_WOOD.get(), new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_hawthorn_log"),
                new ResourceLocation(WitcheryMod.MOD_ID, "block/stripped_hawthorn_log"));
        blockItem(ModBlocks.HAWTHORN_LOG);
        blockItem(ModBlocks.HAWTHORN_WOOD);
        blockItem(ModBlocks.STRIPPED_HAWTHORN_LOG);
        blockItem(ModBlocks.STRIPPED_HAWTHORN_WOOD);
        blockWithItem(ModBlocks.HAWTHORN_PLANKS);
        blockWithItem(ModBlocks.HAWTHORN_LEAVES);
        saplingBlock(ModBlocks.HAWTHORN_SAPLING);



    }

    private void leavesBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cubeAll(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void blockWithItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockWithItem(blockRegistryObject.get(), cubeAll(blockRegistryObject.get()));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject, String appendix) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("witchery:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath() + appendix));
    }

    private void blockItem(RegistryObject<Block> blockRegistryObject) {
        simpleBlockItem(blockRegistryObject.get(), new ModelFile.UncheckedModelFile("witchery:block/" + ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath()));
    }
}
