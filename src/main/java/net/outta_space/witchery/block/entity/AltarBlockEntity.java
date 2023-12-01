package net.outta_space.witchery.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.AABB;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.custom.AltarBlock;

import java.util.ArrayList;
import java.util.List;

import static net.outta_space.witchery.block.custom.AltarBlock.IS_MULTIBLOCK;

public class AltarBlockEntity extends BlockEntity {

    private BlockPos core = null;
    private List<BlockState> surroundingBlocks = new ArrayList<>();

    public AltarBlockEntity(BlockPos pPos, BlockState pBlockState) {
        super(ModBlockEntities.ALTAR_BE.get(), pPos, pBlockState);
    }

    public void setCore(BlockPos pPos) {
        core = pPos;
    }

    private boolean display = true;
    public void tick(Level pLevel, BlockPos pPos, BlockState pState) {

        if(core != null) {
            if(display) {
                if(isCore(pPos)) {
//                    System.out.println("We are the core");

                    AABB aabb = new AABB(pPos).move(0.5, 0, 0.5).inflate(14.0D);
                    surroundingBlocks = pLevel.getBlockStates(aabb).toList();

                } else {
//                    System.out.println("We are not the core");
                }
//                System.out.println("pPos: " + pPos.toString());

//                System.out.println("Core pos: " + core.toString());
                display = false;
            }
        } else {
            display = true;
            surroundingBlocks = null;
        }
    }

    private boolean isCore(BlockPos pPos) {
        return pPos.getX() == core.getX() && pPos.getY() == core.getY() && pPos.getZ() == core.getZ();
    }

}
