package net.outta_space.witchery.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.outta_space.witchery.WitcheryMod;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.TAGLOCK_KIT.get(), new ResourceLocation(WitcheryMod.MOD_ID, "full"),
                ((pStack, pLevel, pEntity, pSeed) -> pStack.hasTag() ? 1f : 0f));

        ItemProperties.register(ModItems.WAYSTONE.get(), new ResourceLocation(WitcheryMod.MOD_ID, "bound"),
                ((pStack, pLevel, pEntity, pSeed) -> pStack.hasTag() ? 1f : 0f));

        ItemProperties.register(ModItems.CIRCLE_TALISMAN.get(), new ResourceLocation(WitcheryMod.MOD_ID, "bound_circle"),
                (pStack, pLevel, pEntity, pSeed) -> {
                    if(pStack.hasTag()) {
                        return pStack.getTag().getInt("circle");
                    } else {
                        return 0;
                    }
                });
    }

}
