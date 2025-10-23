package dev.behindthescenery.sdm_loot_stages.data.functions;

import dev.behindthescenery.sdm_loot_stages.LootStagesMain;
import dev.behindthescenery.sdm_loot_stages.path.LootTablePath;
import dev.behindthescenery.sdmstages.data.containers.Stage;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

public record ReplaceLootTableFunction(ResourceLocation lootTableId) implements LootStageFunction {

    @Override
    public void execute(ResourceLocation tableId, LootTable lootTable, Stage stage, LootContext lootContext, ObjectArrayList<ItemStack> original,
                        ObjectArrayList<ItemStack> copy) {
        final LootTable table = LootStagesMain.getLootTable(lootTableId);
        if(table == LootTable.EMPTY)
            throw new IllegalArgumentException("Can't find LootTable with id: " + lootTableId.toString());

        final LootTablePath path = (LootTablePath)table;
        final ObjectArrayList<ItemStack> newTableItems = path.bts$getRandomItemsCustom(lootContext);

        original.addAll(newTableItems);
    }
}
