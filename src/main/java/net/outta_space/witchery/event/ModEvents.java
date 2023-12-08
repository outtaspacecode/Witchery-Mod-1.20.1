package net.outta_space.witchery.event;

import net.minecraft.client.Minecraft;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
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
import net.minecraftforge.event.entity.living.LivingDeathEvent;
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
    public static void savedByTheBell(LivingDeathEvent event) {
        if(event.getEntity() instanceof Player player) {
            if (InventoryUtil.hasPlayerStackInInventory(player, ModItems.DEATH_PROTECTION_POPPET.get())) {
                ItemStack poppet = player.getInventory().getItem(InventoryUtil.getFirstInventoryIndex(player, ModItems.DEATH_PROTECTION_POPPET.get()));
                if (poppet.hasTag()) {
                    if (Objects.equals(poppet.getTag().getUUID("player_uuid"), player.getUUID())) {
                        event.setCanceled(true);
                        player.setHealth(3);
                        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 360, 1));
                        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 360, 1));
                        if (event.getSource().is(DamageTypes.LAVA) || event.getSource().is(DamageTypes.IN_FIRE) || event.getSource().is(DamageTypes.ON_FIRE)) {
                            player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 360, 1));
                        } else if (event.getSource().is(DamageTypes.DROWN)) {
                            player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 360, 1));
                        }
                        Minecraft.getInstance().level.playLocalSound(player.getX(), player.getY(), player.getZ(), SoundEvents.TOTEM_USE, SoundSource.PLAYERS, 0.5f, 1, false);
                        poppet.shrink(1);
                    }
                }
            }
        }
    }

//    @SubscribeEvent
//    public static void hoverOnAltarBlock(RenderHighlightEvent.Block event) {
//        BlockPos pPos = event.getTarget().getBlockPos();
//        Level pLevel = Minecraft.getInstance().level;
//        assert pLevel != null;
//        if (pLevel.getBlockState(pPos).is(ModBlocks.ALTAR_BLOCK.get())) {
//            AltarBlockEntity abe = (AltarBlockEntity)pLevel.getBlockEntity(pPos);
//            assert abe != null;
//            if(abe.getCore() != null) {
//                AltarBlockEntity core = (AltarBlockEntity)pLevel.getBlockEntity(abe.getCore());
//                assert core != null;
//                System.out.println(core.getCurrentAltarPower() + "/" + core.getBaseAltarPower() + " (" + core.getAbsorptionMultipler() + "x)");
//            }
//        }
//    }


}
