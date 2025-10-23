package dev.behindthescenery.sdm_loot_stages.mixin;

import dev.behindthescenery.sdm_loot_stages.LootStagesMain;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {

    @Inject(method = "reloadResources", at = @At("RETURN"))
    public void bts$reloadResources(Collection<String> collection, CallbackInfoReturnable<CompletableFuture<Void>> cir) {
        final MinecraftServer server = (MinecraftServer)(Object)this;
        cir.getReturnValue().thenAccept(s -> LootStagesMain.applyTableId(server));
    }
}
