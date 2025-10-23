package dev.behindthescenery.sdm_loot_stages.data;

import dev.behindthescenery.sdm_loot_stages.LootStagesMain;
import it.unimi.dsi.fastutil.objects.Object2ObjectArrayMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectMap;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.level.storage.loot.LootTable;

import java.util.ArrayList;
import java.util.List;

public class LootStageContainer extends SimplePreparableReloadListener<Void> {

    public static final LootStageContainer Instance = new LootStageContainer();

    protected Object2ObjectMap<ResourceLocation, List<LootTableStage>> StageData = new Object2ObjectArrayMap<>();

    public void register(ResourceLocation location, LootTableStage stage) {
        StageData.computeIfAbsent(location, s -> new ArrayList<>()).add(stage);
    }

    public List<LootTableStage> getStages(LootTable lootTable) {
        return getStages(LootStagesMain.getLootTableId(lootTable));
    }

    public List<LootTableStage> getStages(ResourceLocation location) {
        return StageData.getOrDefault(location, new ArrayList<>());
    }


    @Override
    protected Void prepare(ResourceManager resourceManager, ProfilerFiller profilerFiller) {
        StageData.clear();
        return null;
    }

    @Override
    protected void apply(Void object, ResourceManager resourceManager, ProfilerFiller profilerFiller) {}
}
