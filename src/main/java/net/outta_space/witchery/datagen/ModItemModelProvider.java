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
        simpleItem(ModItems.MUTANDIS);
        simpleItem(ModItems.MUTANDIS_EXTREMIS);
        modeledBlock(ModBlocks.WITCH_CAULDRON.get());
        modeledBlock(ModBlocks.DISTILLERY.get());
        modeledBlock(ModBlocks.WITCH_OVEN.get());
        modeledBlock(ModBlocks.INFINITY_EGG.get());
        simpleItem(ModItems.GOLDEN_CHALK);
        simpleItem(ModItems.RITUAL_CHALK);
        simpleItem(ModItems.INFERNAL_CHALK);
        simpleItem(ModItems.OTHERWHERE_CHALK);
        simpleItemForBlock(ModBlocks.HEART_GLYPH);
        simpleItemForBlock(ModBlocks.WHITE_CIRCLE_GLYPH, "8");
        simpleItemForBlock(ModBlocks.INFERNAL_CIRCLE_GLYPH, "7");
        simpleItemForBlock(ModBlocks.OTHERWHERE_CIRCLE_GLYPH, "5");
        handheldItem(ModItems.BROOM);
        simpleItem(ModItems.ATTUNED_STONE);
        simpleItem(ModItems.QUICKLIME);
        simpleItem(ModItems.GYPSUM);
        simpleItem(ModItems.DEMON_HEART);
        simpleItem(ModItems.REFINED_EVIL);
        simpleItem(ModItems.TAGLOCK_KIT);

        simpleItem(ModItems.WORMY_APPLE);
        simpleItem(ModItems.BONE_NEEDLE);
        simpleItem(ModItems.WAYSTONE);
        simpleItem(ModItems.ICY_NEEDLE);

        /**********************************************************************
         * Mob Drop Items
         ***********************************************************************/
        simpleItem(ModItems.CREEPER_HEART);
        simpleItem(ModItems.TONGUE_OF_DOG);
        simpleItem(ModItems.WOOL_OF_BAT);


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
        simpleItem(ModItems.PURIFIED_MILK);
        simpleItem(ModItems.DEMONIC_BLOOD);
        simpleItem(ModItems.DIAMOND_VAPOR);
        simpleItem(ModItems.MELLIFLUOUS_HUNGER);
        simpleItem(ModItems.OIL_OF_VITRIOL);
        simpleItem(ModItems.DROP_OF_LUCK);
        simpleItem(ModItems.ENDER_DEW);
        simpleItem(ModItems.TEAR_OF_THE_GODDESS);
        simpleItem(ModItems.FOCUSED_WILL);
        simpleItem(ModItems.CONDENSED_FEAR);

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

    private ItemModelBuilder handheldItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/handheld")).texture("layer0",
                new ResourceLocation(WitcheryMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItem(RegistryObject<Item> item) {
        return withExistingParent(item.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(WitcheryMod.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder simpleItemForBlock(RegistryObject<Block> block) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(WitcheryMod.MOD_ID, "block/" + block.getId().getPath()));
    }

    private ItemModelBuilder simpleItemForBlock(RegistryObject<Block> block, String index) {
        return withExistingParent(block.getId().getPath(),
                new ResourceLocation("item/generated")).texture("layer0",
                new ResourceLocation(WitcheryMod.MOD_ID, "block/" + block.getId().getPath() + index));
    }
}
