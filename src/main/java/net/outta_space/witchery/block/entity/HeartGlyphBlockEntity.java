package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.dimension.BuiltinDimensionTypes;
import net.minecraft.world.phys.AABB;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.recipe.RiteRecipe;
import net.outta_space.witchery.rite.BindCircleTalismanRite;
import net.outta_space.witchery.rite.BindWaystoneRite;
import net.outta_space.witchery.rite.ChargeAttunedStoneRite;
import net.outta_space.witchery.rite.UseWaystoneRite;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

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

    private String failMessage = "";

    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if (pState.getValue(IS_ACTIVE)) {
            if(pLevel.dimensionTypeId() == BuiltinDimensionTypes.OVERWORLD) {

                checkForCircles(pLevel, pPos);
                int circleSize;
                if (largeCircle > 0) {
                    circleSize = 7;
                } else if (mediumCircle > 0) {
                    circleSize = 5;
                } else {
                    circleSize = 3;
                }

                AABB aabb = new AABB(pPos).move(0.5, 0, 0.5).inflate(circleSize, 0, circleSize);
                List<ItemEntity> itemEntities = pLevel.getEntitiesOfClass(ItemEntity.class, aabb);

                if (hasRecipe(itemEntities, pLevel, pPos)) {
                    aabb = getAABBSize(itemEntities, pPos);
                    itemEntities = pLevel.getEntitiesOfClass(ItemEntity.class, aabb);

                    if (!hasRecipe(itemEntities, pLevel, pPos)) {
                        pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, false));
                        return;
                    }

                    if (cooldown <= 0) {

                        if (!itemEntities.isEmpty()) {
                            itemList.add(itemEntities.get(0).getItem());
                            itemEntities.get(0).kill();
                            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                                    SoundEvents.ITEM_PICKUP, SoundSource.BLOCKS, 1, 0, 6);
                        } else {
                            pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, false));
                            performRitual(pLevel, pPos, pState);
                        }

                        resetCooldown();
                    } else {
                        cooldown--;
                    }
                } else if (itemEntities.isEmpty() && itemList.isEmpty()) {
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, false));
                } else {
                    Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
                    if (player != null) {
                        player.sendSystemMessage(Component.literal("§c" + failMessage));
                    }
                    level.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.NOTE_BLOCK_SNARE, SoundSource.BLOCKS, 0.5f, 0, 0);
                    pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, false));
                    returnItems(pLevel, pPos);
                    resetCooldown();
                }
            } else {
                Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
                if (player != null) {
                    player.sendSystemMessage(Component.literal("§cRites can only be performed in the overworld."));
                }
                level.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(), SoundEvents.NOTE_BLOCK_SNARE, SoundSource.BLOCKS, 0.5f, 0, 0);
                pLevel.setBlockAndUpdate(pPos, pState.setValue(IS_ACTIVE, false));
                returnItems(pLevel, pPos);
                resetCooldown();
            }
        }

    }

    private AABB getAABBSize(List<ItemEntity> itemEntities, BlockPos pPos) {
        int circleSize = 3;
        Optional<RiteRecipe> recipe = getCurrentRecipe(itemEntities);
        int[] circles = recipe.get().getCircles();

        if(circles[2] > 0) {
            circleSize = 7;
        } else if(circles[1] > 0) {
            circleSize = 5;
        }

        return new AABB(pPos).move(0.5, 0, 0.5).inflate(circleSize, 0, circleSize);
    }


    private void performRitual(Level pLevel, BlockPos pPos, BlockState pState) {
        Optional<RiteRecipe> recipe = getCurrentRecipe(List.of());
        ItemStack resultItem = recipe.get().getResultItem(pLevel.registryAccess());

        if(resultItem.is(ModItems.WAYSTONE.get())) {
            for(ItemStack item : itemList) {
                if(item.is(ModItems.WAYSTONE.get()) && item.hasTag()) {
                    Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
                    assert player != null;
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                            SoundEvents.NOTE_BLOCK_SNARE, SoundSource.BLOCKS, 1f, 0, 1);
                    player.sendSystemMessage(Component.literal("§cWaystone cannot already be bound"));
                    returnItems(pLevel, pPos);
                    return;
                }
            }

            BindWaystoneRite.perform(pLevel, pPos);
        }

        if(resultItem.is(Items.ENDER_PEARL)) {
            AABB aabb = new AABB(pPos).move(0.5, 0, 0.5).inflate(3, 0, 3);
            List<LivingEntity> livingEntities = pLevel.getEntitiesOfClass(LivingEntity.class, aabb);

            UseWaystoneRite.perform(pLevel, pPos, itemList, livingEntities);
        }

        if(resultItem.is(ModItems.CHARGED_ATTUNED_STONE.get())) {
            ChargeAttunedStoneRite.perform(pLevel, pPos);
        }

        if(resultItem.is(ModItems.CIRCLE_TALISMAN.get())) {
            for(ItemStack items : itemList) {
                if(items.is(ModItems.CIRCLE_TALISMAN.get()) && items.hasTag()) {
                    Player player = pLevel.getNearestPlayer(pPos.getX(), pPos.getY(), pPos.getZ(), 20, false);
                    assert player != null;
                    pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                            SoundEvents.NOTE_BLOCK_SNARE, SoundSource.BLOCKS, 1f, 0, 1);
                    player.sendSystemMessage(Component.literal("§cCircle talisman cannot already be bound"));
                    returnItems(pLevel, pPos);
                    return;
                }
            }
            BindCircleTalismanRite.perform(pLevel, pPos, new int[] {smallCircle, mediumCircle, largeCircle});
        }

        boolean hasAttunedStone = false;
        for(ItemStack is : itemList) {
            if(is.is(ModItems.CHARGED_ATTUNED_STONE.get())) {
                hasAttunedStone = true;
            }
        }

        if(recipe.get().willAllowAttunedStone() && hasAttunedStone) {
            pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, new ItemStack(ModItems.ATTUNED_STONE.get(), 1)));
        } else if(recipe.get().getAltarPower() > 0) {
            AltarBlockEntity abe = (AltarBlockEntity)pLevel.getBlockEntity(getAltarCorePos(pLevel, pPos));
            assert abe != null;
            abe.suckPower(recipe.get().getAltarPower());
        }

        itemList.clear();
    }

    private void returnItems(Level pLevel, BlockPos pPos) {
        if(!itemList.isEmpty()) {
            for (ItemStack item : itemList) {
                pLevel.addFreshEntity(new ItemEntity(pLevel, pPos.getX() + 0.5, pPos.getY() + 0.5, pPos.getZ() + 0.5, item));
            }
            itemList.clear();
        }
    }

    private boolean hasRecipe(List<ItemEntity> itemEntities, Level pLevel, BlockPos pPos) {
        Optional<RiteRecipe> recipe = getCurrentRecipe(itemEntities);

        if(recipe.isEmpty()) {
            failMessage = "Unknown rite.";
            return false;
        }

        boolean hasAttunedStone = false;
        for(ItemEntity ie : itemEntities) {
            if(ie.getItem().is(ModItems.CHARGED_ATTUNED_STONE.get())) {
                hasAttunedStone = true;
            }
        }

        for(ItemStack is : itemList) {
            if(is.is(ModItems.CHARGED_ATTUNED_STONE.get())) {
                hasAttunedStone = true;
            }
        }

        if(!recipe.get().matchesCircles(smallCircle, mediumCircle, largeCircle)) {
            failMessage = "Incorrect ritual circles.";
        } else if(recipe.get().willAllowAttunedStone() && !hasAttunedStone && !recipe.get().hasAltarPower(getAltarPower(pLevel, getAltarCorePos(pLevel, pPos)))) {
            failMessage = "Rite requires altar or charged attuned stone.";
        } else if(!recipe.get().willAllowAttunedStone() && !recipe.get().hasAltarPower(getAltarPower(pLevel, getAltarCorePos(pLevel, pPos)))) {
            failMessage = "Rite requires altar.";
        } else if(smallCircle == 0 && mediumCircle == 0 && largeCircle == 0) {
            failMessage = "Circles required";
            return false;
        } else {
            failMessage = "Unknown rite.";
        }


        return recipe.get().matchesCircles(smallCircle, mediumCircle, largeCircle)
                && (recipe.get().hasAltarPower(getAltarPower(pLevel, getAltarCorePos(pLevel, pPos)))
                    || (recipe.get().willAllowAttunedStone() && hasAttunedStone));

    }

    private BlockPos getAltarCorePos(Level pLevel, BlockPos pPos) {
        boolean hasAltar = false;
        BlockPos corePos = null;
        for(int x = -14; x < 14; x++) {
            for(int z = -14; z < 14; z++) {
                for(int y = -3; y < 3; y++) {
                    BlockPos checkPos = new BlockPos(pPos.getX() + x, pPos.getY() + y, pPos.getZ() + z);
                    if(pLevel.getBlockState(checkPos).is(ModBlocks.ALTAR_BLOCK.get())) {
                        AltarBlockEntity abe = (AltarBlockEntity)pLevel.getBlockEntity(checkPos);
                        if(abe.getCore() != null) {
                            hasAltar = true;
                            corePos = abe.getCore();
                            break;
                        }
                        if(hasAltar) { break; }
                    }
                    if(hasAltar) { break; }
                }
            }
        }

        return corePos;
    }

    private int getAltarPower(Level pLevel, BlockPos corePos) {

        if(corePos == null) {
            return 0;
        }

        AltarBlockEntity abe = (AltarBlockEntity)pLevel.getBlockEntity(corePos);
        assert abe != null;
        return abe.getCurrentAltarPower();
    }

    private Optional<RiteRecipe> getCurrentRecipe(List<ItemEntity> itemEntities) {
        SimpleContainer inventory = new SimpleContainer(itemEntities.size() + itemList.size());

        for(int i = 0; i < itemEntities.size(); i++) {
            inventory.setItem(i, itemEntities.get(i).getItem());
        }

        for(int i = 0; i < itemList.size(); i++) {
            inventory.setItem(i + itemEntities.size(), itemList.get(i));
        }

        return this.level.getRecipeManager().getRecipeFor(RiteRecipe.Type.INSTANCE, inventory, level);
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
