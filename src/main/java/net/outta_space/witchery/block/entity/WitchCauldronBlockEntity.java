package net.outta_space.witchery.block.entity;

import mezz.jei.api.recipe.RecipeType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.HopperBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.HopperBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.items.ItemStackHandler;
import net.outta_space.witchery.recipe.WitchCauldronRecipe;
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;


import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static net.minecraft.world.phys.shapes.Shapes.box;
import static net.outta_space.witchery.block.custom.WitchCauldronBlock.*;

public class WitchCauldronBlockEntity extends BlockEntity {
    public WitchCauldronBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WITCH_CAULDRON_BE.get(), pPos, pBlockState);
    }

    ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return true;
        }
    };

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.putInt("warmUp", warmUp);
        pTag.put("itemHandler", itemHandler.serializeNBT());

        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("itemHandler"));
        cookingItems.clear();
        for(int i = 0; i < 5; i++) {
            if(itemHandler.getStackInSlot(i) != ItemStack.EMPTY) {
                cookingItems.add(i, itemHandler.getStackInSlot(i));
            }
        }
        this.warmUp = pTag.getInt("warmup");
    }

    private int warmUp = 60;
    private int cookTimer = 160;
    private final List<Block> heatSources = List.of(Blocks.LAVA, Blocks.FIRE, Blocks.CAMPFIRE, Blocks.SOUL_CAMPFIRE, Blocks.MAGMA_BLOCK);
    private List<ItemStack> cookingItems = new ArrayList<>();

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {
        if(pState.getValue(FILL_LEVEL) >= 3) {
            if (heatSources.contains(pLevel.getBlockState(pPos.below()).getBlock())) {
                if(warmUp > 0) {
                    warmUp--;
                } else {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_BOILING, true));
                }
            } else {
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_BOILING, false));
                resetWarmUp();
            }
        } else {
            cookingItems.clear();
            pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_BOILING, false));
            resetWarmUp();
        }


        if(pState.getValue(IS_BOILING)) {
            if(cookingItems.size() < 5) {
                AABB aabb = new AABB(pPos).contract(0.0625, 0.0625, 0.0625);

                List<ItemEntity> itemEntities = pLevel.getEntitiesOfClass(ItemEntity.class, aabb);

                for (int i = 0; i < itemEntities.size(); i++) {
                    cookingItems.add(i, itemEntities.get(i).getItem());
                    itemEntities.get(i).kill();
                }

                for (int i = 0; i < cookingItems.size(); i++) {
                    itemHandler.setStackInSlot(i, cookingItems.get(i));
                }
                for (int i = cookingItems.size(); i < 5; i++) {
                    itemHandler.setStackInSlot(i, ItemStack.EMPTY);
                }
            }


            if(hasRecipe()) {
                if(cookTimer > 0) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_COOKING, true));
                    cookTimer--;
                } else {
                    pLevel.setBlockAndUpdate(pPos, pState.getBlock().defaultBlockState());
                    cookItem();
                    resetCookTimer();
                }
            }

        }
        setChanged(level, pPos, pState);

    }

    private void cookItem() {
        Optional<WitchCauldronRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        for(int i = 0; i < itemHandler.getSlots(); i++) {
            itemHandler.setStackInSlot(i, ItemStack.EMPTY);
        }
        cookingItems.clear();

        level.playSeededSound(null, this.worldPosition.getX() + 0.5D, this.worldPosition.getY(), this.worldPosition.getZ() + 0.5D, SoundEvents.PLAYER_LEVELUP, SoundSource.BLOCKS, 0.5F, 0, 8);
        level.addFreshEntity(new ItemEntity(level, this.worldPosition.getX() + 0.5D, this.worldPosition.getY() + 1.0D, this.worldPosition.getZ() + 0.5D, resultItem));
    }

    private void resetCookTimer() {
        cookTimer = 160;
    }

    private boolean hasRecipe() {
        Optional<WitchCauldronRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        return true;
    }

    private Optional<WitchCauldronRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(WitchCauldronRecipe.Type.INSTANCE, inventory, level);
    }

    private void resetWarmUp() {
        warmUp = 60;
    }
}
