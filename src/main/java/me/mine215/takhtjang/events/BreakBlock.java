package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.data.PlayerInfo;
import me.mine215.takhtjang.methods.MDBMethods;
import me.mine215.takhtjang.types.Stats;
import me.mine215.takhtjang.types.Team;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import java.util.List;

public class BreakBlock implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public BreakBlock(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onPlayerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Block block = event.getBlock();

        GameData gameData = new GameData();
        if (gameData.isPlayerBlock(block) || event.getPlayer().getGameMode().equals(GameMode.CREATIVE)) {
            if (block.getType() == Material.MOSSY_COBBLESTONE) {
                for (Player loopPlayer : player.getServer().getOnlinePlayers()) {
                    loopPlayer.playSound(loopPlayer.getLocation(), Sound.ENDERDRAGON_GROWL, 10, 10);
                }
                String teamName = "error";
                String teamColor = ChatColor.GOLD.toString();
                Team team = (Team) block.getMetadata("team").get(0).value();
                gameData.getTeam(team).removeRAnchor();
                switch (team.toString()) {
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

                switch (team.toString()) {
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
                Bukkit.broadcastMessage(ChatColor.WHITE.toString() + ChatColor.BOLD + "SPAWN DESTRUCTION > " + ChatColor.RESET + teamColor + teamName + " Spawn" + ChatColor.GRAY + " was destroyed!");

                MDBMethods mdbMethods = new MDBMethods();
                mdbMethods.updateStatsInt(player, Stats.SPAWN_BREAKS, 1);
            }
            gameData.getPlayer(player).unregisterBlock(event.getBlock());
        } else {
            event.getPlayer().sendMessage(ChatColor.RED + "You can only break blocks placed by a player.");
            event.setCancelled(true);
        }
    }
}