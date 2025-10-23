package dev.behindthescenery.sdm_loot_stages.data.condition;

import dev.behindthescenery.sdmstages.data.containers.Stage;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

public record DimensionCondition(ResourceLocation dimension_id) implements LootStageCondition {

    @Override
    public boolean success(ResourceLocation tableId, LootTable lootTable, Stage stage, LootContext lootContext, ObjectArrayList<ItemStack> lootItems) {
        return lootContext.getLevel().dimension().location().equals(dimension_id);
    }
}
