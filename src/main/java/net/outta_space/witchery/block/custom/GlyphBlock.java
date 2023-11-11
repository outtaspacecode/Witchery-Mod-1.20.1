package net.outta_space.witchery.block.custom;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.IntegerProperty;

import java.util.Random;

public class GlyphBlock extends CarpetBlock {
    public static final IntegerProperty STYLE = IntegerProperty.create("style", 0, 11);
    public GlyphBlock(Properties p_152915_) {
        super(p_152915_);
        this.registerDefaultState(this.defaultBlockState().setValue(STYLE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(STYLE);
    }


}
