package net.outta_space.witchery.item;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.PlaceOnWaterBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;

import javax.swing.*;

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
    public static final RegistryObject<Item> WATER_ARTICHOKE_SEEDS = ITEMS.register("water_artichoke_seeds",
            () -> new PlaceOnWaterBlockItem(ModBlocks.WATER_ARTICHOKE_CROP.get(), new Item.Properties()));
    public static final RegistryObject<Item> WATER_ARTICHOKE = ITEMS.register("water_artichoke",
            () -> new Item(new Item.Properties()));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }

}
