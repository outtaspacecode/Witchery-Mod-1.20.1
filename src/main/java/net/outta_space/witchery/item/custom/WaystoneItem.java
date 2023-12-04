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
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WaystoneItem extends Item {

    public WaystoneItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(pPlayer.isCrouching() && !pPlayer.getItemInHand(pUsedHand).hasTag()) {
            CompoundTag tag = new CompoundTag();
            tag.putIntArray("location", new int[]{(int)pPlayer.getX(), (int)pPlayer.getY(), (int)pPlayer.getZ()});

            pPlayer.getItemInHand(pUsedHand).setTag(tag);

            pLevel.playSeededSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 1f, 5);

        } else if(pPlayer.getItemInHand(pUsedHand).hasTag()) {
            int[] locationTag = pPlayer.getItemInHand(pUsedHand).getTag().getIntArray("location");
            BlockPos pos = new BlockPos(locationTag[0], locationTag[1], locationTag[2]);
            CompoundTag tag = new CompoundTag();
            pPlayer.getItemInHand(pUsedHand).setTag(tag);
            pPlayer.teleportTo(pos.getX(), pos.getY(), pos.getZ());
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(pStack.hasTag()) {
            assert pStack.getTag() != null;
            int[] location = pStack.getTag().getIntArray("location");
            pTooltipComponents.add(Component.literal("Bound to (" + location[0] + ", " + location[1] + ", " + location[2] + ")"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
