package net.outta_space.witchery.block;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.custom.*;
import net.outta_space.witchery.block.custom.cropblock.*;
import net.outta_space.witchery.item.ModItems;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, WitcheryMod.MOD_ID);

    /*******************************************************************************************
     * Basic Blocks
     ********************************************************************************************/
    public static final RegistryObject<Block> WITCH_CAULDRON = registerBlock("witch_cauldron",
            () -> new WitchCauldronBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON).noOcclusion()));
    public static final RegistryObject<Block> WITCH_OVEN = registerBlock("witch_oven",
            () -> new WitchOvenBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON).noOcclusion()));
    public static final RegistryObject<Block> DISTILLERY = registerBlock("distillery",
            () -> new DistilleryBlock(BlockBehaviour.Properties.copy(Blocks.CAULDRON).noOcclusion()));

    public static final RegistryObject<Block> HEART_GLYPH = registerBlock("heart_glyph",
            () -> new HeartGlyphBlock(BlockBehaviour.Properties.copy(Blocks.YELLOW_CARPET).strength(2f).sound(SoundType.STONE).noOcclusion().noCollission()));
    public static final RegistryObject<Block> WHITE_CIRCLE_GLYPH = registerBlock("white_circle_glyph",
            () -> new GlyphBlock(BlockBehaviour.Properties.copy(Blocks.WHITE_CARPET).strength(2f).sound(SoundType.STONE).noOcclusion().noCollission()));
    public static final RegistryObject<Block> INFERNAL_CIRCLE_GLYPH = registerBlock("infernal_circle_glyph",
            () -> new GlyphBlock(BlockBehaviour.Properties.copy(Blocks.RED_CARPET).strength(2f).sound(SoundType.STONE).noOcclusion().noCollission()));
    public static final RegistryObject<Block> OTHERWHERE_CIRCLE_GLYPH = registerBlock("otherwhere_circle_glyph",
            () -> new GlyphBlock(BlockBehaviour.Properties.copy(Blocks.PURPLE_CARPET).strength(2f).sound(SoundType.STONE).noOcclusion().noCollission()));

    public static final RegistryObject<Block> ALTAR_BLOCK = registerBlock("altar_block",
            () -> new AltarBlock(BlockBehaviour.Properties.copy(Blocks.STONE).explosionResistance(1200.0f)));

    public static final RegistryObject<Block> INFINITY_EGG = registerBlock("infinity_egg",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DRAGON_EGG)));

    /*******************************************************************************************
     * Crop Blocks:
     *      - Belladonna
     *      - Mandrake
     *      - Water Artichoke
     *      - Snowbell
     *      - Wolfsbane
     *      - Wormwood
     ********************************************************************************************/

    ////////////////
    // BELLADONNA //
    ////////////////
    public static final RegistryObject<Block> BELLADONNA_CROP = BLOCKS.register("belladonna_crop",
            () -> new BelladonnaCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));


    //////////////
    // MANDRAKE //
    //////////////
    public static final RegistryObject<Block> MANDRAKE_CROP = BLOCKS.register("mandrake_crop",
            () -> new MandrakeCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    /////////////////////
    // WATER ARTICHOKE //
    /////////////////////
    public static final RegistryObject<Block> WATER_ARTICHOKE_CROP = BLOCKS.register("water_artichoke_crop",
            () -> new WaterArtichokeCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    //////////////
    // SNOWBELL //
    //////////////
    public static final RegistryObject<Block> SNOWBELL_CROP = BLOCKS.register("snowbell_crop",
            () -> new SnowBellCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    ///////////////
    // WOLFSBANE //
    ///////////////
    public static final RegistryObject<Block> WOLFSBANE_CROP = BLOCKS.register("wolfsbane_crop",
            () -> new WolfsbaneCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));

    //////////////
    // WORMWOOD //
    //////////////
    public static final RegistryObject<Block> WORMWOOD_CROP = BLOCKS.register("wormwood_crop",
            () -> new WormwoodCropBlock(BlockBehaviour.Properties.copy(Blocks.WHEAT).noCollission().noOcclusion()));


    /********************************************************************************************
     *   Beginning of wood blocks:
     *      - Rowan
     *      - Alder
     *      - Hawthorn
     *
     *      Includes sapling, log and log variants, leaves, and planks
     ********************************************************************************************/

    //////////////////
    // ROWAN BLOCKS //
    //////////////////

    public static final RegistryObject<Block> ROWAN_SAPLING = registerBlock("rowan_sapling",
            () -> new SaplingBlock(null, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ROWAN_LOG = registerBlock("rowan_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> ROWAN_WOOD = registerBlock("rowan_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_ROWAN_LOG = registerBlock("stripped_rowan_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_ROWAN_WOOD = registerBlock("stripped_rowan_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> ROWAN_PLANKS = registerBlock("rowan_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> ROWAN_LEAVES = registerBlock("rowan_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });



    //////////////////
    // ALDER BLOCKS //
    //////////////////

    public static final RegistryObject<Block> ALDER_SAPLING = registerBlock("alder_sapling",
            () -> new SaplingBlock(null, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> ALDER_LOG = registerBlock("alder_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> ALDER_WOOD = registerBlock("alder_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_ALDER_LOG = registerBlock("stripped_alder_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_ALDER_WOOD = registerBlock("stripped_alder_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> ALDER_PLANKS = registerBlock("alder_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> ALDER_LEAVES = registerBlock("alder_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });


    /////////////////////
    // HAWTHORN BLOCKS //
    /////////////////////

    public static final RegistryObject<Block> HAWTHORN_SAPLING = registerBlock("hawthorn_sapling",
            () -> new SaplingBlock(null, BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistryObject<Block> HAWTHORN_LOG = registerBlock("hawthorn_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistryObject<Block> HAWTHORN_WOOD = registerBlock("hawthorn_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistryObject<Block> STRIPPED_HAWTHORN_LOG = registerBlock("stripped_hawthorn_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistryObject<Block> STRIPPED_HAWTHORN_WOOD = registerBlock("stripped_hawthorn_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistryObject<Block> HAWTHORN_PLANKS = registerBlock("hawthorn_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.OAK_PLANKS)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 20;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 5;
                }
            });
    public static final RegistryObject<Block> HAWTHORN_LEAVES = registerBlock("hawthorn_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)){
                @Override
                public boolean isFlammable(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return true;
                }

                @Override
                public int getFlammability(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 60;
                }

                @Override
                public int getFireSpreadSpeed(BlockState state, BlockGetter level, BlockPos pos, Direction direction) {
                    return 30;
                }
            });

    /*********************************************************************************************
     * END OF WOOD ITEMS
     **********************************************************************************************/



    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem (String name, RegistryObject<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }


}
