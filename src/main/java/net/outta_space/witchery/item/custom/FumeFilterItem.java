package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.FilteredFumeFunnelBlock;
import net.outta_space.witchery.block.custom.FumeFunnelBlock;

public class FumeFilterItem extends Item {
    public FumeFilterItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();
        BlockState pBlock = pContext.getLevel().getBlockState(pPos);

        if(!pLevel.isClientSide()) {
            if(pBlock.is(ModBlocks.FUME_FUNNEL.get())) {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.ITEM_FRAME_ADD_ITEM, SoundSource.BLOCKS, 0.5f, 1f, 0);
                pLevel.setBlockAndUpdate(pPos, ModBlocks.FILTERED_FUME_FUNNEL.get().defaultBlockState().setValue(FilteredFumeFunnelBlock.FACING, pBlock.getValue(FumeFunnelBlock.FACING))
                        .setValue(FilteredFumeFunnelBlock.IS_PIPE, pBlock.getValue(FumeFunnelBlock.IS_PIPE)));
                if(!pContext.getPlayer().isCreative()) {
                    pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);
                }
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.CONSUME;
    }
}
