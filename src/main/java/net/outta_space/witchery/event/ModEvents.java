package net.outta_space.witchery.event;

import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.item.ModItems;

import java.util.List;

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
