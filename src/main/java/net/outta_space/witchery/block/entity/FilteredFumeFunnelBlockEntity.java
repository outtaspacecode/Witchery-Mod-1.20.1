package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.common.Mod;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.FilteredFumeFunnelBlock;
import net.outta_space.witchery.block.custom.WitchOvenBlock;

import static net.outta_space.witchery.block.custom.FilteredFumeFunnelBlock.FACING;
import static net.outta_space.witchery.block.custom.FilteredFumeFunnelBlock.IS_PIPE;

public class FilteredFumeFunnelBlockEntity extends BlockEntity {
    public FilteredFumeFunnelBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.FILTERED_FUME_FUNNEL_BE.get(), pPos, pBlockState);
    }

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(pLevel.getBlockState(pPos.below()).getBlock() == ModBlocks.WITCH_OVEN.get()) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 1));
        } else if(pState.getValue(FACING) == Direction.NORTH) {
            if(pLevel.getBlockState(pPos.west()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.west()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 2));
            } else if(pLevel.getBlockState(pPos.east()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.east()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 3));
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
            }
        } else if(pState.getValue(FACING) == Direction.EAST) {
            if(pLevel.getBlockState(pPos.north()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.north()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 2));
            } else if(pLevel.getBlockState(pPos.south()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.south()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 3));
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
            }
        } else if(pState.getValue(FACING) == Direction.SOUTH) {
            if(pLevel.getBlockState(pPos.east()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.east()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 2));
            } else if(pLevel.getBlockState(pPos.west()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.west()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 3));
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
            }
        } else if(pState.getValue(FACING) == Direction.WEST) {
            if(pLevel.getBlockState(pPos.south()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.south()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 2));
            } else if(pLevel.getBlockState(pPos.north()).is(ModBlocks.WITCH_OVEN.get())
                    && pLevel.getBlockState(pPos.north()).getValue(WitchOvenBlock.FACING) == pState.getValue(FACING)) {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_PIPE, 3));
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
            }
        } else {
            pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState().setValue(FACING, pState.getValue(FACING)));
        }
    }
}
