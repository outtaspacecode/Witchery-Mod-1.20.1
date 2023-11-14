package net.outta_space.witchery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.outta_space.witchery.block.ModBlocks;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static java.util.Collections.sort;

public class AltarBlock extends Block {
    public static final BooleanProperty IS_MULTIBLOCK = BooleanProperty.create("is_multiblock");
    private BlockState core = null;
    private ArrayList<BlockPos> positions = new ArrayList<>();
    public AltarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_MULTIBLOCK, false));
    }


    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        updateMultiblock(pLevel, pPos, null, (Player)pPlacer);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
        updateMultiblock(pLevel, pPos, pPos, pPlayer);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_MULTIBLOCK);
    }

    private void updateMultiblock(Level pLevel, BlockPos pPos, BlockPos exclude, Player pPlayer) {
        if(!pLevel.isClientSide()) {
            ArrayList<BlockPos> toVisit = new ArrayList<>();
            ArrayList<BlockPos> visited = new ArrayList<>();
            toVisit.add(pPos);

            while (!toVisit.isEmpty() && visited.size() < 10) {

                BlockPos visiting = toVisit.get(0);
                BlockPos[] tile = new BlockPos[]{visiting.north(), visiting.south(), visiting.east(), visiting.west()};

                for (BlockPos blockPos : tile) {
                    if (pLevel.getBlockState(blockPos).getBlock() instanceof AltarBlock) {
                        if(!toVisit.contains(blockPos) && !visited.contains(blockPos)) {
                            toVisit.add(blockPos);
                        }
                    }
                }

                if(pLevel.getBlockState(toVisit.get(0)).getBlock() instanceof AltarBlock) {
                    visited.add(toVisit.get(0));
                    toVisit.remove(0);
                }
            }

//            pPlayer.sendSystemMessage(Component.literal("Blocks in altar: " + (visited.size())));
            visited.remove(exclude);

            if(visited.size() == 6) {
                int[] xCoords = new int[6];
                int[] zCoords = new int[6];
                for(int i = 0; i < visited.size(); i++) {
                    xCoords[i] = visited.get(i).getX();
                    zCoords[i] = visited.get(i).getZ();
                }

                Arrays.sort(xCoords);
                Arrays.sort(zCoords);

                boolean valid = ((xCoords[0] + 2 == xCoords[5]) && (zCoords[0] + 1 == zCoords[5]))
                        || ((zCoords[0] + 2 == zCoords[5]) && (xCoords[0] + 1 == xCoords[5]));

                if(valid) {
                    for (BlockPos blockPos : visited) {
                        pLevel.setBlockAndUpdate(blockPos, ModBlocks.ALTAR_BLOCK.get().defaultBlockState().setValue(IS_MULTIBLOCK, true));
                    }
                } else {
                    for (BlockPos blockPos : visited) {
                        pLevel.setBlockAndUpdate(blockPos, ModBlocks.ALTAR_BLOCK.get().defaultBlockState().setValue(IS_MULTIBLOCK, false));
                    }
                }
            } else {
                for (BlockPos blockPos : visited) {
                    pLevel.setBlockAndUpdate(blockPos, ModBlocks.ALTAR_BLOCK.get().defaultBlockState().setValue(IS_MULTIBLOCK, false));
                }
            }
//
//
//        ArrayList<BlockPos> visited = new ArrayList<>();
//        ArrayList<BlockPos> toVisit = new ArrayList<>();
//        toVisit.add(pPos);
//        boolean valid = true;
//
//        BlockPos toCheck;
//        while(!toVisit.isEmpty()) {
//            toCheck = toVisit.get(0);
//            toVisit.remove(0);
//
//            BlockPos[] tile = new BlockPos[] { toCheck.north(), toCheck.south(), toCheck.east(), toCheck.west() };
//            int altar_length = 0;
//
//            for(int i = 0; i < tile.length; i++) {
//                if(pLevel.getBlockState(tile[i]).getBlock() instanceof AltarBlock) {
//                    if(!visited.contains(toCheck) && !toVisit.contains(toCheck)) {
//                        toVisit.add(toCheck);
//                    }
//                    altar_length++;
//                }
//            }
//
//            if(altar_length < 2 || altar_length > 3) {
//                valid = false;
//            }
//
//            visited.add(toCheck);
//        }
//
//        pPlayer.sendSystemMessage(Component.literal("size of visited array = " + (visited.size())));
//
//        if(!visited.isEmpty()) {
//            for (int i = 0; i < 6; i++) {
//                pLevel.setBlockAndUpdate(visited.get(i), ModBlocks.ALTAR_BLOCK.get().defaultBlockState().setValue(IS_MULTIBLOCK, true));
//            }
//        }


        }
    }
}
