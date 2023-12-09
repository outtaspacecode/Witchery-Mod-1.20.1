package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Objects;

public class DeathProtectionPoppetItem extends Item {
    public DeathProtectionPoppetItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void appendHoverText(ItemStack pStack, @Nullable Level pLevel, List<Component> pTooltipComponents, TooltipFlag pIsAdvanced) {

        if(pStack.hasTag()) {
            String online = "";
            assert pLevel != null;
            for(Player player : pLevel.players()) {
                assert pStack.getTag() != null;
                if(Objects.equals(pStack.getTag().getUUID("player_uuid"), player.getUUID())) {
                    online = "§3";
                }
            }
            pTooltipComponents.add(Component.literal("§7Bound to " + online + pStack.getTag().getString("bound_player")));
        } else {
            pTooltipComponents.add(Component.literal("§7Poppet must be bound to player to have an effect."));
        }

        super.appendHoverText(pStack, pLevel, pTooltipComponents, pIsAdvanced);
    }
}
