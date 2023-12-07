package net.outta_space.witchery.rite;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;

public class BindCircleTalismanRite {
    public static void perform(Level pLevel, BlockPos pPos, int[] circles) {
        int[] smallCircleX = {3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -2, -1, 0, 1, 2};
        int[] smallCircleZ = {-1, 0, 1, 2, 3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -2};

        int[] mediumCircleX = {5, 5, 5, 5, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -5, -5, -5, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4};
        int[] mediumCircleZ = {-2, -1, 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -5, -5, -5, -5, -4, -3};

        int[] largeCircleX = {7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -7, -7, -7, -7, -7, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6};
        int[] largeCircleZ = {-3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -7, -7, -7, -7, -7, -7, -6, -5, -4};

        int[] circleX = largeCircleX;
        int[] circleZ = largeCircleZ;
        int size = 40;

        CompoundTag tag = getCompoundTag(circles);


        if(circles[0] > 0) {
            circleX = smallCircleX;
            circleZ = smallCircleZ;
            size = 16;
            circles[0] = 0;
        } else if (circles[1] > 0) {
            circleX = mediumCircleX;
            circleZ = mediumCircleZ;
            size = 28;
            circles[1] = 0;
        } else {
            circles[2] = 0;
        }

        for(int i = 0; i < size; i++) {
            BlockPos glyphPos = new BlockPos(pPos.getX() + circleX[i], pPos.getY(), pPos.getZ() + circleZ[i]);
            pLevel.setBlockAndUpdate(glyphPos, Blocks.AIR.defaultBlockState());
        }

        System.out.println(Arrays.toString(circles));
        if(circles[0] == 0 && circles[1] == 0 && circles[2] == 0) {
            pLevel.setBlockAndUpdate(pPos, Blocks.AIR.defaultBlockState());
        }

        ItemStack talisman = new ItemStack(ModItems.CIRCLE_TALISMAN.get(), 1);
        talisman.setTag(tag);

        pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, talisman));
        pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
    }

    @NotNull
    private static CompoundTag getCompoundTag(int[] circles) {
        CompoundTag tag = new CompoundTag();

        if(circles[0] == 1) {
            tag.putInt("circle", 1);
        } else if(circles[0] == 2) {
            tag.putInt("circle", 4);
        } else if(circles[0] == 3) {
            tag.putInt("circle", 7);
        } else if(circles[1] == 1) {
            tag.putInt("circle", 2);
        } else if(circles[1] == 2) {
            tag.putInt("circle", 5);
        } else if(circles[1] == 3) {
            tag.putInt("circle", 8);
        } else if(circles[2] == 1) {
            tag.putInt("circle", 3);
        } else if(circles[2] == 2) {
            tag.putInt("circle", 6);
        } else {
            tag.putInt("circle", 9);
        }

        return tag;
    }
}
