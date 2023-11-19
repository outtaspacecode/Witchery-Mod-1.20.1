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
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.screen.DistilleryMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

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
    private int maxProgress = 200;


    public DistilleryBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.DISTILLERY_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch(pIndex) {
                    case 0 -> DistilleryBlockEntity.this.progress;
                    case 1 -> DistilleryBlockEntity.this.maxProgress;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex) {
                    case 0 -> DistilleryBlockEntity.this.progress = pValue;
                    case 1 -> DistilleryBlockEntity.this.maxProgress = pValue;
                }
            }

            @Override
            public int getCount() {
                return 2;
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


        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
    }

    public void tick(Level level, BlockPos pPos, BlockState pState) {

        if(isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            increaseDistillProcess();
            setChanged(level, pPos, pState);

            if(hasProgressFinished()) {
                distillItem();
                resetProgress();
            }
        } else {
            resetProgress();
        }

    }

    private void distillItem() {
        this.itemHandler.extractItem(INPUT_SLOT_1, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT_1, new ItemStack(ModItems.DIAMOND_VAPOR.get(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() + 1));
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseDistillProcess() {
        this.progress++;
    }

    private boolean hasRecipe() {
        return canInsertAmountIntoOutputSlot(1) && canInsertItemIntoOutputSlot(ModItems.DIAMOND_VAPOR.get())
                && hasRecipeItemInInputSlot();
    }

    private boolean hasRecipeItemInInputSlot() {
        return this.itemHandler.getStackInSlot(INPUT_SLOT_1).getItem() == ModItems.WHIFF_OF_MAGIC.get();
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).is(item) ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getMaxStackSize() >
                this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() + count;
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT_1).getMaxStackSize();
    }

}
