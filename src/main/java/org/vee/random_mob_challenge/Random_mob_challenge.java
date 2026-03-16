package org.vee.random_mob_challenge;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;

public class Random_mob_challenge implements ModInitializer {

    @Override
    public void onInitialize() {

        spawner.register();
        Commands.register();


    }
}
