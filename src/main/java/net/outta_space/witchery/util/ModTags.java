package net.outta_space.witchery.util;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.outta_space.witchery.WitcheryMod;

public class ModTags {

    public static class Items {





        private static TagKey<Item> tag(String name) {
            return ItemTags.create(new ResourceLocation(WitcheryMod.MOD_ID, name));
        }

        private static TagKey<Item> forgeTag(String name) {
            return ItemTags.create(new ResourceLocation("forge", name));
        }
    }



    public static class Blocks {


        public static final TagKey<Block> MINEABLE_WITH_BROOM = tag("mineable_with_broom");

        private static TagKey<Block> tag(String name) {
            return BlockTags.create(new ResourceLocation(WitcheryMod.MOD_ID, name));
        }

        private static TagKey<Block> forgeTag(String name) {
            return BlockTags.create(new ResourceLocation("forge", name));
        }
    }
}
