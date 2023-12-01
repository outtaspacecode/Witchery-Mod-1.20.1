package net.outta_space.witchery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.PushReaction;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.entity.AltarBlockEntity;
import net.outta_space.witchery.block.entity.ModBlockEntities;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Objects;

import static java.util.Collections.sort;

public class AltarBlock extends BaseEntityBlock {
    public static final BooleanProperty IS_MULTIBLOCK = BooleanProperty.create("is_multiblock");
    public AltarBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_MULTIBLOCK, false));
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Override
    public void setPlacedBy(Level pLevel, BlockPos pPos, BlockState pState, @Nullable LivingEntity pPlacer, ItemStack pStack) {
        super.setPlacedBy(pLevel, pPos, pState, pPlacer, pStack);
        updateMultiblock(pLevel, pPos, null);
    }

    @Override
    public void playerWillDestroy(Level pLevel, BlockPos pPos, BlockState pState, Player pPlayer) {
        super.playerWillDestroy(pLevel, pPos, pState, pPlayer);
        updateMultiblock(pLevel, pPos, pPos);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_MULTIBLOCK);
    }

    private void updateMultiblock(Level pLevel, BlockPos pPos, BlockPos exclude) {
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
                        AltarBlockEntity coreBlock = (AltarBlockEntity) pLevel.getBlockEntity(blockPos);
                        assert coreBlock != null;
                        coreBlock.setCore(new BlockPos(xCoords[3], visited.get(0).getY(), zCoords[3]));
                    }
//                    core = new BlockPos(xCoords[3], visited.get(0).getY(), zCoords[3]);
                } else {
                    for (BlockPos blockPos : visited) {
                        pLevel.setBlockAndUpdate(blockPos, ModBlocks.ALTAR_BLOCK.get().defaultBlockState().setValue(IS_MULTIBLOCK, false));
                        AltarBlockEntity coreBlock = (AltarBlockEntity) pLevel.getBlockEntity(blockPos);
                        assert coreBlock != null;
                        coreBlock.setCore(null);
                    }
//                    core = null;
                }

            } else {
                for (BlockPos blockPos : visited) {
                    pLevel.setBlockAndUpdate(blockPos, ModBlocks.ALTAR_BLOCK.get().defaultBlockState().setValue(IS_MULTIBLOCK, false));
                    AltarBlockEntity coreBlock = (AltarBlockEntity) pLevel.getBlockEntity(blockPos);
                    assert coreBlock != null;
                    coreBlock.setCore(null);
                }
//                core = null;
            }
        }
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new AltarBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.ALTAR_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
