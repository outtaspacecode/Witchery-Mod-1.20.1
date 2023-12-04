package net.outta_space.witchery.block.custom;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
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
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.entity.FilteredFumeFunnelBlockEntity;
import net.outta_space.witchery.block.entity.ModBlockEntities;
import net.outta_space.witchery.item.ModItems;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class FilteredFumeFunnelBlock extends BaseEntityBlock {

    public static final DirectionProperty FACING = BlockStateProperties.HORIZONTAL_FACING;
    public static final IntegerProperty IS_PIPE = IntegerProperty.create("is_pipe", 0, 3);
    private static final VoxelShape DEFAULT = box(2.0D, 0.0D, 2.0D, 14.0D, 14.0D, 14.0D);
    private static final VoxelShape PIPE_WEST = box(10.0D, 0.0D, 5.0D, 16.0D, 12.0D, 11.0D);
    private static final VoxelShape PIPE_NORTH = box(5.0D, 0.0D, 10.0D, 11.0D, 12.0D, 16.0D);
    private static final VoxelShape PIPE_EAST = box(0.0D, 0.0D, 5.0D, 6.0D, 12.0D, 11.0D);
    private static final VoxelShape PIPE_SOUTH = box(5.0D, 0.0D, 0.0D, 11.0D, 12.0D, 6.0D);

    public FilteredFumeFunnelBlock(Properties pProperties) {
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

        @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        if(!pLevel.isClientSide()) {
            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.ITEM_FRAME_REMOVE_ITEM, SoundSource.BLOCKS, 0.5f, 1f, 0);
            pLevel.setBlockAndUpdate(pPos, ModBlocks.FUME_FUNNEL.get().defaultBlockState().setValue(FumeFunnelBlock.FACING, pState.getValue(FilteredFumeFunnelBlock.FACING))
                    .setValue(FumeFunnelBlock.IS_PIPE, pState.getValue(FilteredFumeFunnelBlock.IS_PIPE)));
            if(!pPlayer.isCreative()) {
                pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5D, pPos.getY() + 1.0D, pPos.getZ() + 0.5D, new ItemStack(ModItems.FUME_FILTER.get(), 1)));
            }
            return InteractionResult.SUCCESS;
        }

        if(pPlayer.isCrouching()) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.CONSUME;
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
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if(Screen.hasShiftDown()) {
            pTooltip.add(Component.literal("Place to the side or above Witch Oven to increase productivity by 20% per funnel - Right click to remove filter"));
        } else {
            pTooltip.add(Component.literal("§7Press <SHIFT> for more info"));
        }


        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new FilteredFumeFunnelBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.FILTERED_FUME_FUNNEL_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
