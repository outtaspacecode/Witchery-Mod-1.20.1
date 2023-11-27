package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


import java.util.List;

import static net.outta_space.witchery.block.custom.WitchCauldronBlock.FILL_LEVEL;
import static net.outta_space.witchery.block.custom.WitchCauldronBlock.IS_BOILING;

public class WitchCauldronBlockEntity extends BlockEntity {
    public WitchCauldronBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WITCH_CAULDRON_BE.get(), pPos, pBlockState);
    }

    private int warmUp = 60;
    private final List<Block> heatSources = List.of(Blocks.LAVA, Blocks.FIRE, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE, Blocks.MAGMA_BLOCK);

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(pState.getValue(FILL_LEVEL) >= 3) {
            if (heatSources.contains(pLevel.getBlockState(pPos.below()).getBlock())) {
                if(warmUp > 0) {
                    warmUp--;
                } else {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_BOILING, true));
                }
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_BOILING, false));
                resetWarmUp();
            }
        } else {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_BOILING, false));
            resetWarmUp();
        }
    }

    private void resetWarmUp() {
        warmUp = 60;
    }
}
