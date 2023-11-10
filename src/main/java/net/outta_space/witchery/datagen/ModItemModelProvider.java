package net.outta_space.witchery.datagen;

import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, WitcheryMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

        /**********************************************************************
         * Basic Items
         ***********************************************************************/
        simpleItem(ModItems.WOOD_ASH);
        simpleItem(ModItems.RAW_CLAY_VESSEL);
        simpleItem(ModItems.CLAY_VESSEL);
        simpleItem(ModItems.ANOINTING_PASTE);
        modeledBlock(ModBlocks.WITCH_CAULDRON.get());

        /**********************************************************************
         * Crop Items
         ***********************************************************************/
        simpleItem(ModItems.BELLADONNA_SEEDS);
        simpleItem(ModItems.BELLADONNA_FLOWER);
        simpleItem(ModItems.MANDRAKE_SEEDS);
        simpleItem(ModItems.MANDRAKE_ROOT);
        simpleItem(ModItems.WATER_ARTICHOKE_SEEDS);
        simpleItem(ModItems.WATER_ARTICHOKE);
        simpleItem(ModItems.SNOWBELL_SEEDS);
        simpleItem(ModItems.WOLFSBANE_SEEDS);
        simpleItem(ModItems.WOLFSBANE);
        simpleItem(ModItems.WORMWOOD_SEEDS);
        simpleItem(ModItems.WORMWOOD);

        /**********************************************************************
         * Bottled Magic Items
         ***********************************************************************/
        simpleItem(ModItems.EXHALE_OF_THE_HORNED_ONE);
        simpleItem(ModItems.HINT_OF_REBIRTH);
        simpleItem(ModItems.BREATH_OF_THE_GODDESS);
        simpleItem(ModItems.FOUL_FUME);
        simpleItem(ModItems.WHIFF_OF_MAGIC);
        simpleItem(ModItems.REEK_OF_MISFORTUNE);
        simpleItem(ModItems.ODOUR_OF_PURITY);

        /**********************************************************************
         * Sapling Items
         ***********************************************************************/
        saplingItem(ModBlocks.ROWAN_SAPLING);
        saplingItem(ModBlocks.ALDER_SAPLING);
        saplingItem(ModBlocks.HAWTHORN_SAPLING);
    }

    private ItemModelBuilder modeledBlock(Block block) {
        return withExistingParent(ForgeRegistries.BLOCKS.getKey(block).getPath(), new ResourceLocation(WitcheryMod.MOD_ID,
                "block/" + ForgeRegistries.BLOCKS.getKey(block).getPath()));
    }

    private ItemModelBuilder saplingItem(RegistryObject<Block> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(WitcheryMod.MOD_ID, "block/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(WitcheryMod.MOD_ID, "item/" + item.getId().getPath()));
    }
}
