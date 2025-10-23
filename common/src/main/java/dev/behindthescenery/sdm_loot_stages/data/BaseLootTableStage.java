package dev.behindthescenery.sdm_loot_stages.data;

import dev.behindthescenery.sdm_loot_stages.data.condition.LootStageCondition;
import dev.behindthescenery.sdm_loot_stages.data.functions.LootStageFunction;
import dev.behindthescenery.sdmstages.data.containers.Stage;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

public class BaseLootTableStage implements LootTableStage {

    protected final ResourceLocation loot_table_id;
    protected final String stage;
    protected final LootStageCondition[] conditions;
    protected final LootStageFunction[] functions;

    public BaseLootTableStage(ResourceLocation lootTableId, String stage, LootStageCondition[] conditions, LootStageFunction[] functions) {
        this.loot_table_id = lootTableId;
        this.stage = stage;
        this.conditions = conditions;
        this.functions = functions;
    }

    @Override
    public boolean modifyLootTable(ResourceLocation tableId, LootTable lootTable, Stage stage, LootContext lootContext, ObjectArrayList<ItemStack> lootItems) {
        if(stage.contains(this.stage)) return false;

        for (LootStageCondition condition : conditions) {
            if(!condition.success(tableId, lootTable, stage, lootContext, lootItems)) {
                lootItems.clear();
                return true;
            }
        }

        final ObjectArrayList<ItemStack> copy = lootItems.clone();
        lootItems.clear();
        for (LootStageFunction function : functions) {
            function.execute(tableId, lootTable, stage, lootContext, lootItems, copy);
        }

        return true;
    }
}
