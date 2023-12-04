package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;

public class MutatingSprigItem extends Item {
    public MutatingSprigItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level level = pContext.getLevel();

        if(!level.isClientSide()) {
            BlockPos pPos = pContext.getClickedPos();
            if(level.getBlockState(pPos).is(Blocks.GRASS_BLOCK)) {
                level.setBlockAndUpdate(pPos, Blocks.MYCELIUM.defaultBlockState());
                level.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.SLIME_JUMP , SoundSource.BLOCKS, 0.5f, -1f, 0);
                pContext.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1,pContext.getPlayer(), player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                return InteractionResult.SUCCESS;
            }

            if(level.getBlockState(pPos).is(Blocks.MYCELIUM)) {
                level.setBlockAndUpdate(pPos, Blocks.GRASS_BLOCK.defaultBlockState());
                level.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.SLIME_JUMP , SoundSource.BLOCKS, 0.5f, -1f, 0);
                pContext.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1,pContext.getPlayer(), player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                return InteractionResult.SUCCESS;
            }

            if(level.getBlockState(pPos).is(Blocks.DIRT) && level.getBlockState(pPos.above()).is(Blocks.WATER)) {
                level.setBlockAndUpdate(pPos, Blocks.CLAY.defaultBlockState());
                level.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.SLIME_JUMP , SoundSource.BLOCKS, 0.5f, -1f, 0);
                pContext.getPlayer().getItemInHand(InteractionHand.MAIN_HAND).hurtAndBreak(1,pContext.getPlayer(), player -> player.broadcastBreakEvent(player.getUsedItemHand()));
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
        }

        return InteractionResult.CONSUME;
    }
}
