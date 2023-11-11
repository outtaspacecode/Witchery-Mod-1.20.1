package net.outta_space.witchery.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.Tiers;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraftforge.common.ForgeTier;
import net.minecraftforge.common.TierSortingRegistry;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;

import java.util.List;

public class ModToolTiers {

    public static final Tier BROOM = TierSortingRegistry.registerTier(
            new ForgeTier(1,2000,65f,0.1f,0,
                    ModTags.Blocks.MINEABLE_WITH_BROOM, () -> Ingredient.of(ModBlocks.HAWTHORN_SAPLING.get())),
            new ResourceLocation(WitcheryMod.MOD_ID, "broom"), List.of(), List.of(Tiers.WOOD));


}
