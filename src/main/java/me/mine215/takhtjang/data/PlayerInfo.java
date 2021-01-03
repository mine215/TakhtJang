package me.mine215.takhtjang.data;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.MDBMethods;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Stats;
import me.mine215.takhtjang.types.Team;
import me.rayzr522.jsonmessage.JSONMessage;
import org.apache.commons.lang.ObjectUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerInfo {
    public Player player;
    public Player lastHit;
    public Team team;
    public boolean isEliminated = false;
    public boolean ironKit = false;
    public List<Block> blocksPlaced = new ArrayList<>();

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
        double x = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".x");
        double y = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".y");
        double z = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".z");
        spawnLoc = new Location(player.getWorld(), x, y, z);
        player.teleport(spawnLoc);
        new PlayerMethods().kit(player, team);
        if (ironKit) {
            new PlayerMethods().ironKit(player);
        }
        TakhtJang.scheduleSyncDelayedTask(() -> {
            player.setHealth(player.getMaxHealth());
        }, 10);
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
            for (ItemStack itemStack : player.getInventory().getContents()) {
                ItemStack[] armor = new ItemStack[4];
                player.getInventory().setArmorContents(armor);
                if (itemStack != null) {
                    if (!itemStack.getType().equals(Material.WOOD_PICKAXE) && !itemStack.getType().equals(Material.IRON_PICKAXE) && !itemStack.getType().equals(Material.GOLD_PICKAXE)) {
                        if (lastHit != null) {
                            lastHit.getInventory().addItem(itemStack);
                        }
                        player.getInventory().removeItem(itemStack);
                    }
                }
            }
            MDBMethods mdbMethods = new MDBMethods();
            if (new GameData().getTeam(team).rAnchors >= 1) {
                sendSpawn();
                if (lastHit != null) {
                    PlayerInfo killer = new GameData().getPlayer(lastHit);
                    if (killer != null) {
                        Team killerTeam = killer.team;
                        ChatColor killerTeamColor = ChatColor.GOLD;
                        switch (killerTeam.toString()) {
                            case "BLUE":
                                killerTeamColor = ChatColor.BLUE;
                                break;
                            case "RED":
                                killerTeamColor = ChatColor.RED;
                                break;
                            case "GREEN":
                                killerTeamColor = ChatColor.GREEN;
                                break;
                            case "YELLOW":
                                killerTeamColor = ChatColor.YELLOW;
                                break;
                        }
                        mdbMethods.updateStatsInt(lastHit, Stats.KILLS, 1);
                        Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " was kill" + ChatColor.YELLOW + " #" +  + new MDBMethods().getStatInt(lastHit, Stats.KILLS) + ChatColor.GRAY + " by " + killerTeamColor + lastHit.getDisplayName() + ChatColor.GRAY + ".");
                    } else {
                        Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " died.");
                    }
                } else {
                    Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " died.");
                }
                mdbMethods.updateStatsInt(player, Stats.DEATHS, 1);
            } else {
                for (Player loopPlayer : player.getServer().getOnlinePlayers()) {
                    loopPlayer.playSound(loopPlayer.getLocation(), Sound.AMBIENCE_THUNDER, 10, 10);
                }
                player.setGameMode(GameMode.SPECTATOR);
                new GameData().getPlayer(player).isEliminated = true;
                player.teleport(new Location(player.getWorld(), 0, 15, 0));
                JSONMessage.create(ChatColor.BOLD + "FINAL DEATH!").color(ChatColor.AQUA).title(20, 40, 20, player);
                JSONMessage.create("You were eliminated").color(ChatColor.RED).subtitle(player);
                if (lastHit != null) {
                    PlayerInfo killer = new GameData().getPlayer(lastHit);
                    if (killer != null) {
                        Team killerTeam = killer.team;
                        ChatColor killerTeamColor = ChatColor.GOLD;
                        switch (killerTeam.toString()) {
                            case "BLUE":
                                killerTeamColor = ChatColor.BLUE;
                                break;
                            case "RED":
                                killerTeamColor = ChatColor.RED;
                                break;
                            case "GREEN":
                                killerTeamColor = ChatColor.GREEN;
                                break;
                            case "YELLOW":
                                killerTeamColor = ChatColor.YELLOW;
                                break;
                        }
                        mdbMethods.updateStatsInt(lastHit, Stats.FINAL_KILLS, 1);
                        Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " was final" + ChatColor.YELLOW + " #" + new MDBMethods().getStatInt(lastHit, Stats.FINAL_KILLS) + ChatColor.GRAY + " by " + killerTeamColor + lastHit.getDisplayName() + ChatColor.GRAY + ". " + ChatColor.AQUA + ChatColor.BOLD + "FINAL KILL!");
                    } else {
                        Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " was eliminated." + ChatColor.AQUA + " " + ChatColor.BOLD + "FINAL KILL!");
                    }
                } else {
                    Bukkit.broadcastMessage(teamColor + player.getName() + ChatColor.GRAY + " was eliminated." + ChatColor.AQUA + " " + ChatColor.BOLD + "FINAL KILL!");
                }
                mdbMethods.updateStatsInt(player, Stats.FINAL_DEATHS, 1);
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
                            mdbMethods.updateStatsInt(player, Stats.LOSSES, 1);
                        } else {
                            mdbMethods.updateStatsInt(lastHit, Stats.WINS, 1);
                        }
                    }
                    new GameData().resetGame(player);
                }
            }
        } else {
            new PlayerMethods().hub(player, TakhtJang.getInstance().getConfig());
        }
    }

    public void hitPlayer(Player damager) {
        lastHit = damager;
        TakhtJang.scheduleSyncDelayedTask(() -> {
            if (lastHit == damager)
                lastHit = null;
        }, 140);
    }
}
