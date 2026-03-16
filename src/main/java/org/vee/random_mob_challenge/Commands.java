package org.vee.random_mob_challenge;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.server.command.CommandManager;
import net.minecraft.text.Text;

public class Commands {

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(CommandManager.literal("rmc")
                    .then(CommandManager.literal("interval")
                            .then(CommandManager.argument("ticks", IntegerArgumentType.integer(1)).executes(context -> {
                                        int newInterval = IntegerArgumentType.getInteger(context, "ticks");

                                        spawner.spawnInterval = newInterval;

                                        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();

                                        if (client != null) {
                                            net.minecraft.client.toast.SystemToast.add(
                                                    client.getToastManager(),
                                                    net.minecraft.client.toast.SystemToast.Type.NARRATOR_TOGGLE,
                                                    net.minecraft.text.Text.literal("§4§lRMC"),
                                                    net.minecraft.text.Text.literal("Interval: §6" + newInterval + " §aTicks")
                                            );
                                        }

                                        return 1;
                                    })
                            )
                    )
                    .then(CommandManager.literal("enabled-disabled").executes(context -> {
                        boolean current = spawner.isRMCEnabled();
                        spawner.setRMCEnabled(!current);

                        net.minecraft.client.MinecraftClient client = net.minecraft.client.MinecraftClient.getInstance();

                        if (client != null) {
                            net.minecraft.client.toast.SystemToast.add(
                                    client.getToastManager(),
                                    net.minecraft.client.toast.SystemToast.Type.NARRATOR_TOGGLE,
                                    net.minecraft.text.Text.literal("§4§lRMC"),
                                    net.minecraft.text.Text.literal("RMC: §6" + (!current ? "§aenabled" : "§cdisabled"))
                            );
                        }

                        return 1;
                    }))
            );
        });
    }
}
