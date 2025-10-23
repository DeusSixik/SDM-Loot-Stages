package dev.behindthescenery.sdm_loot_stages.mixin;

import dev.behindthescenery.sdm_loot_stages.data.LootStageContainer;
import net.minecraft.server.ReloadableServerResources;
import net.minecraft.server.packs.resources.PreparableReloadListener;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(ReloadableServerResources.class)
public class ReloadableServerResourcesMixin {

    @Inject(method = "listeners", at = @At("RETURN"), cancellable = true)
    public void sdm$listeners(CallbackInfoReturnable<List<PreparableReloadListener>> cir) {
        final List<PreparableReloadListener> list = new ArrayList<>(cir.getReturnValue());
        list.add(LootStageContainer.Instance);
        cir.setReturnValue(list);
    }
}
