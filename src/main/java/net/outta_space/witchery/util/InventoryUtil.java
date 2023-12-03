package net.outta_space.witchery.util;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingUseTotemEvent;
import net.minecraftforge.eventbus.api.IEventBus;

public class InventoryUtil {

    public static boolean hasPlayerStackInInventory(Player player, Item item) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if(!currentStack.isEmpty() && currentStack.is(item)) {
                return true;
            }
        }

        return false;
    }

    public static int getFirstInventoryIndex(Player player, Item item) {
        for(int i = 0; i < player.getInventory().getContainerSize(); i++) {
            ItemStack currentStack = player.getInventory().getItem(i);
            if(!currentStack.isEmpty() && currentStack.is(item)) {
                return i;
            }
        }

        return -1;
    }

}
