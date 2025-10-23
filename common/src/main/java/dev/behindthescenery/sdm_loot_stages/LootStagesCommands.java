package dev.behindthescenery.sdm_loot_stages;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;

public class LootStagesCommands {

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher, CommandBuildContext context, Commands.CommandSelection selection) {
        registerCommands(dispatcher);
    }

    public static void registerCommands(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
                Commands.literal("sdm_loot_stages")
                        .requires(source -> source.hasPermission(2))

                        .then(Commands.literal("reload_tables")
                                .executes(LootStagesCommands::reloadStages))
        );
    }

    private static int reloadStages(CommandContext<CommandSourceStack> context) {
        LootStagesMain.applyTableId(context.getSource().getServer());
        context.getSource().sendSuccess(() -> Component.literal("All loot tables reloaded!"), false);
        return 1;
    }
}
