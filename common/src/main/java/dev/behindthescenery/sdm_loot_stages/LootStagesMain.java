package dev.behindthescenery.sdm_loot_stages;

import dev.behindthescenery.sdm_loot_stages.path.LootTablePath;
import dev.behindthescenery.sdm_loot_stages.utils.ChunkHelper;
import dev.behindthescenery.sdmstages.SdmStages;
import dev.behindthescenery.sdmstages.StageApi;
import dev.behindthescenery.sdmstages.data.StageContainer;
import dev.behindthescenery.sdmstages.data.StageContainerType;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ReloadableServerRegistries;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.storage.loot.LootTable;

public class LootStagesMain {

    public static final ResourceLocation LOOT_TABLE_EMPTY = ResourceLocation.tryBuild("sdm", "empty");

    private static StageContainer ServerStageContainer;
    private static boolean isGlobal = false;
    private static ReloadableServerRegistries.Holder reloadableRegister;
    private static MinecraftServer server;

    public static void onServerStarted(MinecraftServer _server) {
        server = _server;
        ServerStageContainer = StageApi.getServerStage();
        isGlobal = ServerStageContainer.getContainerType() == StageContainerType.GLOBAL;
        reloadableRegister = _server.reloadableRegistries();
        applyTableId(_server);
    }

    public static boolean isGlobal() {
        return isGlobal;
    }

    public static StageContainer getServerStage() {
        return ServerStageContainer;
    }

    public static MinecraftServer getServer() {
        return server;
    }

    public static ReloadableServerRegistries.Holder getReloadableRegister() {
        return reloadableRegister;
    }

    public static ResourceLocation getLootTableId(LootTable table) {
        return ((LootTablePath)table).sdm$getRegistryId();
    }

    public static LootTable getLootTable(ResourceLocation lootTableId) {
        return reloadableRegister.getLootTable(ResourceKey.create(Registries.LOOT_TABLE, lootTableId));
    }

    public static void applyTableId(MinecraftServer _server) {
        final MinecraftServer server = _server == null ? LootStagesMain.server : _server;
        if(server == null) return;

        reloadableRegister = server.reloadableRegistries();

        for (ResourceLocation key : server.reloadableRegistries().getKeys(Registries.LOOT_TABLE)) {
            final ResourceKey<LootTable> resourceKey = ResourceKey.create(Registries.LOOT_TABLE, key);
            if(reloadableRegister.getLootTable(resourceKey) instanceof LootTablePath path) {
                path.sdm$setRegistryId(key);
            }
        }

        SdmStages.LOGGER.info("[LootTableStages]: Applied ResourceLocation for all tables!");
    }
}
