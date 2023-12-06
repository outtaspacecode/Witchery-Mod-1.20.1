package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.rite.BindWaystoneRite;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WaystoneItem extends Item {

    public WaystoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(pStack.hasTag()) {
            assert pStack.getTag() != null;
            int[] location = pStack.getTag().getIntArray("location");
            pTooltipComponents.add(Component.literal("§7Bound to (" + location[0] + ", " + location[1] + ", " + location[2] + ")"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
