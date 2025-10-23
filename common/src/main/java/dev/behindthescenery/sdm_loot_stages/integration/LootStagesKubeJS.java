package dev.behindthescenery.sdm_loot_stages.integration;

import dev.behindthescenery.sdm_loot_stages.api.LootStagesApi;
import dev.latvian.mods.kubejs.plugin.KubeJSPlugin;
import dev.latvian.mods.kubejs.script.BindingRegistry;
import net.minecraft.resources.ResourceLocation;

public class LootStagesKubeJS implements KubeJSPlugin {

    @Override
    public void registerBindings(BindingRegistry bindings) {
        if(bindings.type().isServer()) {
            bindings.add("LootStages", Methods.class);
        }
    }

    public interface Methods {

        static LootStagesApi.Builder builder(ResourceLocation lootTableId, String stage) {
            return LootStagesApi.builder(lootTableId, stage);
        }
    }
}

