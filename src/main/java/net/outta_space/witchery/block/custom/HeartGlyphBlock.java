package net.outta_space.witchery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

public class HeartGlyphBlock extends GlyphBlock {
    public HeartGlyphBlock(Properties p_152915_) {
        super(p_152915_);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

            if(!pLevel.isClientSide()) {

                pPlayer.displayClientMessage(Component.literal("§cUnknown rite."), true);
                return InteractionResult.SUCCESS;

            }

            return InteractionResult.CONSUME;

//        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }
}
