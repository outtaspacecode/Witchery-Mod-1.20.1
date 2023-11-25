package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BedItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BedBlock;
import net.minecraft.world.level.block.entity.BedBlockEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TagLockKitItem extends Item {

    private Player boundPlayer = null;
    private String playerName = null;


    public TagLockKitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {

        Level pLevel = pContext.getLevel();
        Player pPlayer = pContext.getPlayer();
        BlockPos pPos = pContext.getClickedPos();

        if(!pLevel.isClientSide()) {
            if (pLevel.getBlockState(pPos).getBlock() instanceof BedBlock) {
                for (Player p : pLevel.players()) {
                    if (p.getSleepingPos().isPresent() && p.getSleepingPos().get() == pPos) {
                        boundPlayer = p;
                        playerName = p.getName().getString();
                    }

                }
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.CONSUME;



//        return super.useOn(pContext);
    }


    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(playerName != null) {
            pTooltipComponents.add(Component.literal("Bound to " + playerName));
        } else {
            pTooltipComponents.add(Component.literal("No player currently bound"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
