package net.outta_space.witchery.rite;


import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.item.ModItems;
import org.checkerframework.checker.units.qual.C;

import java.util.List;

public class UseWaystoneRite {
    public static void perform(Level pLevel, BlockPos pPos, List<ItemStack> itemList, List<LivingEntity> livingEntities) {

        if(!itemList.get(0).hasTag()) {
            ItemStack item = new ItemStack(ModItems.WAYSTONE.get(), 1);

            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, item));
            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.NOTE_BLOCK_SNARE, SoundSource.BLOCKS, 1f, 0, 1);
            Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
            assert player != null;
            player.sendSystemMessage(Component.literal("§cWaystone must be bound"));
            return;
        } else if (livingEntities.isEmpty()) {
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, itemList.get(0)));
            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.NOTE_BLOCK_SNARE, SoundSource.BLOCKS, 1f, 0, 1);
            Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
            assert player != null;
            player.sendSystemMessage(Component.literal("§cNo entities to warp"));
            return;
        }

        int[] locationTag = itemList.get(0).getTag().getIntArray("location");
        BlockPos pos = new BlockPos(locationTag[0], locationTag[1], locationTag[2]);

        for(LivingEntity entity : livingEntities) {
            entity.teleportTo(pos.getX(), pos.getY(), pos.getZ());
        }

        pLevel.playSeededSound(null, pos.getX(), pos.getY(), pos.getZ(),
                SoundEvents.ENDERMAN_TELEPORT, SoundSource.PLAYERS, 1f, 0, 8);
    }
}
