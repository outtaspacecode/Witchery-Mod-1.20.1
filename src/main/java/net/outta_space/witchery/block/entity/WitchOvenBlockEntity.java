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
import net.minecraft.world.item.crafting.AbstractCookingRecipe;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.item.crafting.SmeltingRecipe;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.recipe.WitchOvenRecipe;
import net.outta_space.witchery.screen.WitchOvenMenu;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.Random;

public class WitchOvenBlockEntity extends BlockEntity implements MenuProvider {

    private static final int INPUT_SLOT = 0;
    private static final int OUTPUT_SLOT = 1;
    private static final int FUEL_SLOT = 2;
    private static final int VESSEL_SLOT = 3;
    private static final int VESSEL_OUTPUT_SLOT = 4;


    private final ItemStackHandler itemHandler = new ItemStackHandler(5) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }

        @Override
        public boolean isItemValid(int slot, @NotNull ItemStack stack) {
            return switch(slot) {
                case INPUT_SLOT -> true;
                case OUTPUT_SLOT, VESSEL_OUTPUT_SLOT -> false;
                case FUEL_SLOT -> net.minecraftforge.common.ForgeHooks.getBurnTime(stack, null) > 0;
                case VESSEL_SLOT -> stack.getItem() == ModItems.CLAY_VESSEL.get();
                default -> super.isItemValid(slot, stack);
            };
        }
    };


    private LazyOptional<IItemHandler> lazyItemHandler = LazyOptional.empty();

    protected final ContainerData data;
    private int progress = 0;
    private int maxProgress = 200;

    private int burnProgress = 0;
    private int burnTime = 1600;


    public WitchOvenBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.WITCH_OVEN_BE.get(), pPos, pBlockState);
        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch(pIndex) {
                    case 0 -> WitchOvenBlockEntity.this.progress;
                    case 1 -> WitchOvenBlockEntity.this.maxProgress;
                    case 2 -> WitchOvenBlockEntity.this.burnProgress;
                    case 3 -> WitchOvenBlockEntity.this.burnTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex) {
                    case 0 -> WitchOvenBlockEntity.this.progress = pValue;
                    case 1 -> WitchOvenBlockEntity.this.maxProgress = pValue;
                    case 2 -> WitchOvenBlockEntity.this.burnProgress = pValue;
                    case 3 -> WitchOvenBlockEntity.this.burnTime = pValue;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public void drops() {
        SimpleContainer inventory = new SimpleContainer(itemHandler.getSlots());
        for (int i = 0; i < itemHandler.getSlots(); i++) {
            inventory.setItem(i, itemHandler.getStackInSlot(i));
        }

        Containers.dropContents(this.level, this.worldPosition, inventory);
    }


    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {

        if(cap == ForgeCapabilities.ITEM_HANDLER) {
            return lazyItemHandler.cast();
        }


        return super.getCapability(cap, side);
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Witch Oven");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new WitchOvenMenu(pContainerId, pPlayerInventory, this, this.data);
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
        pTag.putInt("progress", progress);
        pTag.putInt("maxProgress", maxProgress);
        pTag.putInt("burnProgress", burnProgress);
        pTag.putInt("burnTime", burnTime);


        super.saveAdditional(pTag);
    }

    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);

        itemHandler.deserializeNBT(pTag.getCompound("inventory"));
        progress = pTag.getInt("progress");
        maxProgress = pTag.getInt("maxProgress");
        burnProgress = pTag.getInt("burnProgress");
        burnTime = pTag.getInt("burnTime");
    }


    public void tick(Level level, BlockPos pPos, BlockState pState) {

        if(fuelIsBurning()) {
            if(burnProgress <= burnTime) {
                increaseBurnProgress();
            } else {
                resetBurnProgress();
            }
        } else {
            checkForValidFuel();
        }

        if(isOutputSlotEmptyOrReceivable() && hasRecipe()) {
            if(fuelIsBurning()) {

                increaseCraftingProcess();
                setChanged(level, pPos, pState);

                if (hasProgressFinished()) {

                    cookItem();
                    if(hasModdedRecipe()) {
                        tryForBottledMagic();
                    }

                    resetProgress();
                }
            } else if(progress > 0 && !(this.itemHandler.getStackInSlot(FUEL_SLOT).getCount() > 0)) {
                this.progress -= 5;

                if(progress < 0) {
                    resetProgress();
                }
            }
        } else {
            resetProgress();
        }

    }

    private boolean fuelIsBurning() {
        return burnProgress > 0;
    }

    private void checkForValidFuel() {
        if(this.itemHandler.getStackInSlot(FUEL_SLOT).getCount() > 0 &&
            hasRecipe()) {
            burnProgress = 1;

            burnTime = net.minecraftforge.common.ForgeHooks.getBurnTime(this.itemHandler.getStackInSlot(FUEL_SLOT), null);
            if (this.itemHandler.getStackInSlot(FUEL_SLOT).getItem() == Items.LAVA_BUCKET) {
                this.itemHandler.setStackInSlot(FUEL_SLOT, new ItemStack(Items.BUCKET, 1));
            } else {
                this.itemHandler.extractItem(FUEL_SLOT, 1, false);
            }
        }
    }

    private void resetBurnProgress() {
        burnProgress = 0;
    }

    private void increaseBurnProgress() {
        burnProgress++;
    }

    private static final int VESSEL_FILL_CHANCE = 30;
    private void tryForBottledMagic() {

        if(this.itemHandler.getStackInSlot(VESSEL_SLOT).getCount() > 0) {
            if(isVesselOutputEmptyOrRecievable()) {
                Random rand = new Random();
                if(rand.nextInt(100) < VESSEL_FILL_CHANCE) {

                    Optional<WitchOvenRecipe> recipe = getModdedRecipe();
                    ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

                    this.itemHandler.extractItem(VESSEL_SLOT, 1, false);

                    this.itemHandler.setStackInSlot(VESSEL_OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                            this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getCount() + resultItem.getCount()));

                }
            }
        } else {
            this.itemHandler.setStackInSlot(VESSEL_OUTPUT_SLOT, null);
        }
    }

    private boolean hasModdedRecipe() {
        Optional<WitchOvenRecipe> recipe = getModdedRecipe();


        if (recipe.isEmpty()) {
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());


        return canInsertAmountIntoVesselSlot(resultItem.getCount())
                && canInsertItemIntoVesselSlot(resultItem.getItem());
    }

    private boolean canInsertItemIntoVesselSlot(Item item) {
        return this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).is(item) ||
                this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoVesselSlot(int count) {
        return this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getCount() + count <=
                this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getMaxStackSize();
    }

    private Optional<WitchOvenRecipe> getModdedRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(WitchOvenRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean isVesselOutputEmptyOrRecievable() {
        return this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).isEmpty() ||
                this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getMaxStackSize();
    }

    private void cookItem() {
        Optional<SmeltingRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + resultItem.getCount()));
    }

    private void resetProgress() {
        this.progress = 0;
    }

    private boolean hasProgressFinished() {
        return this.progress >= this.maxProgress;
    }

    private void increaseCraftingProcess() {
        this.progress++;
    }

    private boolean hasRecipe() {
        Optional<SmeltingRecipe> recipe = getCurrentRecipe();


        if (recipe.isEmpty()) {
            maxProgress = 200;
            return false;
        }
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        maxProgress = resultItem.getBurnTime(RecipeType.SMELTING);

        return canInsertAmountIntoOutputSlot(resultItem.getCount())
                && canInsertItemIntoOutputSlot(resultItem.getItem());
    }

    private Optional<SmeltingRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, inventory, level);
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).is(item) ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(int count) {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + count <=
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty() ||
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < this.itemHandler.getStackInSlot(OUTPUT_SLOT).getMaxStackSize();
    }
}
