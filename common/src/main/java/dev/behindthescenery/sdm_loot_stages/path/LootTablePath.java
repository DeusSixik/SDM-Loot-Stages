package dev.behindthescenery.sdm_loot_stages.path;

import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;

public interface LootTablePath {

    ResourceLocation sdm$getRegistryId();

    void sdm$setRegistryId(ResourceLocation location);

    ObjectArrayList<ItemStack> bts$getRandomItemsCustom(LootContext originalContext);
}
