package net.outta_space.witchery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.entity.FumeFunnelBlockEntity;
import net.outta_space.witchery.block.entity.ModBlockEntities;
import net.outta_space.witchery.block.entity.WitchOvenBlockEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FumeFunnelBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty IS_PIPE = IntegerProperty.create("is_pipe", 0, 3);
    private static final VoxelShape DEFAULT = box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    private static final VoxelShape PIPE_WEST = box(10.0D, 0.0D, 5.0D, 16.0D, 12.0D, 11.0D);
    private static final VoxelShape PIPE_NORTH = box(5.0D, 0.0D, 10.0D, 11.0D, 12.0D, 16.0D);
    private static final VoxelShape PIPE_EAST = box(0.0D, 0.0D, 5.0D, 6.0D, 12.0D, 11.0D);
    private static final VoxelShape PIPE_SOUTH = box(5.0D, 0.0D, 0.0D, 11.0D, 12.0D, 6.0D);

    public FumeFunnelBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_PIPE, 0));
    }

    public BlockState rotate(BlockState pState, Rotation pRot) {
        return pState.setValue(FACING, pRot.rotate(pState.getValue(FACING)));
    }

    public BlockState mirror(BlockState pState, Mirror pMirror) {
        return pState.rotate(pMirror.getRotation(pState.getValue(FACING)));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        if(pState.getValue(IS_PIPE) == 1) {
            if(pState.getValue(FACING) == Direction.WEST) {
                return PIPE_WEST;
            }
            if(pState.getValue(FACING) == Direction.NORTH) {
                return PIPE_NORTH;
            }
            if(pState.getValue(FACING) == Direction.EAST) {
                return PIPE_EAST;
            }
            if(pState.getValue(FACING) == Direction.SOUTH) {
                return PIPE_SOUTH;
            }
        }
        return DEFAULT;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext pContext) {
        int is_pipe = 0;
        if(pContext.getLevel().getBlockState(pContext.getClickedPos().below()).is(ModBlocks.WITCH_OVEN.get())) {
            is_pipe = 1;
        }

        return this.defaultBlockState().setValue(FACING, pContext.getHorizontalDirection().getOpposite()).setValue(IS_PIPE, is_pipe);
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FACING)
                .add(IS_PIPE);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FumeFunnelBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.FUME_FUNNEL_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
