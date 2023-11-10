package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.sound.SoundEvent;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;

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

            if(pBlock.is(Blocks.CAULDRON)) {
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

        return InteractionResult.CONSUME;
    }
}
