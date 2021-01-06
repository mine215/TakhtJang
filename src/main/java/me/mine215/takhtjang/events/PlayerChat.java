package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.LevelMethods;
import me.mine215.takhtjang.methods.MDBMethods;
import me.mine215.takhtjang.types.Stats;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;

public class PlayerChat implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public PlayerChat(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String level = new LevelMethods().getFormattedLevel(player);
        String format = level + " " + ChatColor.AQUA + event.getPlayer().getDisplayName() + ChatColor.WHITE + ": " + event.getMessage();
        event.setFormat(format);
    }
}
