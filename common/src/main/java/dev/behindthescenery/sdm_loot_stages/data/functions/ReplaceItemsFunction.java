package dev.behindthescenery.sdm_loot_stages.data.functions;

import dev.behindthescenery.sdmstages.data.containers.Stage;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;

public record ReplaceItemsFunction(Entry[] entries) implements LootStageFunction {

    @Override
    public void execute(ResourceLocation tableId, LootTable lootTable, Stage stage, LootContext lootContext, ObjectArrayList<ItemStack> lootItems,
                        ObjectArrayList<ItemStack> copy) {
        if(copy.isEmpty()) return;

        bo:
        for (final ItemStack item : copy) {
            for (final Entry entry : entries) {
                if (ItemStack.isSameItem(item, entry.current)) {
                    final ItemStack find = entry.to;
                    find.setCount(Math.min(find.getMaxStackSize(), item.getCount()));
                    lootItems.add(find);
                    continue bo;
                }
            }

            lootItems.add(item);
        }
    }

    public record Entry(ItemStack current, ItemStack to) {}
}
