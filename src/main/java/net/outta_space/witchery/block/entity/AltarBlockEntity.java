package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.screen.AltarMenu;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.outta_space.witchery.block.custom.AltarBlock.INDEX;

public class AltarBlockEntity extends BlockEntity implements MenuProvider {

    private BlockPos core = null;
    private final List<Block> augmentItems = new ArrayList<>(6);
    private List<BlockState> surroundingBlocks = new ArrayList<>();
    private int cooldown;

    private int currentAltarPower;
    private int baseAltarPower;
    private int absorptionMultipler;
    private Double range;
    protected final ContainerData data;

    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR_BE.get(), pPos, pBlockState);
        cooldown = 20;
        for(int i = 0; i < 6; i++) {
            this.augmentItems.add(null);
        }
        resetAltarPower();
        resetBaseAltarPower();
        absorptionMultipler = 1;
        resetRange();

        this.data = new ContainerData() {
            @Override
            public int get(int pIndex) {
                return switch (pIndex) {
                    case 0 -> AltarBlockEntity.this.getCurrentAltarPower();
                    case 1 -> AltarBlockEntity.this.getBaseAltarPower();
                    case 2 -> AltarBlockEntity.this.getAbsorptionMultipler();
                    default -> 0;
                };
            }

            @Override
            public void set(int pIndex, int pValue) {
                switch(pIndex) {
                    case 0 -> AltarBlockEntity.this.currentAltarPower = pValue;
                    case 1 -> AltarBlockEntity.this.baseAltarPower = pValue;
                    case 2 -> AltarBlockEntity.this.absorptionMultipler = pValue;
                }
            }

            @Override
            public int getCount() {
                return 3;
            }
        };
    }

    private void resetRange() {
        range = 14.0D;
    }

    public int getBaseAltarPower() {
        return baseAltarPower;
    }

    public int getCurrentAltarPower() {
        return currentAltarPower;
    }

    public int getAbsorptionMultipler() {
        return  absorptionMultipler;
    }

    private void resetBaseAltarPower() {
        baseAltarPower = 0;
    }

    private void resetAugmentItems() {
        for(int i = 0; i < 6; i++) {
            this.augmentItems.set(i, null);
        }
    }

    private void resetCooldown() {
        this.cooldown = 20;
    }

    public void setCore(BlockPos pPos) {
        core = pPos;
    }
    public BlockPos getCore() {
        return core;
    }

    public void suckPower(int amount) {
        this.currentAltarPower -= amount;
    }
    public void setAugmentItems(int index, Block block) {
        augmentItems.set(index, block);
    }

    @Override
    protected void saveAdditional(CompoundTag pTag) {
        super.saveAdditional(pTag);
        if(core != null) {
            pTag.putIntArray("core", new int[]{core.getX(), core.getY(), core.getZ()});
            pTag.putBoolean("valid", true);
        } else {
            pTag.putIntArray("core", new int[]{});
            pTag.putBoolean("valid", false);
        }
        pTag.putInt("basePower", baseAltarPower);
        pTag.putInt("currentPower", currentAltarPower);
        pTag.putInt("multiplier", absorptionMultipler);
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.getBoolean("valid")) {
            core = new BlockPos(pTag.getIntArray("core")[0], pTag.getIntArray("core")[1], pTag.getIntArray("core")[2]);
        } else {
            core = null;
        }
        baseAltarPower = pTag.getInt("basePower");
        currentAltarPower = pTag.getInt("currentPower");
        absorptionMultipler = pTag.getInt("multiplier");
    }

    private boolean display = true;
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if(core != null) {
            AltarBlockEntity be = (AltarBlockEntity) pLevel.getBlockEntity(core);
            be.setAugmentItems(pState.getValue(INDEX), pLevel.getBlockState(pPos.above()).getBlock());

            if(isCore(pPos)) {

                if(cooldown == 20) {
                    AABB aabb = new AABB(pPos).move(0.5, 0, 0.5).inflate(range);
                    surroundingBlocks = pLevel.getBlockStates(aabb).toList();

                    setAltarPower();
                    setMultipliers();
                    incrementAltarPower();

                }

                if(cooldown > 0) {
                    cooldown--;
                } else {
                    resetCooldown();
                }
            }
        } else {
            surroundingBlocks = null;
            resetAugmentItems();
            resetCooldown();
            resetAltarPower();
            resetBaseAltarPower();
            resetRange();
        }
    }

    private void incrementAltarPower() {
        currentAltarPower += (10 * absorptionMultipler);
        if(currentAltarPower > baseAltarPower) {
            currentAltarPower = baseAltarPower;
        }
    }

    private void setMultipliers() {
        int altarMultiplier = 1;
        absorptionMultipler = 1;

        if(augmentItems.contains(Blocks.WITHER_SKELETON_SKULL)) {
            altarMultiplier += 2;
            absorptionMultipler += 2;
        } else if(augmentItems.contains(Blocks.SKELETON_SKULL)) {
            altarMultiplier += 1;
            absorptionMultipler += 1;
        }

        if(augmentItems.contains(Blocks.TORCH)) {
            absorptionMultipler += 1;
        }

        if(augmentItems.contains(ModBlocks.INFINITY_EGG.get())) {
            altarMultiplier = 50;
            absorptionMultipler = 50;
        }

        baseAltarPower *= altarMultiplier;
    }

    private void resetAltarPower() {
        currentAltarPower = 0;
    }

    private void setAltarPower() {
        List<Block> acceptedBlockTypes = List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MYCELIUM, Blocks.FARMLAND, Blocks.GRASS, Blocks.TALL_GRASS, Blocks.DRAGON_EGG, ModBlocks.INFINITY_EGG.get(),
                Blocks.WATER, Blocks.RED_MUSHROOM, Blocks.BROWN_MUSHROOM, Blocks.MUSHROOM_STEM, Blocks.BROWN_MUSHROOM_BLOCK, Blocks.RED_MUSHROOM_BLOCK, Blocks.VINE, Blocks.SUGAR_CANE, Blocks.CACTUS,
                Blocks.PUMPKIN, Blocks.MELON);

        List<Block> visitedLogBlocks = new ArrayList<>();
        List<Block> visitedLeafBlocks = new ArrayList<>();
        List<Block> visitedFlowerBlocks = new ArrayList<>();
        List<Block> visitedCropBlocks = new ArrayList<>();
        List<Block> visitedSaplingBlocks = new ArrayList<>();
        Map<Block, Integer> map = new HashMap<>();

        for(BlockState pState : surroundingBlocks) {
            if(pState.is(BlockTags.LOGS)) {
                if(!visitedLogBlocks.contains(pState.getBlock())) {
                    visitedLogBlocks.add(pState.getBlock());
                    map.put(pState.getBlock(), 1);
                } else {
                    map.put(pState.getBlock(), map.get(pState.getBlock()) + 1);
                }
            }

            if(pState.is(BlockTags.LEAVES)) {
                if(!visitedLeafBlocks.contains(pState.getBlock())) {
                    visitedLeafBlocks.add(pState.getBlock());
                    map.put(pState.getBlock(), 1);
                } else {
                    map.put(pState.getBlock(), map.get(pState.getBlock()) + 1);
                }
            }

            if(pState.is(BlockTags.FLOWERS)) {
                if(!visitedFlowerBlocks.contains(pState.getBlock())) {
                    visitedFlowerBlocks.add(pState.getBlock());
                    map.put(pState.getBlock(), 1);
                } else {
                    map.put(pState.getBlock(), map.get(pState.getBlock()) + 1);
                }
            }

            if(pState.is(BlockTags.CROPS)) {
                if(!visitedCropBlocks.contains(pState.getBlock())) {
                    visitedCropBlocks.add(pState.getBlock());
                    map.put(pState.getBlock(), 1);
                } else {
                    map.put(pState.getBlock(), map.get(pState.getBlock()) + 1);
                }
            }

            if(pState.is(BlockTags.SAPLINGS)) {
                if(!visitedSaplingBlocks.contains(pState.getBlock())) {
                    visitedSaplingBlocks.add(pState.getBlock());
                    map.put(pState.getBlock(), 1);
                } else {
                    map.put(pState.getBlock(), map.get(pState.getBlock()) + 1);
                }
            }


            for(Block blocks : acceptedBlockTypes) {
                if(pState.is(blocks)) {
                    if(!map.containsKey(pState.getBlock())) {
                        map.put(pState.getBlock(), 1);
                    } else {
                        map.put(pState.getBlock(), map.get(pState.getBlock()) + 1);
                    }
                }
            }
        }

//        System.out.println(map.toString());

        resetBaseAltarPower();
        for(Block visitedBlock : visitedLogBlocks) {
            baseAltarPower += Math.min(map.get(visitedBlock), 18) * 3;
        }

        for(Block visitedBlock : visitedLeafBlocks) {
            baseAltarPower += Math.min(map.get(visitedBlock), 20) * 4;
        }

        for(Block visitedBlock : visitedFlowerBlocks) {
            baseAltarPower += Math.min(map.get(visitedBlock), 30) * 4;
        }

        for(Block visitedBlock : visitedCropBlocks) {
            baseAltarPower += Math.min(map.get(visitedBlock), 20) * 4;
        }

        for(Block visitedBlock : visitedSaplingBlocks) {
            baseAltarPower += Math.min(map.get(visitedBlock), 20) * 4;
        }


        if(map.containsKey(Blocks.GRASS_BLOCK)) {
            baseAltarPower += Math.min(map.get(Blocks.GRASS_BLOCK), 80) * 2;
        }
        if(map.containsKey(Blocks.MYCELIUM)) {
            baseAltarPower += Math.min(map.get(Blocks.MYCELIUM), 80) * 2;
        }
        if(map.containsKey(Blocks.DIRT)) {
            baseAltarPower += Math.min(map.get(Blocks.DIRT), 80);
        }
        if(map.containsKey(Blocks.FARMLAND)) {
            baseAltarPower += Math.min(map.get(Blocks.FARMLAND), 100);
        }
        if(map.containsKey(Blocks.WATER)) {
            baseAltarPower += Math.min(map.get(Blocks.WATER), 50);
        }
        if(map.containsKey(Blocks.GRASS)) {
            baseAltarPower += Math.min(map.get(Blocks.GRASS), 20);
        }
        if(map.containsKey(Blocks.TALL_GRASS)) {
            baseAltarPower += Math.min(map.get(Blocks.TALL_GRASS), 20) * 3;
        }
        if(map.containsKey(Blocks.DRAGON_EGG)) {
            baseAltarPower += Math.min(map.get(Blocks.DRAGON_EGG), 1) * 250;
        }
        if(map.containsKey(ModBlocks.INFINITY_EGG.get())) {
            baseAltarPower += Math.min(map.get(ModBlocks.INFINITY_EGG.get()), 1) * 1000;
        }
        if(map.containsKey(Blocks.RED_MUSHROOM)) {
            baseAltarPower += Math.min(map.get(Blocks.RED_MUSHROOM), 20) * 3;
        }
        if(map.containsKey(Blocks.BROWN_MUSHROOM)) {
            baseAltarPower += Math.min(map.get(Blocks.BROWN_MUSHROOM), 20) * 3;
        }
        if(map.containsKey(Blocks.RED_MUSHROOM_BLOCK)) {
            baseAltarPower += Math.min(map.get(Blocks.RED_MUSHROOM_BLOCK), 20) * 3;
        }
        if(map.containsKey(Blocks.BROWN_MUSHROOM_BLOCK)) {
            baseAltarPower += Math.min(map.get(Blocks.BROWN_MUSHROOM_BLOCK), 20) * 3;
        }
        if(map.containsKey(Blocks.MUSHROOM_STEM)) {
            baseAltarPower += Math.min(map.get(Blocks.MUSHROOM_STEM), 20) * 3;
        }
        if(map.containsKey(Blocks.VINE)) {
            baseAltarPower += Math.min(map.get(Blocks.VINE), 50) * 2;
        }
        if(map.containsKey(Blocks.SUGAR_CANE)) {
            baseAltarPower += Math.min(map.get(Blocks.SUGAR_CANE), 50) * 3;
        }
        if(map.containsKey(Blocks.CACTUS)) {
            baseAltarPower += Math.min(map.get(Blocks.CACTUS), 50) * 3;
        }
        if(map.containsKey(Blocks.PUMPKIN)) {
            baseAltarPower += Math.min(map.get(Blocks.PUMPKIN), 20) * 4;
        }
        if(map.containsKey(Blocks.MELON)) {
            baseAltarPower += Math.min(map.get(Blocks.MELON), 20) * 4;
        }

    }

    private boolean isCore(BlockPos pPos) {
        return pPos.getX() == core.getX() && pPos.getY() == core.getY() && pPos.getZ() == core.getZ();
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Altar");
    }

    @Nullable
    @Override
    public AbstractContainerMenu createMenu(int pContainerId, Inventory pPlayerInventory, Player pPlayer) {
        return new AltarMenu(pContainerId, pPlayerInventory, this, this.data);
    }
}
