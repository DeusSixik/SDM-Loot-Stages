package dev.behindthescenery.sdm_loot_stages.neoforge;

import dev.behindthescenery.sdm_loot_stages.SdmLootStages;
import net.minecraft.world.level.storage.loot.LootTable;
import net.neoforged.fml.common.Mod;

@Mod(SdmLootStages.MOD_ID)
public final class SdmLootStagesNeoForge {
    public SdmLootStagesNeoForge() {

        SdmLootStages.init();
    }
}
