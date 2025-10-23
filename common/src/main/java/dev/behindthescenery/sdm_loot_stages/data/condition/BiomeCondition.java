package dev.behindthescenery.sdm_loot_stages.data.condition;

import dev.behindthescenery.sdmstages.data.containers.Stage;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;

public record BiomeCondition(ResourceLocation biome_id) implements LootStageCondition{

    @Override
    public boolean success(ResourceLocation tableId, LootTable lootTable, Stage stage, LootContext lootContext, ObjectArrayList<ItemStack> lootItems) {
        final Vec3 pos = lootContext.getParamOrNull(LootContextParams.ORIGIN);
        if(pos != null) {
            return lootContext.getLevel().getBiome(new BlockPos((int) pos.x, (int) pos.y, (int) pos.z)).is(biome_id);
        }

        return true;
    }
}
