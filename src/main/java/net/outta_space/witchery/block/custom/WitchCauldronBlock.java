package net.outta_space.witchery.block.custom;


import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.CauldronBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.outta_space.witchery.block.ModBlocks;
import org.jetbrains.annotations.NotNull;


public class WitchCauldronBlock extends Block {
    public static final IntegerProperty FILL_LEVEL = IntegerProperty.create("fill_level", 0, 3);
    public static final BooleanProperty IS_BOILING = BooleanProperty.create("is_boiling");
    public WitchCauldronBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FILL_LEVEL, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(IS_BOILING, false));
    }

    private static final VoxelShape OUTSIDE = box(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);
    private static final VoxelShape INSIDE = box(3.0D, 4.0D, 3.0D, 13.0D, 13.0D, 13.0D);
    //public static final VoxelShape SHAPE = Shapes.join(OUTSIDE, Shapes.or(box(2.0D, 12.0D, 2.0D, 14.0D, 13.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);
    public static final VoxelShape SHAPE = OUTSIDE;


    //protected double FILL_LEVEL = 0.0D;
    protected static final int FULL = 3;
    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState pState, Level pLevel, BlockPos pPos, Player pPlayer, InteractionHand pHand, BlockHitResult pHit) {
        ItemStack itemstack = pPlayer.getItemInHand(pHand);
        if(!pLevel.isClientSide()) {
            if (itemstack.is(Items.WATER_BUCKET) && pState.getValue(FILL_LEVEL) < FULL) {
                pLevel.playSound((Player) null, pPos, SoundEvents.BUCKET_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
                int fill_level = pState.getValue(FILL_LEVEL) + 1;
                pLevel.setBlockAndUpdate(pPos, ModBlocks.WITCH_CAULDRON.get().defaultBlockState().setValue(FILL_LEVEL, fill_level));
                pPlayer.setItemInHand(pHand, ItemUtils.createFilledResult(itemstack, pPlayer, new ItemStack(Items.BUCKET)));
                //pPlayer.sendSystemMessage(Component.literal("Filled cauldron to " + (pState.getValue(FILL_LEVEL) + 1)));
                return InteractionResult.SUCCESS;
            } else if (itemstack.is(Items.BUCKET) && pState.getValue(FILL_LEVEL) > 0) {
                pLevel.playSound((Player) null, pPos, SoundEvents.BUCKET_FILL, SoundSource.BLOCKS, 1.0F, 1.0F);
                int fill_level = pState.getValue(FILL_LEVEL) - 1;
                pLevel.setBlockAndUpdate(pPos, ModBlocks.WITCH_CAULDRON.get().defaultBlockState().setValue(FILL_LEVEL, fill_level));
                pPlayer.setItemInHand(pHand, ItemUtils.createFilledResult(itemstack, pPlayer, new ItemStack(Items.WATER_BUCKET)));
                //pPlayer.sendSystemMessage(Component.literal("Emptied cauldron to " + (pState.getValue(FILL_LEVEL) - 1)));
                return InteractionResult.SUCCESS;
            }
            return InteractionResult.FAIL;
        }

        return InteractionResult.CONSUME;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FILL_LEVEL)
                .add(IS_BOILING);
    }
}
