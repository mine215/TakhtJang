package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlaceBlockEvent implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public PlaceBlockEvent(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onPlayerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlockPlaced();

        GameData gameData = new GameData();
        if (!player.getGameMode().equals(GameMode.CREATIVE)) {
            if (gameData.getPlayer(player) != null) {
                if (block.getType() == Material.MOSSY_COBBLESTONE) {
                    gameData.getTeam(gameData.getPlayer(player).team).addRAnchor(block);
                    for (Player loopPlayer : player.getServer().getOnlinePlayers()) {
                        loopPlayer.playSound(loopPlayer.getLocation(), Sound.WITHER_SPAWN, 10, 10);
                    }
                    String teamName = "error";
                    String teamColor = ChatColor.GOLD.toString();
                    switch (gameData.getPlayer(event.getPlayer()).team.toString()) {
                        case "BLUE":
                            teamName = ChatColor.BLUE + "Blue";
                            break;
                        case "RED":
                            teamName = ChatColor.RED + "Red";
                            break;
                        case "GREEN":
                            teamName = ChatColor.GREEN + "Green";
                            break;
                        case "YELLOW":
                            teamName = ChatColor.YELLOW + "Yellow";
                            break;
                    }

                    switch (gameData.getPlayer(event.getPlayer()).team.toString()) {
                        case "BLUE":
                            teamColor = ChatColor.BLUE.toString();
                            break;
                        case "RED":
                            teamColor = ChatColor.RED.toString();
                            break;
                        case "GREEN":
                            teamColor = ChatColor.GREEN.toString();
                            break;
                        case "YELLOW":
                            teamColor = ChatColor.YELLOW.toString();
                            break;
                    }
                    Bukkit.broadcastMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "SPAWN CREATION > " + ChatColor.RESET + teamColor + teamName + " Spawn" + ChatColor.GRAY + " was placed!");
                }
                gameData.getPlayer(player).registerBlock(event.getBlockPlaced());
            } else {
                event.setCancelled(true);
            }
        }
    }
}