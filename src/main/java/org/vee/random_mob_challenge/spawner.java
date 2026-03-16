package org.vee.random_mob_challenge;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;

import java.util.Optional;

public class spawner {
    public static int spawnInterval = 200;
    private static int tickCounter = 0;

    private static boolean RMCEnabled = false;

    public static boolean isRMCEnabled() {
        return RMCEnabled;
    }

    public static void setRMCEnabled(boolean enabled) {
        RMCEnabled = enabled;
    }

    public static void register() {
        ServerTickEvents.END_SERVER_TICK.register(server -> {
            tickCounter++;

            if (tickCounter >= spawnInterval) {
                if (RMCEnabled) {
                    for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                        ServerWorld world = player.getEntityWorld().toServerWorld();
                        BlockPos pos = player.getBlockPos();

                        Registries.ENTITY_TYPE.getRandom(world.getRandom()).ifPresent(entry -> {
                            EntityType<?> entityType = entry.value();
                            entityType.spawn(world, pos, SpawnReason.EVENT);
                        });
                    }
                    tickCounter = 0;
                } else {tickCounter = 0;}
            }
        });
    }
}