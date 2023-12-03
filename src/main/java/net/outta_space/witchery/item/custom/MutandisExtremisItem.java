package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.outta_space.witchery.block.ModBlocks;

import java.util.List;
import java.util.Random;

public class MutandisExtremisItem extends Item {
    public MutandisExtremisItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        BlockPos pPos = pContext.getClickedPos();
        BlockState pBlock = pContext.getLevel().getBlockState(pPos);
        List<Block> convertiblePlants = List.of(ModBlocks.ROWAN_SAPLING.get(), Blocks.BIRCH_SAPLING, Blocks.OAK_SAPLING, Blocks.JUNGLE_SAPLING, Blocks.SPRUCE_SAPLING, ModBlocks.HAWTHORN_SAPLING.get(), ModBlocks.ALDER_SAPLING.get(),
                ModBlocks.SPANISH_MOSS.get());

        if(!pLevel.isClientSide()) {

            if(pBlock.getBlock() instanceof BushBlock || pBlock.getBlock() instanceof VineBlock) {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5f, -1f, 0);
                Random rand = new Random();
                pLevel.setBlockAndUpdate(pPos, convertiblePlants.get(rand.nextInt(convertiblePlants.size())).defaultBlockState());
                pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
                return InteractionResult.SUCCESS;
            } else if (pBlock.getBlock() instanceof GrassBlock) {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5f, -1f, 0);
                Random rand = new Random();
                if(rand.nextInt(100) < 38) {
                    pLevel.setBlockAndUpdate(pPos, Blocks.MYCELIUM.defaultBlockState());
                }
                pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
                return InteractionResult.SUCCESS;
            } else if (pBlock.getBlock() instanceof SandBlock) {
            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5f, -1f, 0);
            Random rand = new Random();
            if(rand.nextInt(100) < 18) {
                pLevel.setBlockAndUpdate(pPos, Blocks.SOUL_SAND.defaultBlockState());
            }
            pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
            return InteractionResult.SUCCESS;
        }

            return InteractionResult.FAIL;

        }
        return InteractionResult.CONSUME;
    }
}
