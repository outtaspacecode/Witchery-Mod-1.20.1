package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.HeartGlyphBlock;
import org.checkerframework.checker.units.qual.C;

import java.util.ArrayList;
import java.util.List;

import static net.outta_space.witchery.block.custom.HeartGlyphBlock.IS_ACTIVE;

public class HeartGlyphBlockEntity extends BlockEntity {
    public HeartGlyphBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HEART_GLYPH_BE.get(), pPos, pBlockState);
    }

    private int smallCircle;
    private int mediumCircle;
    private int largeCircle;

    private int cooldown = 20;
    private List<ItemStack> itemList = new ArrayList<ItemStack>();

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if(pState.getValue(IS_ACTIVE)) {
            if (cooldown <= 0) {

                AABB aabb = new AABB(pPos).move(0.5, 0, 0.5).inflate(3, 0, 3);
                List<ItemEntity> itemEntities = pLevel.getEntitiesOfClass(ItemEntity.class, aabb);
                if(!itemEntities.isEmpty()) {
                    itemList.add(itemEntities.get(0).getItem());
                    itemEntities.get(0).kill();
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                            SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1, 0, 6);
                } else {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, false));
                }

                resetCooldown();
            } else {
                cooldown--;
            }
        } else if(!itemList.isEmpty()) {
            for(ItemStack item : itemList) {
                pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, item));
            }

            checkForCircles(pLevel, pPos);

            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
            Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
            if(player != null) {
                player.sendSystemMessage(Component.literal("§cUnknown rite."));

                if(smallCircle == 1) {
                    player.sendSystemMessage(Component.literal("We have a small white circle"));
                } else if(smallCircle == 2) {
                    player.sendSystemMessage(Component.literal("We have a small red circle"));
                } else if(smallCircle == 3) {
                    player.sendSystemMessage(Component.literal("We have a small purple circle"));
                } else {
                    player.sendSystemMessage(Component.literal("No small circle to be found"));
                }

                if(mediumCircle == 1) {
                    player.sendSystemMessage(Component.literal("We have a medium white circle"));
                } else if(mediumCircle == 2) {
                    player.sendSystemMessage(Component.literal("We have a medium red circle"));
                } else if(mediumCircle == 3) {
                    player.sendSystemMessage(Component.literal("We have a medium purple circle"));
                } else {
                    player.sendSystemMessage(Component.literal("No medium circle to be found"));
                }

                if(largeCircle == 1) {
                    player.sendSystemMessage(Component.literal("We have a large white circle"));
                } else if(largeCircle == 2) {
                    player.sendSystemMessage(Component.literal("We have a large red circle"));
                } else if(largeCircle == 3) {
                    player.sendSystemMessage(Component.literal("We have a large purple circle"));
                } else {
                    player.sendSystemMessage(Component.literal("No large circle to be found"));
                }
            }
            itemList.clear();
        }

    }

    private void checkForCircles(Level pLevel, BlockPos pPos) {

        boolean whiteCircle = true;
        boolean redCircle = true;
        boolean purpleCircle = true;

        int[] smallCircleX = {3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -2, -1, 0, 1, 2};
        int[] smallCircleZ = {-1, 0, 1, 2, 3, 3, 3, 2, 1, 0, -1, -2, -3, -3, -3, -2};

        for(int i = 0; i < 16; i++) {
            BlockPos checkPos = new BlockPos(pPos.getX() + smallCircleX[i], pPos.getY(), pPos.getZ() + smallCircleZ[i]);
            if(!pLevel.getBlockState(checkPos).is(ModBlocks.WHITE_CIRCLE_GLYPH.get())) {
                whiteCircle = false;
            }

            if(!pLevel.getBlockState(checkPos).is(ModBlocks.INFERNAL_CIRCLE_GLYPH.get())) {
                redCircle = false;
            }

            if(!pLevel.getBlockState(checkPos).is(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get())) {
                purpleCircle = false;
            }
        }

        if(whiteCircle) {
            smallCircle = 1;
        } else if(redCircle) {
            smallCircle = 2;
        } else if(purpleCircle) {
            smallCircle = 3;
        } else {
            smallCircle = 0;
        }

        whiteCircle = true;
        redCircle = true;
        purpleCircle = true;

        int[] mediumCircleX = {5, 5, 5, 5, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -5, -5, -5, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4};
        int[] mediumCircleZ = {-2, -1, 0, 1, 2, 3, 4, 5, 5, 5, 5, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -5, -5, -5, -5, -4, -3};

        for(int i = 0; i < 28; i++) {
            BlockPos checkPos = new BlockPos(pPos.getX() + mediumCircleX[i], pPos.getY(), pPos.getZ() + mediumCircleZ[i]);
            if(!pLevel.getBlockState(checkPos).is(ModBlocks.WHITE_CIRCLE_GLYPH.get())) {
                whiteCircle = false;
            }

            if(!pLevel.getBlockState(checkPos).is(ModBlocks.INFERNAL_CIRCLE_GLYPH.get())) {
                redCircle = false;
            }

            if(!pLevel.getBlockState(checkPos).is(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get())) {
                purpleCircle = false;
            }
        }

        if(whiteCircle) {
            mediumCircle = 1;
        } else if(redCircle) {
            mediumCircle = 2;
        } else if(purpleCircle) {
            mediumCircle = 3;
        } else {
            mediumCircle = 0;
        }

        whiteCircle = true;
        redCircle = true;
        purpleCircle = true;

        int[] largeCircleX = {7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -7, -7, -7, -7, -7, -7, -6, -5, -4, -3, -2, -1, 0, 1, 2, 3, 4, 5, 6};
        int[] largeCircleZ = {-3, -2, -1, 0, 1, 2, 3, 4, 5, 6, 7, 7, 7, 7, 7, 7, 7, 6, 5, 4, 3, 2, 1, 0, -1, -2, -3, -4, -5, -6, -7, -7, -7, -7, -7, -7, -7, -6, -5, -4};

        for(int i = 0; i < 40; i++) {
            BlockPos checkPos = new BlockPos(pPos.getX() + largeCircleX[i], pPos.getY(), pPos.getZ() + largeCircleZ[i]);
            if(!pLevel.getBlockState(checkPos).is(ModBlocks.WHITE_CIRCLE_GLYPH.get())) {
                whiteCircle = false;
            }

            if(!pLevel.getBlockState(checkPos).is(ModBlocks.INFERNAL_CIRCLE_GLYPH.get())) {
                redCircle = false;
            }

            if(!pLevel.getBlockState(checkPos).is(ModBlocks.OTHERWHERE_CIRCLE_GLYPH.get())) {
                purpleCircle = false;
            }
        }

        if(whiteCircle) {
            largeCircle = 1;
        } else if(redCircle) {
            largeCircle = 2;
        } else if(purpleCircle) {
            largeCircle = 3;
        } else {
            largeCircle = 0;
        }
    }

    private void resetCooldown() {
        cooldown = 20;
    }
}
