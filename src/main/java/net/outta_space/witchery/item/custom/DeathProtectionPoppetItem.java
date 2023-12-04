package net.outta_space.witchery.item.custom;

import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

public class DeathProtectionPoppetItem extends Item {
    public DeathProtectionPoppetItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        Level pLevel = pContext.getLevel();
        BlockPos pPos = pContext.getClickedPos();
        Player pPlayer = pContext.getPlayer();

        if(!pLevel.isClientSide()) {
            pLevel.playSeededSound(null, pPlayer.getX(), pPlayer.getY(), pPlayer.getZ(),SoundEvents.TOTEM_USE, SoundSource.BLOCKS, 0.5f, 1.0f, 2);
            return InteractionResult.SUCCESS;
        }
        for(int i = 0; i < 100; i++) {
            assert pPlayer != null;
            pLevel.addParticle(ParticleTypes.TOTEM_OF_UNDYING, pPlayer.getX(), pPlayer.getY() + 1, pPlayer.getZ(), 0, 0, 0);
        }

        return InteractionResult.CONSUME;
    }
}
