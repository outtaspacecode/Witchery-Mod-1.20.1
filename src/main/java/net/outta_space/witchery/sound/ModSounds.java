package net.outta_space.witchery.sound;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;

import java.util.function.Supplier;

public class ModSounds {

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, WitcheryMod.MOD_ID);


    public static final RegistryObject<SoundEvent> DRAW_WITH_CHALK = registerSoundEvent("draw_with_chalk");
    public static final RegistryObject<SoundEvent> SPLASH = registerSoundEvent("splash");
    public static final RegistryObject<SoundEvent> BLOP = registerSoundEvent("blop");




    private static RegistryObject<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = new ResourceLocation(WitcheryMod.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }



    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }

}
