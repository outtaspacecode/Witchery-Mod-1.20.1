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
                    output.accept(ModItems.ICY_NEEDLE.get());
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
                    output.accept(ModItems.TAGLOCK_KIT.get());
                    output.accept(ModItems.POPPET.get());
                    output.accept(ModItems.DEATH_PROTECTION_POPPET.get());
                    output.accept(ModItems.ATTUNED_STONE.get());
                    output.accept(ModItems.CHARGED_ATTUNED_STONE.get());
                    output.accept(ModItems.FUME_FILTER.get());
                    output.accept(ModItems.ANOINTING_PASTE.get());
                    output.accept(ModItems.MUTANDIS.get());
                    output.accept(ModItems.MUTANDIS_EXTREMIS.get());
                    output.accept(ModBlocks.SPANISH_MOSS.get());
                    output.accept(ModBlocks.WITCH_CAULDRON.get());
                    output.accept(ModItems.ENT_TWIG.get());
                    output.accept(ModItems.MUTATING_SPRIG.get());
                    output.accept(ModBlocks.WITCH_OVEN.get());
                    output.accept(ModBlocks.FUME_FUNNEL.get());
                    output.accept(ModBlocks.FILTERED_FUME_FUNNEL.get());
                    output.accept(ModBlocks.DISTILLERY.get());
                    output.accept(ModItems.WOOD_ASH.get());
                    output.accept(ModItems.QUICKLIME.get());
                    output.accept(ModItems.GYPSUM.get());

                    output.accept(ModItems.WORMY_APPLE.get());
                    output.accept(ModItems.BONE_NEEDLE.get());
                    output.accept(ModItems.WAYSTONE.get());

                    output.accept(ModItems.REFINED_EVIL.get());
                    output.accept(ModItems.DEMON_HEART.get());
                    output.accept(ModItems.CREEPER_HEART.get());
                    output.accept(ModItems.TONGUE_OF_DOG.get());
                    output.accept(ModItems.WOOL_OF_BAT.get());

                    output.accept(ModItems.RAW_CLAY_VESSEL.get());
                    output.accept(ModItems.CLAY_VESSEL.get());

                    /************************************************************
                     * Bottled magic items
                     ************************************************************/

                    output.accept(ModItems.FOUL_FUME.get());
                    output.accept(ModItems.DIAMOND_VAPOR.get());
                    output.accept(ModItems.OIL_OF_VITRIOL.get());
                    output.accept(ModItems.EXHALE_OF_THE_HORNED_ONE.get());
                    output.accept(ModItems.BREATH_OF_THE_GODDESS.get());
                    output.accept(ModItems.HINT_OF_REBIRTH.get());
                    output.accept(ModItems.WHIFF_OF_MAGIC.get());
                    output.accept(ModItems.REEK_OF_MISFORTUNE.get());
                    output.accept(ModItems.ODOUR_OF_PURITY.get());
                    output.accept(ModItems.TEAR_OF_THE_GODDESS.get());
                    output.accept(ModItems.DROP_OF_LUCK.get());
                    output.accept(ModItems.ENDER_DEW.get());
                    output.accept(ModItems.DEMONIC_BLOOD.get());
                    output.accept(ModItems.MELLIFLUOUS_HUNGER.get());
                    output.accept(ModItems.PURIFIED_MILK.get());
                    output.accept(ModItems.FOCUSED_WILL.get());
                    output.accept(ModItems.CONDENSED_FEAR.get());

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
