package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.outta_space.witchery.block.custom.HeartGlyphBlock;

import java.util.ArrayList;
import java.util.List;

import static net.outta_space.witchery.block.custom.HeartGlyphBlock.IS_ACTIVE;

public class HeartGlyphBlockEntity extends BlockEntity {
    public HeartGlyphBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.HEART_GLYPH_BE.get(), pPos, pBlockState);
    }

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
            pLevel.playSeededSound(null, pPos.getX(), pPos.getY(), pPos.getZ(),
                    SoundEvents.LAVA_EXTINGUISH , SoundSource.BLOCKS, 1f, 1, 1);
            itemList.clear();
        }

    }

    private void resetCooldown() {
        cooldown = 20;
    }
}
