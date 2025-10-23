package dev.behindthescenery.sdm_loot_stages.mixin;

import dev.behindthescenery.sdm_loot_stages.LootStagesMain;
import dev.behindthescenery.sdm_loot_stages.data.LootStageContainer;
import dev.behindthescenery.sdm_loot_stages.data.LootTableStage;
import dev.behindthescenery.sdm_loot_stages.path.LootTablePath;
import dev.behindthescenery.sdm_loot_stages.utils.ChunkHelper;
import dev.behindthescenery.sdmstages.SdmStages;
import dev.behindthescenery.sdmstages.data.containers.Stage;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParams;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.Optional;

@Mixin(LootTable.class)
public abstract class LootTableMixin implements LootTablePath {

    @Shadow
    protected abstract ObjectArrayList<ItemStack> getRandomItems(LootContext arg);

    @Shadow
    @Final
    private Optional<ResourceLocation> randomSequence;
    @Unique
    private final LootTable this$lootable = (LootTable) (Object)this;

    @Unique
    private ResourceLocation sdm$registry_id = LootStagesMain.LOOT_TABLE_EMPTY;

    @Inject(method = "getRandomItems(Lnet/minecraft/world/level/storage/loot/LootContext;)Lit/unimi/dsi/fastutil/objects/ObjectArrayList;",
     at = @At("RETURN"), cancellable = true)
    public void bts$getRandomItems(LootContext lootContext, CallbackInfoReturnable<ObjectArrayList<ItemStack>> cir) {
        final boolean isGlobal = LootStagesMain.isGlobal();
        final ObjectArrayList<ItemStack> list = cir.getReturnValue();

        try {
            if (isGlobal) {
                bts$getRandomItems$modify(lootContext, LootStagesMain.getServerStage().getStage(null), list);
                return;
            }


            Player player = null;
            final DamageSource damageSource = lootContext.getParamOrNull(LootContextParams.DAMAGE_SOURCE);
            if (damageSource != null) {
                if (damageSource.getEntity() instanceof Player pl1)
                    player = pl1;
            } else {
                final Entity thidEntity = lootContext.getParamOrNull(LootContextParams.THIS_ENTITY);
                if (thidEntity != null) {
                    if (thidEntity instanceof Player pl1)
                        player = pl1;
                }
            }

            if (player == null) {
                Vec3 pos = lootContext.getParamOrNull(LootContextParams.ORIGIN);

                if (pos == null) {
                    final BlockEntity blockEntity = lootContext.getParamOrNull(LootContextParams.BLOCK_ENTITY);
                    if (blockEntity != null) {
                        pos = blockEntity.getBlockPos().getCenter();
                    }
                }

                if (pos != null) {
                    player = ChunkHelper.getNearestPlayer(lootContext.getLevel(), pos);
                }

                if (player == null) {
                    SdmStages.LOGGER.error("[LootTableStages] Can't find player! The loot drop has been canceled.!");
                    return;
                }
            }

            bts$getRandomItems$modify(lootContext, LootStagesMain.getServerStage().getStage(player), list);
        } finally {
            cir.setReturnValue(list);
        }
    }

    @Unique
    private void bts$getRandomItems$modify(LootContext lootContext, Stage stage, ObjectArrayList<ItemStack> list) {
        List<LootTableStage> find = LootStageContainer.Instance.getStages(sdm$registry_id);
        if(find == null || find.isEmpty()) return;

        for (LootTableStage lootTableStage : find) {
            if(lootTableStage.modifyLootTable(sdm$registry_id, this$lootable, stage, lootContext, list)) {
                return;
            }
        }
    }

    @Override
    public ResourceLocation sdm$getRegistryId() {
        return sdm$registry_id;
    }

    @Override
    public void sdm$setRegistryId(ResourceLocation location) {
        sdm$registry_id = location;
    }

    @Override
    public ObjectArrayList<ItemStack> bts$getRandomItemsCustom(LootContext originalContext) {
        final LootContext newContext = new LootContext.Builder(((LootContextAccessor)originalContext).getParams()).create(randomSequence);
        return getRandomItems(newContext);
    }
}
