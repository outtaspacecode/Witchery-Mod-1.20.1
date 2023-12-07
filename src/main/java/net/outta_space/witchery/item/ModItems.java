package net.outta_space.witchery.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.custom.*;
import net.outta_space.witchery.util.ModTags;
import net.outta_space.witchery.util.ModToolTiers;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, WitcheryMod.MOD_ID);

    /*********************************************************************************************
     * Basic items
     *********************************************************************************************/
    public static final RegistryObject<Item> WOOD_ASH = ITEMS.register("wood_ash",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> RAW_CLAY_VESSEL = ITEMS.register("raw_clay_vessel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CLAY_VESSEL = ITEMS.register("clay_vessel",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENT_TWIG = ITEMS.register("ent_twig",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MUTATING_SPRIG = ITEMS.register("mutating_sprig",
            () -> new MutatingSprigItem(new Item.Properties().durability(15)));
    public static final RegistryObject<Item> ANOINTING_PASTE = ITEMS.register("anointing_paste",
            () -> new AnointingPasteItem(new Item.Properties()));
    public static final RegistryObject<Item> MUTANDIS = ITEMS.register("mutandis",
            () -> new MutandisItem(new Item.Properties()));
    public static final RegistryObject<Item> MUTANDIS_EXTREMIS = ITEMS.register("mutandis_extremis",
            () -> new MutandisExtremisItem(new Item.Properties()));

    public static final RegistryObject<Item> GOLDEN_CHALK = ITEMS.register("golden_chalk",
            () -> new ChalkItem(ModBlocks.HEART_GLYPH.get(), new Item.Properties().durability(65)));
    public static final RegistryObject<Item> RITUAL_CHALK = ITEMS.register("ritual_chalk",
            () -> new ChalkItem(ModBlocks.WHITE_CIRCLE_GLYPH.get(), new Item.Properties().durability(65)));
    public static final RegistryObject<Item> INFERNAL_CHALK = ITEMS.register("infernal_chalk",
            () -> new ChalkItem(ModBlocks.INFERNAL_CIRCLE_GLYPH.get(), new Item.Properties().durability(65)));
    public static final RegistryObject<Item> OTHERWHERE_CHALK = ITEMS.register("otherwhere_chalk",
            () -> new ChalkItem(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get(), new Item.Properties().durability(65)));

    public static final RegistryObject<Item> POPPET = ITEMS.register("poppet",
            () -> new Item(new Item.Properties().stacksTo(1)));
    public static final RegistryObject<Item> DEATH_PROTECTION_POPPET = ITEMS.register("death_protection_poppet",
            () -> new DeathProtectionPoppetItem(new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> BROOM = ITEMS.register("broom",
            () -> new BroomItem(ModToolTiers.BROOM, 1,1, ModTags.Blocks.MINEABLE_WITH_BROOM, new Item.Properties().stacksTo(1)));

    public static final RegistryObject<Item> ATTUNED_STONE = ITEMS.register("attuned_stone",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CHARGED_ATTUNED_STONE = ITEMS.register("charged_attuned_stone",
            () -> new ChargedAttunedStoneItem(new Item.Properties()));

    public static final RegistryObject<Item> FUME_FILTER = ITEMS.register("fume_filter",
            () -> new FumeFilterItem(new Item.Properties()));

    public static final RegistryObject<Item> WORMY_APPLE = ITEMS.register("wormy_apple",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BONE_NEEDLE = ITEMS.register("bone_needle",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WAYSTONE = ITEMS.register("waystone",
            () -> new WaystoneItem(new Item.Properties()));
    public static final RegistryObject<Item> ICY_NEEDLE = ITEMS.register("icy_needle",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> QUICKLIME = ITEMS.register("quicklime",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> GYPSUM = ITEMS.register("gypsum",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REFINED_EVIL = ITEMS.register("refined_evil",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEMON_HEART = ITEMS.register("demon_heart",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> TAGLOCK_KIT = ITEMS.register("taglock_kit",
            () -> new TagLockKitItem(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> CIRCLE_TALISMAN = ITEMS.register("circle_talisman",
            () -> new CircleTalismanItem(new Item.Properties().stacksTo(1)));



    /*********************************************************************************************
     * Mob drops
     *********************************************************************************************/
    public static final RegistryObject<Item> CREEPER_HEART = ITEMS.register("creeper_heart",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TONGUE_OF_DOG = ITEMS.register("tongue_of_dog",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WOOL_OF_BAT = ITEMS.register("wool_of_bat",
            () -> new Item(new Item.Properties()));



    /*********************************************************************************************
     * Beginning of Bottled Magic items
     *********************************************************************************************/

    public static final RegistryObject<Item> EXHALE_OF_THE_HORNED_ONE = ITEMS.register("exhale_of_the_horned_one",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> HINT_OF_REBIRTH = ITEMS.register("hint_of_rebirth",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> BREATH_OF_THE_GODDESS = ITEMS.register("breath_of_the_goddess",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FOUL_FUME = ITEMS.register("foul_fume",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> WHIFF_OF_MAGIC = ITEMS.register("whiff_of_magic",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> REEK_OF_MISFORTUNE = ITEMS.register("reek_of_misfortune",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ODOUR_OF_PURITY = ITEMS.register("odour_of_purity",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> PURIFIED_MILK = ITEMS.register("purified_milk",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DEMONIC_BLOOD = ITEMS.register("demonic_blood",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DIAMOND_VAPOR = ITEMS.register("diamond_vapor",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> MELLIFLUOUS_HUNGER = ITEMS.register("mellifluous_hunger",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> OIL_OF_VITRIOL = ITEMS.register("oil_of_vitriol",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> DROP_OF_LUCK = ITEMS.register("drop_of_luck",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> ENDER_DEW = ITEMS.register("ender_dew",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> TEAR_OF_THE_GODDESS = ITEMS.register("tear_of_the_goddess",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> FOCUSED_WILL = ITEMS.register("focused_will",
            () -> new Item(new Item.Properties()));
    public static final RegistryObject<Item> CONDENSED_FEAR = ITEMS.register("condensed_fear",
            () -> new Item(new Item.Properties()));

    /*********************************************************************************************
     * End of Bottled Magic items
     *********************************************************************************************/

    /*********************************************************************************************
     * Beginning of Seed and Crop items
     *********************************************************************************************/

    ////////////////
    // BELLADONNA //
    ////////////////
    public static final RegistryObject<Item> BELLADONNA_SEEDS = ITEMS.register("belladonna_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BELLADONNA_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> BELLADONNA_FLOWER = ITEMS.register("belladonna_flower",
            () -> new Item(new Item.Properties()));

    //////////////
    // MANDRAKE //
    //////////////
    public static final RegistryObject<Item> MANDRAKE_SEEDS = ITEMS.register("mandrake_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MANDRAKE_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> MANDRAKE_ROOT = ITEMS.register("mandrake_root",
            () -> new Item(new Item.Properties()));

    /////////////////////
    // WATER ARTICHOKE //
    /////////////////////
    /* Special thanks to Sir Bl4ckL1on from the Kaupenjoe discord server for suggesting  *
     *      the PlaceOnWaterBlockItem() method                                           */
    public static final RegistryObject<Item> WATER_ARTICHOKE_SEEDS = ITEMS.register("water_artichoke_seeds",
            () -> new PlaceOnWaterBlockItem(ModBlocks.WATER_ARTICHOKE_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> WATER_ARTICHOKE = ITEMS.register("water_artichoke",
            () -> new Item(new Item.Properties()));

    //////////////
    // SNOWBELL //
    //////////////
    public static final RegistryObject<Item> SNOWBELL_SEEDS = ITEMS.register("snowbell_seeds",
            () -> new ItemNameBlockItem(ModBlocks.SNOWBELL_CROP.get(), new Item.Properties()));

    ///////////////
    // WOLFSBANE //
    ///////////////
    public static final RegistryObject<Item> WOLFSBANE_SEEDS = ITEMS.register("wolfsbane_seeds",
            () -> new ItemNameBlockItem(ModBlocks.WOLFSBANE_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> WOLFSBANE = ITEMS.register("wolfsbane",
            () -> new Item(new Item.Properties()));

    //////////////
    // WORMWOOD //
    //////////////
    public static final RegistryObject<Item> WORMWOOD_SEEDS = ITEMS.register("wormwood_seeds",
            () -> new ItemNameBlockItem(ModBlocks.WORMWOOD_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> WORMWOOD = ITEMS.register("wormwood",
            () -> new Item(new Item.Properties()));



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
