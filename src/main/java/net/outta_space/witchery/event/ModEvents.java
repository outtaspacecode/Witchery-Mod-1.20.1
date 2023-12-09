package net.outta_space.witchery.event;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.client.Minecraft;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.DamageSources;
import net.minecraft.world.damagesource.DamageTypes;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.npc.InventoryCarrier;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingDamageEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.item.ModItems;
import net.outta_space.witchery.util.InventoryUtil;

import java.util.List;
import java.util.Objects;

@Mod.EventBusSubscriber(modid = WitcheryMod.MOD_ID)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
//        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemStack(Items.EMERALD, 35),
                new ItemStack(ModItems.CREEPER_HEART.get()), 1, 15, 0.08f
        ));

    }

    @SubscribeEvent
    public static void savedByTheBell(LivingDamageEvent event) {
        if(event.getEntity() instanceof Player player) {
            if (InventoryUtil.hasPlayerStackInInventory(player, ModItems.DEATH_PROTECTION_POPPET.get())) {
                ItemStack poppet = player.getInventory().getItem(InventoryUtil.getFirstInventoryIndex(player, ModItems.DEATH_PROTECTION_POPPET.get()));
                if (poppet.hasTag()) {
                    if (Objects.equals(poppet.getTag().getUUID("player_uuid"), player.getUUID())) {
                        if (player.getHealth() - event.getAmount() <= 0) {
                            event.setCanceled(true);
                            ServerPlayer serverplayer = (ServerPlayer) player;
                            serverplayer.awardStat(Stats.ITEM_USED.get(ModItems.DEATH_PROTECTION_POPPET.get()), 1);
                            CriteriaTriggers.USED_TOTEM.trigger(serverplayer, ModItems.DEATH_PROTECTION_POPPET.get().getDefaultInstance());

                            player.setHealth(3.0F);
                            player.removeAllEffects();
                            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 360, 1));
                            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 360, 1));
                            if(event.getSource().is(DamageTypes.LAVA) || event.getSource().is(DamageTypes.IN_FIRE) || event.getSource().is(DamageTypes.ON_FIRE)) {
                                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 360, 0));
                            } else if (event.getSource().is(DamageTypes.DROWN)) {
                                player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 360, 1));
                            }
                            player.level().broadcastEntityEvent(player, (byte) 35);
                            poppet.shrink(1);
                        }
                    }
                }
            }
        }
    }
}
