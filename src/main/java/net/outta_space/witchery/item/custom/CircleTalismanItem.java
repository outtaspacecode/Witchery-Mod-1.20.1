package net.outta_space.witchery.item.custom;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraftforge.event.level.NoteBlockEvent;
import net.outta_space.witchery.block.ModBlocks;

import javax.annotation.Nullable;
import java.util.Random;

import static net.outta_space.witchery.block.custom.GlyphBlock.STYLE;

public class CircleTalismanItem extends Item {


    public CircleTalismanItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        Player pPlayer = pContext.getPlayer();

        assert pPlayer != null;
        if(!pPlayer.getItemInHand(pContext.getHand()).hasTag()) {
            return InteractionResult.FAIL;
        }

        BlockPos clickedPos;
        if(pContext.getLevel().getBlockState(pContext.getClickedPos()).is(ModBlocks.HEART_GLYPH.get())) {
            clickedPos = pContext.getClickedPos();
        } else {
            clickedPos = pContext.getClickedPos().relative(pContext.getClickedFace());
        }
        System.out.println(clickedPos.toString());
        Level pLevel = pContext.getLevel();

        int[] smallCircleX = {3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -2, -1, 0, 1, 2};
        int[] smallCircleZ = {-1, 0, 1, 2, 3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -2};

        int[] mediumCircleX = {5, 5, 5, 5, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -5, -5, -5, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4};
        int[] mediumCircleZ = {-2, -1, 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -5, -5, -5, -5, -4, -3};

        int[] largeCircleX = {7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -7, -7, -7, -7, -7, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6};
        int[] largeCircleZ = {-3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -7, -7, -7, -7, -7, -7, -6, -5, -4};

        ItemStack talisman = pPlayer.getItemInHand(pContext.getHand());
        Random rand = new Random();

        int[] circleX;
        int[] circleZ;
        int size;
        if(talisman.getTag().getInt("circle") == 1 || talisman.getTag().getInt("circle") == 4 || talisman.getTag().getInt("circle") == 7) {
            circleX = smallCircleX;
            circleZ = smallCircleZ;
            size = 16;
        } else if(talisman.getTag().getInt("circle") == 2 || talisman.getTag().getInt("circle") == 5 || talisman.getTag().getInt("circle") == 8) {
            circleX = mediumCircleX;
            circleZ = mediumCircleZ;
            size = 28;
        } else {
            circleX = largeCircleX;
            circleZ = largeCircleZ;
            size = 40;
        }

        Block glyphType;

        if(talisman.getTag().getInt("circle") < 4) {
            glyphType = ModBlocks.WHITE_CIRCLE_GLYPH.get();
        } else if(talisman.getTag().getInt("circle") < 7) {
            glyphType = ModBlocks.INFERNAL_CIRCLE_GLYPH.get();
        } else {
            glyphType = ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get();
        }

        if(pLevel.getBlockState(clickedPos.below()).isSolid() && (pLevel.getBlockState(clickedPos).is(Blocks.AIR) || pLevel.getBlockState(clickedPos).is(ModBlocks.HEART_GLYPH.get()))) {
            boolean valid = true;
            for(int i = 0; i < size; i++) {
                BlockPos placePos = new BlockPos(clickedPos.getX() + circleX[i], clickedPos.getY(), clickedPos.getZ() + circleZ[i]);
                if(!pLevel.getBlockState(placePos).is(Blocks.AIR) || !pLevel.getBlockState(placePos.below()).isSolid()) {
                    valid = false;
                }
            }
            if(valid) {
                if(!pLevel.getBlockState(clickedPos).is(ModBlocks.HEART_GLYPH.get())) {
                    pLevel.setBlockAndUpdate(new BlockPos(clickedPos.getX(), clickedPos.getY(), clickedPos.getZ()), ModBlocks.HEART_GLYPH.get().defaultBlockState());
                }
                for(int i = 0; i < size; i++) {
                    BlockPos placePos = new BlockPos(clickedPos.getX() + circleX[i], clickedPos.getY(), clickedPos.getZ() + circleZ[i]);
                    pLevel.setBlockAndUpdate(placePos, glyphType.defaultBlockState().setValue(STYLE, rand.nextInt(11)));
                }
                return InteractionResult.sidedSuccess(pLevel.isClientSide());
            }
        }

        return super.useOn(pContext);
    }
}
