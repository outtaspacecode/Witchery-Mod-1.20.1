package net.outta_space.witchery.item.custom;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import net.outta_space.witchery.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class TagLockKitItem extends Item {

    public TagLockKitItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level pLevel, Player pPlayer, InteractionHand pUsedHand) {

        if(pPlayer.isCrouching() && !pPlayer.getItemInHand(pUsedHand).hasTag()) {
            CompoundTag tag = new CompoundTag();
            tag.putString("bound_player", pPlayer.getName().getString());
            tag.putUUID("player_uuid", pPlayer.getUUID());

            ItemStack taglockKit = new ItemStack(ModItems.TAGLOCK_KIT.get(), 1);
            taglockKit.setTag(tag);
            DamageSources source = new DamageSources(pLevel.registryAccess());
            pPlayer.hurt(source.playerAttack(pPlayer), 1);
            pLevel.playSeededSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(), SoundEvents.EXPERIENCE_ORB_PICKUP, SoundSource.BLOCKS, 0.5f, 1f, 5);
            pPlayer.getItemInHand(pUsedHand).shrink(1);
            pPlayer.addItem(taglockKit);
        }

        return super.use(pLevel, pPlayer, pUsedHand);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(pStack.hasTag()) {
            pTooltipComponents.add(Component.literal("Bound to " + pStack.getTag().getString("bound_player")));
        } else {
            pTooltipComponents.add(Component.literal("No player currently bound"));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
