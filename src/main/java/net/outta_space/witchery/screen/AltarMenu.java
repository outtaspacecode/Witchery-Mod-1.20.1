package net.outta_space.witchery.screen;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.outta_space.witchery.block.ModBlocks;
import net.outta_space.witchery.block.entity.AltarBlockEntity;

public class AltarMenu extends AbstractContainerMenu {
    public final AltarBlockEntity blockEntity;
    private final Level level;
    private final ContainerData data;

    public AltarMenu(int pContainerId, Inventory inv, FriendlyByteBuf extraData) {
        this(pContainerId, inv, inv.player.level().getBlockEntity(extraData.readBlockPos()), new SimpleContainerData(3));
    }

    public AltarMenu(int pContainerId, Inventory inv, BlockEntity entity, ContainerData data) {
            super(ModMenuTypes.ALTAR_MENU.get(), pContainerId);
            checkContainerSize(inv, 5);
            blockEntity = ((AltarBlockEntity) entity);
            this.level = inv.player.level();
            this.data = data;

            addDataSlots(data);
    }

    @Override
    public ItemStack quickMoveStack(Player pPlayer, int pIndex) {
        return null;
    }

    @Override
    public boolean stillValid(Player pPlayer) {
        return stillValid(ContainerLevelAccess.create(level, blockEntity.getBlockPos()),
                pPlayer, ModBlocks.ALTAR_BLOCK.get());
    }

    public int getCurrentPower() {
        return data.get(0);
    }

    public int getBasePower() {
        return data.get(1);
    }

    public int getMultiplier() {
        return data.get(2);
    }
}
