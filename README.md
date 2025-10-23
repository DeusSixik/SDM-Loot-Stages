# SDM Loot Stages

SDM Loot Stages allows you to create a step-by-step progression for loot. 
Allows you to limit loot tables, replace them with others, or replace items in the table.

## How to use ?

The mod adds support for KubeJS and CraftTweaker

### KubeJS
```js
// Server_Script

LootStages.builder("minecraft:blocks/dirt", "one")
    .replaceLootTable("minecraft:chests/village/village_fisher").build();
    
LootStages.builder("minecraft:chests/village/village_fisher", "two")
    .replaceItem('minecraft:wheat_seeds', 'minecraft:diamond').build();
```

### CraftTweaker
`import mods.lootstages.LootStages;`

```ts
import mods.lootstages.LootStages;

LootStages.builder(<resource:minecraft:blocks/dirt>, "one")
    .replaceLootTable(<resource:minecraft:chests/village/village_fisher>).build();

LootStages.builder(<resource:minecraft:chests/village/village_fisher>, "two")
    .replaceItem(<item:minecraft:wheat_seeds>, <item:minecraft:diamond>).build();
```

| Method           | Type                                         | Return           | Description                                                                |
|------------------|----------------------------------------------|------------------|----------------------------------------------------------------------------|
| builder          | tableId as ResourceLocation, stage as String | LootStageBuilder | Create builder                                                             |
| replaceLootTable | tableId as ResourceLocation                  | LootStageBuilder | The original tablet will be replaced with a new one.                       |
| replaceItem      | original as ItemStack, newItem as ItemStack  | LootStageBuilder | The item from the table will be replaced with a new item.                  |
| biome            | biome as ResourceLocation                    | LootStageBuilder | Additional restriction condition. The prey must be in a specific biome     |
| dimension        | dimension as ResourceLocation                | LootStageBuilder | Additional restriction condition. The prey must be in a specific dimension |
| build            |                                              | void             | Completes the constructor chain                                            |
