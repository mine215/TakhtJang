package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PickupItemEvent implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public PickupItemEvent(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onPlayerPickupItem(PlayerPickupItemEvent event) {
    }
}
