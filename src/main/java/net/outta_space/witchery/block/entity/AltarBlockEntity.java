package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.AltarBlock;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static net.outta_space.witchery.block.custom.AltarBlock.INDEX;
import static net.outta_space.witchery.block.custom.AltarBlock.IS_MULTIBLOCK;

public class AltarBlockEntity extends BlockEntity {

    private BlockPos core = null;
    private final List<Block> augmentItems = new ArrayList<>(6);
    private List<BlockState> surroundingBlocks = new ArrayList<>();
    private int cooldown;

    private int currentAltarPower;
    private int baseAltarPower;

    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR_BE.get(), pPos, pBlockState);
        cooldown = 100;
        for(int i = 0; i < 6; i++) {
            this.augmentItems.add(null);
        }
        resetAltarPower();
        resetBaseAltarPower();
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
        this.cooldown = 100;
    }

    public void setCore(BlockPos pPos) {
        core = pPos;
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
    }


    @Override
    public void load(CompoundTag pTag) {
        super.load(pTag);
        if(pTag.getBoolean("valid")) {
            core = new BlockPos(pTag.getIntArray("core")[0], pTag.getIntArray("core")[1], pTag.getIntArray("core")[2]);
        }
    }

    private boolean display = true;
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if(core != null) {
            AltarBlockEntity be = (AltarBlockEntity) pLevel.getBlockEntity(core);
            be.setAugmentItems(pState.getValue(INDEX), pLevel.getBlockState(pPos.above()).getBlock());

            if(isCore(pPos)) {

                if(cooldown == 100) {
                    AABB aabb = new AABB(pPos).move(0.5, 0, 0.5).inflate(14.0D);
                    surroundingBlocks = pLevel.getBlockStates(aabb).toList();

                    setAltarPower();

//                    System.out.println(augmentItems.toString());
                }

                if(cooldown > 0) {
                    cooldown--;
                } else {
                    resetCooldown();
                }

            } else {
//                    System.out.println("We are not the core");
            }

//            }
        } else {
            surroundingBlocks = null;
            resetAugmentItems();
            resetCooldown();
            resetAltarPower();
        }
    }

    private void resetAltarPower() {
        currentAltarPower = 0;
    }

    private void setAltarPower() {
        List<Block> acceptedBlockTypes = List.of(Blocks.GRASS_BLOCK, Blocks.DIRT, Blocks.MYCELIUM, Blocks.FARMLAND, Blocks.GRASS, Blocks.TALL_GRASS, Blocks.DRAGON_EGG, ModBlocks.INFINITY_EGG.get());

        List<Block> visitedLogBlocks = new ArrayList<>();
        List<Block> visitedLeafBlocks = new ArrayList<>();
        List<Block> visitedFlowerBlocks = new ArrayList<>();
        List<Block> visitedCropBlocks = new ArrayList<>();
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

        System.out.println(map.toString());

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

        System.out.println("Base power: " + baseAltarPower);
    }

    private boolean isCore(BlockPos pPos) {
        return pPos.getX() == core.getX() && pPos.getY() == core.getY() && pPos.getZ() == core.getZ();
    }

}
