package net.outta_space.witchery.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.cropblock.*;

import java.util.function.Function;

public class ModBlockStateProvider extends BlockStateProvider {
    public ModBlockStateProvider(PackOutput output, ExistingFileHelper exFileHelper) {
        super(output, WitcheryMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {

        /**********************************************************************
         * Basic items
         ***********************************************************************/
        carpetBlock(ModBlocks.HEART_GLYPH);

        /**********************************************************************
         * Tree and wood items
         ***********************************************************************/

        ///////////
        // ROWAN //
        ///////////
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
        saplingBlock(ModBlocks.ROWAN_SAPLING);

        ///////////
        // ALDER //
        ///////////
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
        saplingBlock(ModBlocks.ALDER_SAPLING);

        //////////////
        // HAWTHORN //
        //////////////
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
        saplingBlock(ModBlocks.HAWTHORN_SAPLING);

        /**********************************************************************
         * End of tree and wood items
         ***********************************************************************/

        /**********************************************************************
         * Crop items
         ***********************************************************************/

        makeCrop((BelladonnaCropBlock) ModBlocks.BELLADONNA_CROP.get(), "belladonna_stage", "belladonna_stage", false);
        makeCrop((MandrakeCropBlock) ModBlocks.MANDRAKE_CROP.get(), "mandrake_stage", "mandrake_stage", false);
        makeCrop((WaterArtichokeCropBlock) ModBlocks.WATER_ARTICHOKE_CROP.get(), "water_artichoke_stage", "water_artichoke_stage", false);
        makeCrop((SnowBellCropBlock) ModBlocks.SNOWBELL_CROP.get(), "snowbell_stage", "snowbell_stage", true);
        makeCrop((WolfsbaneCropBlock) ModBlocks.WOLFSBANE_CROP.get(), "wolfsbane_stage", "wolfsbane_stage", true);
        makeCrop((WormwoodCropBlock) ModBlocks.WORMWOOD_CROP.get(), "wormwood_stage", "wormwood_stage", true);


        /**********************************************************************
         * End of crop items
         ***********************************************************************/


    }

    public void makeCrop(CropBlock block, String modelName, String textureName, boolean isCross) {
        Function<BlockState, ConfiguredModel[]> function = state -> states(state, block, modelName, textureName, isCross);

        getVariantBuilder(block).forAllStates(function);
    }

    private ConfiguredModel[] states(BlockState state, CropBlock block, String modelName, String textureName, boolean isCross) {
        ConfiguredModel[] models = new ConfiguredModel[1];
        if(isCross) {
            models[0] = new ConfiguredModel(models().cross(modelName + state.getValue(((WitcheryCropBlock) block).getAgeProperty()),
                    new ResourceLocation(WitcheryMod.MOD_ID, "block/" + textureName + state.getValue(((WitcheryCropBlock) block).getAgeProperty()))).renderType("cutout"));
        } else {
            models[0] = new ConfiguredModel(models().crop(modelName + state.getValue(((WitcheryCropBlock) block).getAgeProperty()),
                    new ResourceLocation(WitcheryMod.MOD_ID, "block/" + textureName + state.getValue(((WitcheryCropBlock) block).getAgeProperty()))).renderType("cutout"));
        }

        return models;
    }

    private void saplingBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().cross(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
    }

    private void carpetBlock(RegistryObject<Block> blockRegistryObject) {
        simpleBlock(blockRegistryObject.get(),
                models().carpet(ForgeRegistries.BLOCKS.getKey(blockRegistryObject.get()).getPath(), blockTexture(blockRegistryObject.get())).renderType("cutout"));
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
