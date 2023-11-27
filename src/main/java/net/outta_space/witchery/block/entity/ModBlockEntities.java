package net.outta_space.witchery.block.entity;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.entity.DispenserBlockEntity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.block.ModBlocks;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, WitcheryMod.MOD_ID);

    public static final RegistryObject<BlockEntityType<WitchOvenBlockEntity>> WITCH_OVEN_BE =
            BLOCK_ENTITIES.register("witch_oven_be", () ->
                    BlockEntityType.Builder.of(WitchOvenBlockEntity::new,
                            ModBlocks.WITCH_OVEN.get()).build(null));

    public static final RegistryObject<BlockEntityType<DistilleryBlockEntity>> DISTILLERY_BE =
            BLOCK_ENTITIES.register("distillery_be", () ->
                    BlockEntityType.Builder.of(DistilleryBlockEntity::new,
                            ModBlocks.DISTILLERY.get()).build(null));

    public static final RegistryObject<BlockEntityType<HeartGlyphBlockEntity>> HEART_GLYPH_BE =
            BLOCK_ENTITIES.register("heart_glyph_be", () ->
                    BlockEntityType.Builder.of(HeartGlyphBlockEntity::new,
                            ModBlocks.HEART_GLYPH.get()).build(null));



    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
