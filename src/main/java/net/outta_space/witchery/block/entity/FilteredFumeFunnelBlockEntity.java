package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.outta_space.witchery.block.ModBlocks;

import static net.outta_space.witchery.block.custom.FilteredFumeFunnelBlock.FACING;
import static net.outta_space.witchery.block.custom.FilteredFumeFunnelBlock.IS_PIPE;

public class FilteredFumeFunnelBlockEntity extends BlockEntity {
    public FilteredFumeFunnelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FILTERED_FUME_FUNNEL_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(pLevel.getBlockState(pPos.below()).getBlock() == ModBlocks.WITCH_OVEN.get()) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, true));
        } else {
            pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
        }
    }
}
