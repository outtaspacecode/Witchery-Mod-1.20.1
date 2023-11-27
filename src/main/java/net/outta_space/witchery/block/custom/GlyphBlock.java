package net.outta_space.witchery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.outta_space.witchery.block.ModBlocks;

import java.util.Random;

public class GlyphBlock extends CarpetBlock {
    public static final IntegerProperty STYLE = IntegerProperty.create("style", 0, 11);
    public GlyphBlock(Properties p_152915_) {
        super(p_152915_);
        this.registerDefaultState(this.defaultBlockState().setValue(STYLE, 0));
    }

    @Override
    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if(pState.is(ModBlocks.INFERNAL_CIRCLE_GLYPH.get())) {
            pLevel.addParticle(ParticleTypes.FLAME, (double) pPos.getX() + (pRandom.nextDouble() * (10.0D / 16.0D) + 0.15D),
                    (double) pPos.getY() + (1.0 / 16.0), (double) pPos.getZ() + (pRandom.nextDouble() * (10.0D / 16.0D) + 0.15D), 0.0D, 0.0D, 0.0D);
        }
        if(pState.is(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get())) {
            pLevel.addParticle(ParticleTypes.PORTAL, (double)pPos.getX() + (pRandom.nextDouble() * (10.0D / 16.0D) + 0.15D),
                    (double)pPos.getY() + (1.0D / 16.0D), (double)pPos.getZ() + (pRandom.nextDouble() * (10.0D / 16.0D) + 0.15D), 0.0D, 0.0D, 0.0D);
        }
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STYLE);
    }


}
