package net.outta_space.witchery.datagen;

import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.EntityTypePredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.KilledTrigger;
import net.minecraft.core.HolderLookup;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.animal.Wolf;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.fml.common.Mod;
import net.outta_space.witchery.WitcheryMod;
import net.outta_space.witchery.item.ModItems;

import java.util.function.Consumer;

public class ModAdvancementProvider implements ForgeAdvancementProvider.AdvancementGenerator {
    @Override
    public void generate(HolderLookup.Provider registries, Consumer<Advancement> saver, ExistingFileHelper existingFileHelper) {


        Advancement rootAdvancement = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.WHIFF_OF_MAGIC.get()),
                        Component.literal("The First Step"), Component.literal("Take your first step towards witchcraft"),
                        new ResourceLocation(WitcheryMod.MOD_ID, "textures/block/stripped_alder_log.png"), FrameType.TASK,
                        true, true, false))
                .addCriterion("has_anointing_paste", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.ANOINTING_PASTE.get()))
                .save(saver, new ResourceLocation(WitcheryMod.MOD_ID, "witchery"), existingFileHelper);

        Advancement creeperHeart = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.CREEPER_HEART.get()),
                        Component.literal("Love Bomb"), Component.literal("Obtain a creeper heart"),
                        null, FrameType.TASK,
                        true, true, false))
                .parent(rootAdvancement)
                .addCriterion("has_creeper_heart", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.CREEPER_HEART.get()))
                .save(saver, new ResourceLocation(WitcheryMod.MOD_ID, "creeper_heart"), existingFileHelper);

        Advancement youMonster = Advancement.Builder.advancement()
                .display(new DisplayInfo(new ItemStack(ModItems.TONGUE_OF_DOG.get()),
                        Component.literal("You Monster"), Component.literal("Cut a dog's tongue out. How can you live with yourself?"),
                        null, FrameType.CHALLENGE,
                        true, true, true))
                .parent(rootAdvancement)
                .addCriterion("has_dog_tongue", InventoryChangeTrigger.TriggerInstance.hasItems(ModItems.TONGUE_OF_DOG.get()))
                .save(saver, new ResourceLocation(WitcheryMod.MOD_ID, "you_monster"), existingFileHelper);


    }
}
