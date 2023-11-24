package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.Containers;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.recipe.DistilleryRecipe;
import net.outta_space.witchery.screen.DistilleryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

import static net.outta_space.witchery.block.custom.DistilleryBlock.VESSEL_COUNT;

public class DistilleryBlockEntity extends BlockEntity implements MenuProvider {

    public static final int VESSEL_SLOT = 0;
    public static final int INPUT_SLOT_1 = 1;
    public static final int INPUT_SLOT_2 = 2;
    public static final int OUTPUT_SLOT_1 = 3;
    public static final int OUTPUT_SLOT_2 = 4;
    public static final int OUTPUT_SLOT_3 = 5;
    public static final int OUTPUT_SLOT_4 = 6;


    private final ItemStackHandler itemHandler = new ItemStackHandler(7) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                case VESSEL_SLOT -> stack.getItem() == ModItems.CLAY_VESSEL.get();
                case INPUT_SLOT_1, INPUT_SLOT_2 -> true;
                case OUTPUT_SLOT_1, OUTPUT_SLOT_2, OUTPUT_SLOT_3, OUTPUT_SLOT_4 -> false;
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();


    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 525;
    private int bubbleProgress = 0;
    private int bubbleMaxProgress = 15;
    private boolean hasAltar = false;


    public DistilleryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DISTILLERY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                int temp = 0;
                if(hasAltar)
                    temp = 1;
                return switch(pIndex) {
                    case 0 -> DistilleryBlockEntity.this.progress;
                    case 1 -> DistilleryBlockEntity.this.maxProgress;
                    case 2 -> DistilleryBlockEntity.this.bubbleProgress;
                    case 3 -> DistilleryBlockEntity.this.bubbleMaxProgress;
                    case 4 -> temp;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                int temp = 0;
                switch(pIndex) {
                    case 0 -> DistilleryBlockEntity.this.progress = pValue;
                    case 1 -> DistilleryBlockEntity.this.maxProgress = pValue;
                    case 2 -> DistilleryBlockEntity.this.bubbleProgress = pValue;
                    case 3 -> DistilleryBlockEntity.this.bubbleMaxProgress = pValue;
                    case 4 -> temp = pValue;
                }
                DistilleryBlockEntity.this.hasAltar = temp == 1;
            }

            @Override
            public int getCount() {
                return 5;
            }
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for(int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Distillery");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new DistilleryMenu(pContainerId, pPlayerInventory, this, this.data);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }

        return super.getCapability(cap, side);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        lazyItemHandler = LazyOptional.of(() -> itemHandler);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        lazyItemHandler.invalidate();
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        pTag.put("inventory", itemHandler.serializeNBT());
        pTag.putInt("progress", this.progress);
        pTag.putInt("bubbleProgress", this.bubbleProgress);
        pTag.putBoolean("hasAltar", this.hasAltar);


        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        this.progress = pTag.getInt("progress");
        this.bubbleProgress = pTag.getInt("bubbleProgress");
        this.hasAltar = pTag.getBoolean("hasAltar");
    }






    public void tick(Level level, BlockPos pPos, BlockState pState) {

        if(this.itemHandler.getStackInSlot(VESSEL_SLOT).getCount() >= 4) {
            level.setBlockAndUpdate(pPos, pState.setValue(VESSEL_COUNT, 4));
        } else if(this.itemHandler.getStackInSlot(VESSEL_SLOT).isEmpty()) {
            level.setBlockAndUpdate(pPos, pState.setValue(VESSEL_COUNT, 0));
        } else {
            level.setBlockAndUpdate(pPos, pState.setValue(VESSEL_COUNT, this.itemHandler.getStackInSlot(VESSEL_SLOT).getCount()));
        }

        if(hasRecipe() && outputSlotsAreAvailable()) {
            increaseDistillProcess();

            if(progress >= maxProgress) {
                distillItems();
                resetProgress();
            }

            setChanged(level, pPos, pState);
        } else {
            resetProgress();
        }

    }

    private void resetProgress() {
        progress = 0;
        bubbleProgress = 0;
    }

    private void distillItems() {
        Optional<DistilleryRecipe> recipe = getCurrentRecipe();
        NonNullList<ItemStack> resultItemList = recipe.get().getResultItemList();

        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT_1, new ItemStack(resultItemList.get(0).getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() + resultItemList.get(0).getCount()));

        if(resultItemList.size() > 1) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT_2, new ItemStack(resultItemList.get(1).getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_2).getCount() + resultItemList.get(1).getCount()));
        }

        if(resultItemList.size() > 2) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT_3, new ItemStack(resultItemList.get(2).getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_3).getCount() + resultItemList.get(2).getCount()));
        }

        if(resultItemList.size() > 3) {
            this.itemHandler.setStackInSlot(OUTPUT_SLOT_4, new ItemStack(resultItemList.get(3).getItem(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_4).getCount() + resultItemList.get(3).getCount()));
        }

        this.itemHandler.extractItem(VESSEL_SLOT, recipe.get().getVesselCount(), false);
    }

    private void increaseDistillProcess() {
        progress++;
        bubbleProgress++;
        if(bubbleProgress > bubbleMaxProgress)
            bubbleProgress = 0;
    }

    private boolean outputSlotsAreAvailable() {
        Optional<DistilleryRecipe> recipe = getCurrentRecipe();
        NonNullList<ItemStack> resultItemList = recipe.get().getResultItemList();

        ItemStack outputSlot1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_1);
        ItemStack outputSlot2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_2);
        ItemStack outputSlot3 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_3);
        ItemStack outputSlot4 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_4);

        if(resultItemList.size() == 1) {
            return ((outputSlot1.getItem() == resultItemList.get(0).getItem()
                    && outputSlot1.getCount() < outputSlot1.getMaxStackSize())
                    || outputSlot1.isEmpty());
        }

        if(resultItemList.size() == 2) {
            return ((outputSlot1.getItem() == resultItemList.get(0).getItem()
                    && outputSlot1.getCount() < outputSlot1.getMaxStackSize())
                    || outputSlot1.isEmpty())

                    && ((outputSlot2.getItem() == resultItemList.get(1).getItem()
                    && outputSlot2.getCount() < outputSlot2.getMaxStackSize())
                    || outputSlot2.isEmpty());
        }

        if(resultItemList.size() == 3) {
            return ((outputSlot1.getItem() == resultItemList.get(0).getItem()
                    && outputSlot1.getCount() < outputSlot1.getMaxStackSize())
                    || outputSlot1.isEmpty())

                    && ((outputSlot2.getItem() == resultItemList.get(1).getItem()
                    && outputSlot2.getCount() < outputSlot2.getMaxStackSize())
                    || outputSlot2.isEmpty())

                    && ((outputSlot3.getItem() == resultItemList.get(2).getItem()
                    && outputSlot3.getCount() < outputSlot3.getMaxStackSize())
                    || outputSlot3.isEmpty());
        }

        return ((outputSlot1.getItem() == resultItemList.get(0).getItem()
                && outputSlot1.getCount() < outputSlot1.getMaxStackSize())
                || outputSlot1.isEmpty())

                && ((outputSlot2.getItem() == resultItemList.get(1).getItem()
                && outputSlot2.getCount() < outputSlot2.getMaxStackSize())
                || outputSlot2.isEmpty())

                && ((outputSlot3.getItem() == resultItemList.get(2).getItem()
                && outputSlot3.getCount() < outputSlot3.getMaxStackSize())
                || outputSlot3.isEmpty())

                && ((outputSlot4.getItem() == resultItemList.get(3).getItem()
                && outputSlot4.getCount() < outputSlot4.getMaxStackSize())
                || outputSlot4.isEmpty());
    }

    private Optional<DistilleryRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());
        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(DistilleryRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasRecipe() {
        Optional<DistilleryRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        return true;

//        return (this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem() == ModItems.OIL_OF_VITRIOL.get()
//            && this.itemHandler.getStackInSlot(INPUT_SLOT_2).getItem() == Items.DIAMOND)
//                || (this.itemHandler.getStackInSlot(INPUT_SLOT_2).getItem() == ModItems.OIL_OF_VITRIOL.get()
//                && this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem() == Items.DIAMOND);
    }


}
