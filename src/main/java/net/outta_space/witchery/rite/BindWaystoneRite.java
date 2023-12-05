package net.outta_space.witchery.rite;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.item.ModItems;

public class BindWaystoneRite {
    public static void perform(Level pLevel, BlockPos pPos) {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("location", new int[]{pPos.getX(), pPos.getY(), pPos.getZ()});

        ItemStack item = new ItemStack(ModItems.WAYSTONE.get(), 1);
        item.setTag(tag);

        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, item));
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
    }
}
