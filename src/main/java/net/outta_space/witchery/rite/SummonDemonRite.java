package net.outta_space.witchery.rite;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.item.ModItems;

public class SummonDemonRite {
    public static void perform(Level pLevel, BlockPos pPos) {
        ItemStack heart = new ItemStack(ModItems.DEMON_HEART.get(), 1);

        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, heart));
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
    }
}
