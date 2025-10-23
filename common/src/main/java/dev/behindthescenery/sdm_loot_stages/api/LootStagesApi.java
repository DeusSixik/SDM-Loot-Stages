package dev.behindthescenery.sdm_loot_stages.api;

import com.blamejared.crafttweaker.api.annotation.ZenRegister;
import dev.behindthescenery.sdm_loot_stages.data.BaseLootTableStage;
import dev.behindthescenery.sdm_loot_stages.data.LootStageContainer;
import dev.behindthescenery.sdm_loot_stages.data.condition.BiomeCondition;
import dev.behindthescenery.sdm_loot_stages.data.condition.DimensionCondition;
import dev.behindthescenery.sdm_loot_stages.data.condition.LootStageCondition;
import dev.behindthescenery.sdm_loot_stages.data.functions.LootStageFunction;
import dev.behindthescenery.sdm_loot_stages.data.functions.ReplaceItemsFunction;
import dev.behindthescenery.sdm_loot_stages.data.functions.ReplaceLootTableFunction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.openzen.zencode.java.ZenCodeType;

import java.util.ArrayList;
import java.util.List;

@ZenRegister
@ZenCodeType.Name("mods.lootstages.LootStages")
public class LootStagesApi {

    @ZenCodeType.Method
    public static Builder builder(ResourceLocation lootTableId, String stage) {
        return new Builder(lootTableId, stage);
    }

    @ZenRegister
    @ZenCodeType.Name("mods.lootstages.LootStageBuilder")
    public static class Builder {
        protected final ResourceLocation loot_table_id;
        protected final String stage;

        protected List<LootStageCondition> conditions = new ArrayList<>();
        protected List<LootStageFunction> functions = new ArrayList<>();

        protected List<ReplaceItemsFunction.Entry> replaceItemsFunction = new ArrayList<>();

        protected Builder(ResourceLocation lootTableId, String stage) {
            this.loot_table_id = lootTableId;
            this.stage = stage;
        }

        @ZenCodeType.Method
        public Builder biome(ResourceLocation biome_id) {
            conditions.add(new BiomeCondition(biome_id));
            return this;
        }

        @ZenCodeType.Method
        public Builder dimension(ResourceLocation dimension_id) {
            conditions.add(new DimensionCondition(dimension_id));
            return this;
        }

        @ZenCodeType.Method
        public Builder replaceItem(ItemStack original, ItemStack replace) {
            replaceItemsFunction.add(new ReplaceItemsFunction.Entry(original, replace));
            return this;
        }

        @ZenCodeType.Method
        public Builder replaceLootTable(ResourceLocation loot_table_id) {
            functions.add(new ReplaceLootTableFunction(loot_table_id));
            return this;
        }

        @ZenCodeType.Method
        public void build() {
            functions.add(new ReplaceItemsFunction(replaceItemsFunction.toArray(new ReplaceItemsFunction.Entry[0])));
            final BaseLootTableStage lootTableStage = new BaseLootTableStage(loot_table_id, stage,
                    conditions.toArray(new LootStageCondition[0]), functions.toArray(new LootStageFunction[0]));
            LootStageContainer.Instance.register(loot_table_id, lootTableStage);
        }
    }
}
