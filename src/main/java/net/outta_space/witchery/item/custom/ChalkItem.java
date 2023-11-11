package net.outta_space.witchery.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Position;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.gameevent.GameEvent;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.WitchCauldronBlock;
import net.outta_space.witchery.sound.ModSounds;

import java.util.Objects;
import java.util.Random;

import static net.outta_space.witchery.block.custom.GlyphBlock.STYLE;

public class ChalkItem extends ItemNameBlockItem {
    public ChalkItem(Block pBlock, Properties pProperties) {
        super(pBlock, pProperties);
    }

    @Override
    public InteractionResult place(BlockPlaceContext pContext) {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();
        if(!pLevel.isClientSide())
        {
            BlockState blockState = pLevel.getBlockState(pPos.below());
            if(blockState.isSolid() && !pLevel.getBlockState(pPos).is(ModBlocks.HEART_GLYPH.get()) && !pLevel.getBlockState(pPos).is(ModBlocks.WHITE_CIRCLE_GLYPH.get())
                    && !pLevel.getBlockState(pPos).is(ModBlocks.INFERNAL_CIRCLE_GLYPH.get()) && !pLevel.getBlockState(pPos).is(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get())) {
                if(blockState.is(Blocks.FARMLAND) || blockState.is(Blocks.DIRT_PATH)) {
                    pLevel.setBlockAndUpdate(pPos.below(), Blocks.DIRT.defaultBlockState());
                }
                Random rand = new Random();
                pLevel.setBlockAndUpdate(pPos, getBlock().defaultBlockState().setValue(STYLE, rand.nextInt(11)));
                pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                        ModSounds.DRAW_WITH_CHALK.get() ,SoundSource.BLOCKS, 1f, 1f, 0);
                return InteractionResult.SUCCESS;
            }

            return InteractionResult.FAIL;
        }

        return InteractionResult.CONSUME;
    }



}
