package me.mine215.takhtjang.data;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import me.rayzr522.jsonmessage.JSONMessage;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfo {
    public Player player;
    public Team team;
    public boolean isEliminated = false;
    public boolean ironKit = false;
    public List<Block> blocksPlaced = new ArrayList<>();
    public boolean hasScaffold = false;

    public PlayerInfo(Player playerIn, Team teamIn) {
        player = playerIn;
        team = teamIn;
    }

    public void registerBlock(Block block) {
        blocksPlaced.add(block);
    }

    public boolean unregisterBlock(Block block) {
        try {
            blocksPlaced.remove(block);
            return true;
        } catch (NullPointerException e) {
            return false;
        }
    }

    public void joinGame(Team teamIn) {
        TakhtJang.shouldSpawnRes = true;
        try {
            new GameData().getTeam(team).removePlayer(player);
        } catch (NullPointerException ignored) { }
        team = teamIn;
        new GameData().addPlayer(player, team);
        sendSpawn();
        if (!new GameData().getTeam(team).wasRAnchorGiven) {
            new GameData().getTeam(team).wasRAnchorGiven = true;
        }
        player.getInventory().addItem(new ItemStack(Material.MOSSY_COBBLESTONE));
    }

    public void sendSpawn() {
        player.setGameMode(GameMode.SURVIVAL);
        Location spawnLoc;
        switch (team.toString()) {
            case "BLUE":
                spawnLoc = new Location(player.getWorld(), 0.5, 4.0, 13.5);
                break;
            case "RED":
                spawnLoc = new Location(player.getWorld(), -12.5, 4.0, 0.5);
                break;
            case "GREEN":
                spawnLoc = new Location(player.getWorld(), 0.5, 4.0, -12.5);
                break;
            case "YELLOW":
                spawnLoc = new Location(player.getWorld(), 13.5, 4.0, 0.5);
                break;
            default:
                spawnLoc = new Location(player.getWorld(), 0, 4.0, 0);
                break;
        }
        player.teleport(spawnLoc);
        new PlayerMethods().kit(player, team);
        if (ironKit) {
            new PlayerMethods().ironKit(player);
        }
        player.setHealth(player.getMaxHealth());
    }

    public void kill() {

        if (new GameData().getTeam(team) != null) {
            ChatColor teamColor = ChatColor.GOLD;
            switch (team.toString()) {
                case "BLUE":
                    teamColor = ChatColor.BLUE;
                    break;
                case "RED":
                    teamColor = ChatColor.RED;
                    break;
                case "GREEN":
                    teamColor = ChatColor.GREEN;
                    break;
                case "YELLOW":
                    teamColor = ChatColor.YELLOW;
                    break;
            }
            if (new GameData().getTeam(team).rAnchors >= 1) {
                sendSpawn();
                Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " died.");
            } else {
                for (Player loopPlayer : player.getServer().getOnlinePlayers()) {
                    loopPlayer.playSound(loopPlayer.getLocation(), Sound.AMBIENCE_THUNDER, 10, 10);
                }
                player.setGameMode(GameMode.SPECTATOR);
                new GameData().getPlayer(player).isEliminated = true;
                player.teleport(new Location(player.getWorld(), 0, 15, 0));
                JSONMessage.create(ChatColor.BOLD + "FINAL DEATH!").color(ChatColor.AQUA).title(20, 40, 20, player);
                JSONMessage.create("You were eliminated").color(ChatColor.RED).subtitle(player);
                Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " was eliminated." + ChatColor.AQUA + " " + ChatColor.BOLD + "FINAL KILL!");
                PlayerInfo lastPlayer = new GameData().getLastPlayer();
                if (lastPlayer != null) {
                    switch (lastPlayer.team.toString()) {
                        case "BLUE":
                            teamColor = ChatColor.BLUE;
                            break;
                        case "RED":
                            teamColor = ChatColor.RED;
                            break;
                        case "GREEN":
                            teamColor = ChatColor.GREEN;
                            break;
                        case "YELLOW":
                            teamColor = ChatColor.YELLOW;
                            break;
                    }
                    Bukkit.broadcastMessage(teamColor + lastPlayer.player.getName() + ChatColor.GOLD + " won!");
                    JSONMessage.create("VICTORY").color(ChatColor.GOLD).title(20, 40, 20, lastPlayer.player);
                    for (Player playerLost : player.getServer().getOnlinePlayers()) {
                        if (playerLost != lastPlayer.player) {
                            JSONMessage.create("GAME OVER").color(ChatColor.RED).title(20, 40, 20, playerLost);
                            JSONMessage.create(teamColor + lastPlayer.player.getName() + ChatColor.GOLD + " won").subtitle(playerLost);
                        }
                    }
                    new GameData().resetGame(player);
                }
            }
        } else {
            new PlayerMethods().hub(player);
        }
    }
}
