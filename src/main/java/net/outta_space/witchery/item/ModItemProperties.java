package net.outta_space.witchery.item;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.outta_space.witchery.WitcheryMod;

public class ModItemProperties {

    public static void addCustomItemProperties() {
        ItemProperties.register(ModItems.TAGLOCK_KIT.get(), new ResourceLocation(WitcheryMod.MOD_ID, "full"),
                ((pStack, pLevel, pEntity, pSeed) -> pStack.hasTag() ? 1f : 0f));
    }

}
