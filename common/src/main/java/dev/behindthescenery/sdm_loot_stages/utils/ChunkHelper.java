package dev.behindthescenery.sdm_loot_stages.utils;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.phys.Vec3;

import java.util.List;

public class ChunkHelper {


    public static Player getNearestPlayer(LevelAccessor level, Vec3 pos) {
        final List<? extends Player> players = level.players();
        if(players.size() > 1) {
            Player nearestPlayer = null;
            double minDistance = Double.MAX_VALUE;
            for (Player player : players) {
                final double distance = player.distanceToSqr(pos);
                if (distance < minDistance) {
                    minDistance = distance;
                    nearestPlayer = player;
                }
            }
            return nearestPlayer;
        } else return players.getFirst();
    }
}
