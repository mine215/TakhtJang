package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.MDBMethods;
import me.mine215.takhtjang.methods.RankMethods;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.permissions.PermissionAttachment;

public class PlayerJoin implements Listener {

    static TakhtJang main;
    final FileConfiguration config;

    public PlayerJoin(FileConfiguration config, TakhtJang main) {
        PlayerJoin.main = main;
        this.config = config;
    }

    @EventHandler
    public void join(org.bukkit.event.player.PlayerJoinEvent event) {
        Player player = event.getPlayer();

        RankMethods rankMethods = new RankMethods();
        rankMethods.updateVisualRank(player);

        MDBMethods mdbMethods = new MDBMethods();

        event.setJoinMessage(" ");
        if (mdbMethods.getRankName(player).equals("owner") || mdbMethods.getRankName(player).equals("admin") || mdbMethods.getRankName(player).equals("content") || mdbMethods.getRankName(player).equals("legend")) {
            event.setJoinMessage(ChatColor.DARK_AQUA + ">" + ChatColor.AQUA + "> " + rankMethods.getFormattedName(player, mdbMethods.getRankName(player)) + ChatColor.GOLD + " has joined the server " + ChatColor.AQUA + "<" + ChatColor.DARK_AQUA + "<");
        }

        if (mdbMethods.isPlayerManaged(player)) {
            PermissionAttachment attachment = player.addAttachment(main);
            for (String permission : mdbMethods.getManagedPlayerPermission(player)) {
                attachment.setPermission(permission, true);
            }
        }
    }
}