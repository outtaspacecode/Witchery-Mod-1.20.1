package net.outta_space.witchery.rite;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Rabbit;
import net.minecraft.world.level.Level;
import org.checkerframework.checker.units.qual.C;

import java.util.concurrent.CountDownLatch;

public class ConvertDemonBunnyRite {
    public static void perform(Level pLevel, BlockPos pPos, Rabbit rabbit) {
        rabbit.setVariant(Rabbit.Variant.EVIL);
    }
}
