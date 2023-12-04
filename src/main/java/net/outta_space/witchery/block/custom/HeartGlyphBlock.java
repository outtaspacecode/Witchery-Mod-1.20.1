package net.outta_space.witchery.block.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.outta_space.witchery.block.entity.HeartGlyphBlockEntity;
import net.outta_space.witchery.block.entity.ModBlockEntities;
import org.jetbrains.annotations.Nullable;

public class HeartGlyphBlock extends BaseEntityBlock {

    public static final BooleanProperty IS_ACTIVE = BooleanProperty.create("is_active");
    public static final IntegerProperty STYLE = IntegerProperty.create("style", 0, 11);

    public HeartGlyphBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(IS_ACTIVE, false));
        this.registerDefaultState(this.defaultBlockState().setValue(STYLE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(IS_ACTIVE)
                .add(STYLE);
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {

            if(!pLevel.isClientSide()) {
                if(!pState.getValue(IS_ACTIVE)) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, true));
                    return InteractionResult.SUCCESS;
                }

            }

            return InteractionResult.CONSUME;

//        return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
    }


    private VoxelShape SHAPE = box(0, 0, 0, 16, 1, 16);

    @Override
    public VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new HeartGlyphBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.HEART_GLYPH_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
