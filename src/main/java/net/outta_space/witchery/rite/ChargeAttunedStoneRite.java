package net.outta_space.witchery.rite;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.item.ModItems;

public class ChargeAttunedStoneRite {
    public static void perform(Level pLevel, BlockPos pPos) {
        ItemStack item = new ItemStack(ModItems.CHARGED_ATTUNED_STONE.get(), 1);

        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, item));
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
    }
}
