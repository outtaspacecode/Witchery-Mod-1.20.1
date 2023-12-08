package net.outta_space.witchery.rite;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.item.ModItems;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class DeathProtectionRite {
    public static void perform(Level pLevel, BlockPos pPos, ItemStack taglock) {
        ItemStack poppet = new ItemStack(ModItems.DEATH_PROTECTION_POPPET.get(), 1);
        CompoundTag tag = new CompoundTag();
        tag.putString("bound_player", taglock.getTag().getString("bound_player"));
        tag.putUUID("player_uuid", taglock.getTag().getUUID("player_uuid"));

        poppet.setTag(tag);

        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, poppet));
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
    }
}
