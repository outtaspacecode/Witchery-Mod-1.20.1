package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.outta_space.witchery.block.ModBlocks;

import java.util.List;
import java.util.Random;

public class MutandisItem extends Item {
    public MutandisItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        BlockPos pPos = pContext.getClickedPos();
        BlockState pBlock = pContext.getLevel().getBlockState(pPos);
        List<Block> convertiblePlants = List.of(Blocks.JUNGLE_SAPLING, Blocks.ACACIA_SAPLING, Blocks.BIRCH_SAPLING, Blocks.DARK_OAK_SAPLING, Blocks.OAK_SAPLING, Blocks.SPRUCE_SAPLING,
                Blocks.CHERRY_SAPLING, Blocks.VINE, ModBlocks.ROWAN_SAPLING.get(), ModBlocks.ALDER_SAPLING.get(), ModBlocks.HAWTHORN_SAPLING.get(), Blocks.DANDELION, Blocks.POPPY,
                Blocks.BLUE_ORCHID, Blocks.ALLIUM, Blocks.AZURE_BLUET, Blocks.OXEYE_DAISY, Blocks.PINK_TULIP, Blocks.CORNFLOWER, Blocks.LILY_OF_THE_VALLEY, Blocks.LILY_PAD, Blocks.SWEET_BERRY_BUSH);

        if(!pLevel.isClientSide()) {

            if(pBlock.getBlock() instanceof BushBlock || pBlock.getBlock() instanceof VineBlock) {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.LAVA_EXTINGUISH, SoundSource.BLOCKS, 0.5f, -1f, 0);
                Random rand = new Random();
                pLevel.setBlockAndUpdate(pPos, convertiblePlants.get(rand.nextInt(convertiblePlants.size())).defaultBlockState());
                pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;

        }
        return InteractionResult.CONSUME;
    }
}
