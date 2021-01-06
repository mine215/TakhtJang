package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class PlayerSleep implements Listener {

    static TakhtJang main;
    final FileConfiguration config;

    public PlayerSleep(FileConfiguration config, TakhtJang main) {
        PlayerSleep.main = main;
        this.config = config;
    }

    @EventHandler
    public void onPlayerSleep(PlayerBedEnterEvent event) {
        Player player = event.getPlayer();
        if (new GameData().getPlayer(player) != null) {
            event.setCancelled(true);
        }
    }
}