package net.outta_space.witchery;

import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.GrassColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.outta_space.witchery.block.ModBlocks;


// Color handler magic, thanks to BOP (Biomes O' Plenty)
@Mod.EventBusSubscriber(modid = WitcheryMod.MOD_ID, value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ColorHandler {

    @SubscribeEvent
    public static void registerItemColors(RegisterColorHandlersEvent.Item event) {
        event.getItemColors().register((pStack, pTintIndex) ->  {
          BlockState state = ((BlockItem)pStack.getItem()).getBlock().defaultBlockState();
          return event.getBlockColors().getColor(state, null, null, pTintIndex); },
                ModBlocks.WITCH_CAULDRON.get());
    }

    @SubscribeEvent
    public static void registerBlockColors(RegisterColorHandlersEvent.Block event) {
        event.getBlockColors().register((state, world, pos, tintIndex) ->
                world != null && pos != null ? BiomeColors.getAverageWaterColor(world, pos) : GrassColor.getDefaultColor(),
                ModBlocks.WITCH_CAULDRON.get());
    }

}
