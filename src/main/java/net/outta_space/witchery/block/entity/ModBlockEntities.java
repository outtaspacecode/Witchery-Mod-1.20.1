package net.outta_space.witchery.block.entity;

import net.minecraft.world.level.block.entity.BlockEntityType;
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

    public static final RegistryObject<BlockEntityType<WitchCauldronBlockEntity>> WITCH_CAULDRON_BE =
            BLOCK_ENTITIES.register("witch_cauldron_be", () ->
                    BlockEntityType.Builder.of(WitchCauldronBlockEntity::new,
                            ModBlocks.WITCH_CAULDRON.get()).build(null));

    public static final RegistryObject<BlockEntityType<FumeFunnelBlockEntity>> FUME_FUNNEL_BE =
            BLOCK_ENTITIES.register("fume_funnel_be", () ->
                    BlockEntityType.Builder.of(FumeFunnelBlockEntity::new,
                        ModBlocks.FUME_FUNNEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<FilteredFumeFunnelBlockEntity>> FILTERED_FUME_FUNNEL_BE =
            BLOCK_ENTITIES.register("filtered_fume_funnel_be", () ->
                    BlockEntityType.Builder.of(FilteredFumeFunnelBlockEntity::new,
                            ModBlocks.FILTERED_FUME_FUNNEL.get()).build(null));

    public static final RegistryObject<BlockEntityType<AltarBlockEntity>> ALTAR_BE =
            BLOCK_ENTITIES.register("altar_be", () ->
                    BlockEntityType.Builder.of(AltarBlockEntity::new,
                            ModBlocks.ALTAR_BLOCK.get()).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITIES.register(eventBus);
    }
}
