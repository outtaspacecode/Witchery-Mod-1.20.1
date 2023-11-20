package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.outta_space.witchery.screen.DistilleryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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

        if(hasRecipe() && clayVesselsAreInSlot() && outputSlotsAreAvailable()) {
            increaseDistillProcess();

            if(progress >= maxProgress) {
                distillItems();
                resetProgress();
            }
        } else {
            resetProgress();
        }

    }

    private void resetProgress() {
        progress = 0;
        bubbleProgress = 0;
    }

    private void distillItems() {
        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);
        this.itemHandler.extractItem(INPUT_SLOT_2, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT_1, new ItemStack(ModItems.DIAMOND_VAPOR.get(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() + 1));
        this.itemHandler.setStackInSlot(OUTPUT_SLOT_2, new ItemStack(ModItems.DIAMOND_VAPOR.get(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_2).getCount() + 1));
        this.itemHandler.setStackInSlot(OUTPUT_SLOT_3, new ItemStack(ModItems.ODOUR_OF_PURITY.get(), this.itemHandler.getStackInSlot(OUTPUT_SLOT_3).getCount() + 1));
        this.itemHandler.setStackInSlot(OUTPUT_SLOT_4, ItemStack.EMPTY);

        this.itemHandler.extractItem(VESSEL_SLOT, 3, false);
    }

    private void increaseDistillProcess() {
        progress++;
        bubbleProgress++;
        if(bubbleProgress > bubbleMaxProgress)
            bubbleProgress = 0;
    }

    private boolean outputSlotsAreAvailable() {
        ItemStack outputSlot1 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_1);
        ItemStack outputSlot2 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_2);
        ItemStack outputSlot3 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_3);
        ItemStack outputSlot4 = this.itemHandler.getStackInSlot(OUTPUT_SLOT_4);

        return ((outputSlot1.getItem() == ModItems.DIAMOND_VAPOR.get()
                && outputSlot1.getCount() < outputSlot1.getMaxStackSize())
                || outputSlot1.isEmpty())

                && ((outputSlot2.getItem() == ModItems.DIAMOND_VAPOR.get()
                && outputSlot2.getCount() < outputSlot2.getMaxStackSize())
                || outputSlot2.isEmpty())

                && ((outputSlot3.getItem() == ModItems.ODOUR_OF_PURITY.get()
                && outputSlot3.getCount() < outputSlot3.getMaxStackSize())
                || outputSlot3.isEmpty())

                && ((outputSlot4.getItem() == ItemStack.EMPTY.getItem()
                && outputSlot4.getCount() < outputSlot4.getMaxStackSize())
                || outputSlot4.isEmpty());
    }


    private boolean clayVesselsAreInSlot() {
        return this.itemHandler.getStackInSlot(VESSEL_SLOT).getItem() == ModItems.CLAY_VESSEL.get()
                && this.itemHandler.getStackInSlot(VESSEL_SLOT).getCount() >= 3;
    }

    private boolean hasRecipe() {
        return (this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem() == ModItems.OIL_OF_VITRIOL.get()
            && this.itemHandler.getStackInSlot(INPUT_SLOT_2).getItem() == Items.DIAMOND)
                || (this.itemHandler.getStackInSlot(INPUT_SLOT_2).getItem() == ModItems.OIL_OF_VITRIOL.get()
                && this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem() == Items.DIAMOND);
    }


}
