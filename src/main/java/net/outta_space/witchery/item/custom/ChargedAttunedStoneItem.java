package net.outta_space.witchery.item.custom;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

public class ChargedAttunedStoneItem extends Item {
    public ChargedAttunedStoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public boolean isFoil(ItemStack pStack) {
        return true;
    }
}
