package net.outta_space.witchery.block.custom;


import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
import net.outta_space.witchery.block.entity.ModBlockEntities;
import net.outta_space.witchery.block.entity.WitchCauldronBlockEntity;
import net.outta_space.witchery.sound.ModSounds;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;


public class WitchCauldronBlock extends BaseEntityBlock {
    public static final IntegerProperty FILL_LEVEL = IntegerProperty.create("fill_level", 0, 3);
    public static final BooleanProperty IS_BOILING = BooleanProperty.create("is_boiling");
    public static final BooleanProperty IS_COOKING = BooleanProperty.create("is_cooking");


    private static final VoxelShape OUTSIDE = box(1.0D, 0.0D, 1.0D, 15.0D, 13.0D, 15.0D);
    private static final VoxelShape INSIDE = box(3.0D, 4.0D, 3.0D, 13.0D, 13.0D, 13.0D);
    //public static final VoxelShape SHAPE = Shapes.join(OUTSIDE, Shapes.or(box(2.0D, 12.0D, 2.0D, 14.0D, 13.0D, 14.0D), INSIDE), BooleanOp.ONLY_FIRST);
    public static final VoxelShape SHAPE = OUTSIDE;


    //protected double FILL_LEVEL = 0.0D;
    protected static final int FULL = 3;

    public WitchCauldronBlock(Properties pProperties) {
        super(pProperties);
        this.registerDefaultState(this.defaultBlockState().setValue(FILL_LEVEL, 0));
        this.registerDefaultState(this.defaultBlockState().setValue(IS_BOILING, false));
        this.registerDefaultState(this.defaultBlockState().setValue(IS_COOKING, false));
    }

    @Override
    public @NotNull VoxelShape getShape(BlockState pState, BlockGetter pLevel, BlockPos pPos, CollisionContext pContext) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState pState) {
        return RenderShape.MODEL;
    }

    public void animateTick(BlockState pState, Level pLevel, BlockPos pPos, RandomSource pRandom) {
        if (pState.getValue(IS_BOILING)) {
            double d0 = (double)pPos.getX() + 0.5D;
            double d1 = (double)pPos.getY();
            double d2 = (double)pPos.getZ() + 0.5D;
            if (pRandom.nextDouble() < 0.5D) {
                pLevel.playLocalSound(d0, d1, d2, ModSounds.BLOP.get(), SoundSource.BLOCKS, 0.5F, 1.6F, false);
            }

            pLevel.addParticle(ParticleTypes.BUBBLE_POP, (double)pPos.getX() + (pRandom.nextDouble() * (10.0D / 16.0D) + 0.15D),
                    (double)pPos.getY() + (11.0D / 16.0D) + 0.05D, (double)pPos.getZ() + (pRandom.nextDouble() * (10.0D / 16.0D) + 0.15D), 0.0D, 0.0D, 0.0D);

        }

        if(pState.getValue(IS_COOKING)) {
            pLevel.addParticle(ParticleTypes.FIREWORK, (double)pPos.getX() + 0.5D, (double)pPos.getY() + (11.0D / 16.0D) + 0.05D,
                    (double)pPos.getZ() + 0.5D, (0.5D - pRandom.nextDouble()) * 0.25, 0.25D, (0.5D - pRandom.nextDouble()) * 0.25);
            if(pRandom.nextDouble() < 0.75D) {
                pLevel.playLocalSound(pPos.getX() + 0.5D, pPos.getY(), pPos.getZ() + 0.5D, SoundEvents.FIREWORK_ROCKET_LAUNCH, SoundSource.BLOCKS, 0.5f, 0.6f + (float)pRandom.nextDouble(), false);
            }
        }
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable BlockGetter pLevel, List<Component> pTooltip, TooltipFlag pFlag) {
        if(Screen.hasShiftDown()) {
            pTooltip.add(Component.literal("Obtained by using anointing paste on a cauldron"));
        } else {
            pTooltip.add(Component.literal("§7Press <SHIFT> for more info"));
        }


        super.appendHoverText(pStack, pLevel, pTooltip, pFlag);
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
        }
        if(!(itemstack.is(Items.WATER_BUCKET) && pState.getValue(FILL_LEVEL) < FULL)) {
            return super.use(pState, pLevel, pPos, pPlayer, pHand, pHit);
        }
        return InteractionResult.CONSUME;
    }


    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> pBuilder) {
        pBuilder.add(FILL_LEVEL)
                .add(IS_BOILING)
                .add(IS_COOKING);
    }

    @Nullable
    @Override
    public BlockEntity newBlockEntity(BlockPos pPos, BlockState pState) {
        return new WitchCauldronBlockEntity(pPos, pState);
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level pLevel, BlockState pState, BlockEntityType<T> pBlockEntityType) {
        if(pLevel.isClientSide()) {
            return null;
        }

        return createTickerHelper(pBlockEntityType, ModBlockEntities.WITCH_CAULDRON_BE.get(),
                (pLevel1, pPos, pState1, pBlockEntity) -> pBlockEntity.tick(pLevel1, pPos, pState1));
    }
}
