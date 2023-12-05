package net.outta_space.witchery.rite;


import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class BindWaystoneRite {
    public static void perform(Level pLevel, Player pPlayer, InteractionHand pHand) {
        CompoundTag tag = new CompoundTag();
        tag.putIntArray("location", new int[]{(int)pPlayer.getX(), (int)pPlayer.getY(), (int)pPlayer.getZ()});

        pPlayer.getItemInHand(pHand).setTag(tag);

        pLevel.playSeededSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 1f, 5);
    }
}
