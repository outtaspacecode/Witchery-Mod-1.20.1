package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.outta_space.witchery.block.ModBlocks;

import java.util.Random;

public class AnointingPasteItem extends Item {
    public AnointingPasteItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        if(!pContext.getLevel().isClientSide()) {

            BlockPos pPos = pContext.getClickedPos();
            Level pLevel = pContext.getLevel();
            BlockState pBlock = pContext.getLevel().getBlockState(pPos);
            Player pPlayer = pContext.getPlayer();

            if(pBlock.is(Blocks.CAULDRON) || pBlock.is(Blocks.WATER_CAULDRON) || pBlock.is(Blocks.LAVA_CAULDRON) || pBlock.is(Blocks.POWDER_SNOW_CAULDRON)) {
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.PLAYER_LEVELUP ,SoundSource.BLOCKS, 1f, -1f, 0);
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        SoundEvents.LAVA_EXTINGUISH ,SoundSource.BLOCKS, 1f, -1f, 0);
                pLevel.setBlockAndUpdate(pPos, ModBlocks.WITCH_CAULDRON.get().defaultBlockState());
                assert pPlayer != null;
                pContext.getItemInHand().setCount(pContext.getItemInHand().getCount() - 1);


                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
        }

        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();

        Random rand = new Random();
        for(int i = 0; i < 3; i++) {
            pLevel.addParticle(ParticleTypes.EXPLOSION, pPos.getX() + rand.nextDouble(), pPos.getY() + rand.nextDouble(), pPos.getZ() + rand.nextDouble(), 0, 0, 0);
        }

        return InteractionResult.CONSUME;
    }
}
