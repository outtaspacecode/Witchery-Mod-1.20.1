package net.outta_space.witchery.util;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, WitcheryMod.MOD_ID);

    public static final RegistryObject<CreativeModeTab> COURSE_TAB = CREATIVE_MODE_TABS.register("witchery_tab",
        () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.WHIFF_OF_MAGIC.get()))
                .title(Component.translatable("creativetab.witchery_tab"))
                .displayItems((displayParameters, output) -> {

                    /************************************************************
                     * Seed and crop items
                     ************************************************************/
                    output.accept(ModItems.BELLADONNA_SEEDS.get());
                    output.accept(ModItems.BELLADONNA_FLOWER.get());
                    output.accept(ModItems.MANDRAKE_SEEDS.get());
                    output.accept(ModItems.MANDRAKE_ROOT.get());
                    output.accept(ModItems.WATER_ARTICHOKE_SEEDS.get());
                    output.accept(ModItems.WATER_ARTICHOKE.get());
                    output.accept(ModItems.SNOWBELL_SEEDS.get());
                    output.accept(ModItems.WOLFSBANE_SEEDS.get());
                    output.accept(ModItems.WOLFSBANE.get());
                    output.accept(ModItems.WORMWOOD_SEEDS.get());
                    output.accept(ModItems.WORMWOOD.get());

                    /************************************************************
                     * Basic items
                     ************************************************************/

                    output.accept(ModBlocks.ALTAR_BLOCK.get());
                    output.accept(ModItems.RITUAL_CHALK.get());
                    output.accept(ModItems.GOLDEN_CHALK.get());
                    output.accept(ModItems.INFERNAL_CHALK.get());
                    output.accept(ModItems.OTHERWHERE_CHALK.get());
                    output.accept(ModItems.BROOM.get());
                    output.accept(ModItems.ANOINTING_PASTE.get());
                    output.accept(ModBlocks.WITCH_CAULDRON.get());
                    output.accept(ModItems.WOOD_ASH.get());
                    output.accept(ModItems.RAW_CLAY_VESSEL.get());
                    output.accept(ModItems.CLAY_VESSEL.get());


                    output.accept(ModItems.CREEPER_HEART.get());
                    output.accept(ModItems.TONGUE_OF_DOG.get());
                    output.accept(ModItems.WOOL_OF_BAT.get());

                    /************************************************************
                     * Bottled magic items
                     ************************************************************/

                    output.accept(ModItems.EXHALE_OF_THE_HORNED_ONE.get());
                    output.accept(ModItems.HINT_OF_REBIRTH.get());
                    output.accept(ModItems.BREATH_OF_THE_GODDESS.get());
                    output.accept(ModItems.FOUL_FUME.get());
                    output.accept(ModItems.WHIFF_OF_MAGIC.get());
                    output.accept(ModItems.REEK_OF_MISFORTUNE.get());
                    output.accept(ModItems.ODOUR_OF_PURITY.get());

                    /************************************************************
                     * Tree and wood items
                     ************************************************************/

                    ///////////
                    // ROWAN //
                    ///////////
                    output.accept(ModBlocks.ROWAN_SAPLING.get());
                    output.accept(ModBlocks.ROWAN_LOG.get());
                    output.accept(ModBlocks.STRIPPED_ROWAN_LOG.get());
                    output.accept(ModBlocks.ROWAN_WOOD.get());
                    output.accept(ModBlocks.STRIPPED_ROWAN_WOOD.get());
                    output.accept(ModBlocks.ROWAN_LEAVES.get());
                    output.accept(ModBlocks.ROWAN_PLANKS.get());

                    ///////////
                    // ALDER //
                    ///////////
                    output.accept(ModBlocks.ALDER_SAPLING.get());
                    output.accept(ModBlocks.ALDER_LOG.get());
                    output.accept(ModBlocks.STRIPPED_ALDER_LOG.get());
                    output.accept(ModBlocks.ALDER_WOOD.get());
                    output.accept(ModBlocks.STRIPPED_ALDER_WOOD.get());
                    output.accept(ModBlocks.ALDER_LEAVES.get());
                    output.accept(ModBlocks.ALDER_PLANKS.get());

                    //////////////
                    // HAWTHORN //
                    //////////////
                    output.accept(ModBlocks.HAWTHORN_SAPLING.get());
                    output.accept(ModBlocks.HAWTHORN_LOG.get());
                    output.accept(ModBlocks.STRIPPED_HAWTHORN_LOG.get());
                    output.accept(ModBlocks.HAWTHORN_WOOD.get());
                    output.accept(ModBlocks.STRIPPED_HAWTHORN_WOOD.get());
                    output.accept(ModBlocks.HAWTHORN_LEAVES.get());
                    output.accept(ModBlocks.HAWTHORN_PLANKS.get());

                }).build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
