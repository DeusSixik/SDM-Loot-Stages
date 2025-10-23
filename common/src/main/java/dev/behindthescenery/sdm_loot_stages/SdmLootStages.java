package dev.behindthescenery.sdm_loot_stages;

import dev.architectury.event.events.common.CommandRegistrationEvent;
import dev.architectury.event.events.common.LifecycleEvent;

public final class SdmLootStages {
    public static final String MOD_ID = "sdm_lootstages";

    public static void init() {


        CommandRegistrationEvent.EVENT.register(LootStagesCommands::registerCommands);
        LifecycleEvent.SERVER_STARTED.register(LootStagesMain::onServerStarted);
    }
}
