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
import net.minecraft.world.level.block.FurnaceBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.FurnaceBlockEntity;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraftforge.common.Tags;
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
import java.util.function.ToIntFunction;

import static net.outta_space.witchery.block.custom.WitchOvenBlock.BURNING;

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

    private int burnProgress = -1;
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
            increaseBurnProgress();

            if(burnProgress >= burnTime) {
                resetBurnProgress(level, pPos, pState);
            }

            if(hasRecipe() && hasOutputSlot()) {

                increaseSmeltPorgress();

                if(progress >= maxProgress) {
                    smeltItem();

                    if(hasVesselRecipe() && hasVesselOutputSlot()) {
                        tryForVesselMagic();
                    }

                    resetProgress();
                }
            } else {
                resetProgress();
            }

            setChanged(level, pPos, pState);
        } else {
            if (burnProgress == -1 && hasRecipe() && hasOutputSlot()) {
                checkForValidFuel(level, pPos, pState);
            }

            if(!fuelIsBurning()) {
                if(progress > 0) {
                    progress -= 2;
                }


                if(progress < 0) {
                    progress = 0;
                }
            }
        }

    }

    private boolean hasVesselOutputSlot() {
        Optional<WitchOvenRecipe> recipe = getCurrentVesselRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        return (this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getCount() < resultItem.getMaxStackSize()
                && this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getItem() == resultItem.getItem())
                || this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).isEmpty();
    }

    private Optional<WitchOvenRecipe> getCurrentVesselRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(WitchOvenRecipe.Type.INSTANCE, inventory, level);
    }

    private boolean hasVesselRecipe() {
        Optional<WitchOvenRecipe> recipe = getVesselRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        return true;
    }

    private Optional<WitchOvenRecipe> getVesselRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(WitchOvenRecipe.Type.INSTANCE, inventory, level);
    }

    private static final int VESSEL_CHANCE = 30;
    private void tryForVesselMagic() {
        Random rand = new Random();
        if(rand.nextInt() < VESSEL_CHANCE) {
            Optional<WitchOvenRecipe> recipe = getVesselRecipe();
            ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

            this.itemHandler.extractItem(VESSEL_SLOT, 1, false);

            this.itemHandler.setStackInSlot(VESSEL_OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                    this.itemHandler.getStackInSlot(VESSEL_OUTPUT_SLOT).getCount() + resultItem.getCount()));
        }
    }

    private void resetProgress() {
        progress = 0;
    }

    private void smeltItem() {
        Optional<SmeltingRecipe> recipe = getCurrentRecipe();
        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        this.itemHandler.extractItem(INPUT_SLOT, 1, false);

        this.itemHandler.setStackInSlot(OUTPUT_SLOT, new ItemStack(resultItem.getItem(),
                this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() + resultItem.getCount()));
    }

    private void increaseSmeltPorgress() {
        progress++;
    }

    private boolean hasOutputSlot() {
        Optional<SmeltingRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        ItemStack resultItem = recipe.get().getResultItem(getLevel().registryAccess());

        return (this.itemHandler.getStackInSlot(OUTPUT_SLOT).getCount() < resultItem.getMaxStackSize()
                && this.itemHandler.getStackInSlot(OUTPUT_SLOT).getItem() == resultItem.getItem())
                || this.itemHandler.getStackInSlot(OUTPUT_SLOT).isEmpty();
    }

    private boolean hasRecipe() {
        Optional<SmeltingRecipe> recipe = getCurrentRecipe();

        if(recipe.isEmpty()) {
            return false;
        }

        maxProgress = recipe.get().getCookingTime();
        return true;
    }

    private Optional<SmeltingRecipe> getCurrentRecipe() {
        SimpleContainer inventory = new SimpleContainer(this.itemHandler.getSlots());

        for(int i = 0; i < this.itemHandler.getSlots(); i++) {
            inventory.setItem(i, this.itemHandler.getStackInSlot(i));
        }

        return this.level.getRecipeManager().getRecipeFor(RecipeType.SMELTING, inventory, level);
    }

    private void checkForValidFuel(Level pLevel, BlockPos pPos, BlockState pState) {
        if(!this.itemHandler.getStackInSlot(FUEL_SLOT).isEmpty()) {
            burnTime = net.minecraftforge.common.ForgeHooks.getBurnTime(this.itemHandler.getStackInSlot(FUEL_SLOT), null);
            burnProgress = 0;
            pLevel.setBlockAndUpdate(pPos, pState.setValue(BURNING, true));
            if(this.itemHandler.getStackInSlot(FUEL_SLOT).getItem() == Items.LAVA_BUCKET) {
                this.itemHandler.setStackInSlot(FUEL_SLOT, new ItemStack(Items.BUCKET, 1));
            } else {
                this.itemHandler.extractItem(FUEL_SLOT, 1, false);
            }
        }
    }

    private void resetBurnProgress(Level pLevel, BlockPos pPos, BlockState pState) {
        burnProgress = -1;
        if(this.itemHandler.getStackInSlot(FUEL_SLOT).isEmpty()) {
            pLevel.setBlockAndUpdate(pPos, pState.setValue(BURNING, false));
        }
    }

    private void increaseBurnProgress() {
        burnProgress++;
    }

    private boolean fuelIsBurning() {
        return burnProgress >= 0;
    }


}
