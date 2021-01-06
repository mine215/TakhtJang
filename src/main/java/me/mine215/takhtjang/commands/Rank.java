package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.data.Party;
import me.mine215.takhtjang.data.PartyData;
import me.mine215.takhtjang.methods.MDBMethods;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.methods.RankMethods;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.util.Locale;

public class Rank implements CommandExecutor {

    static TakhtJang main;
    FileConfiguration config;

    public Rank(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {

        Player player;

        try {
            player = (Player) sender;
        } catch (Exception e) {
            player = null;
        }

        if (args.length >= 2) {
            String argument = args[0].toLowerCase();

            if (argument.equals("set") && args.length >= 3) {
                String playerName = args[1].toLowerCase();
                String rankName = args[2].toLowerCase();
                if (Bukkit.getServer().getPlayer(playerName) != null) {
                    Player playerToGiveRank = Bukkit.getServer().getPlayer(playerName);
                    new RankMethods().setRank(playerToGiveRank, rankName);
                    new RankMethods().updateVisualRank(playerToGiveRank);
                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + playerToGiveRank.getDisplayName() + ChatColor.GREEN + " now is a " + rankName);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + playerToGiveRank.getDisplayName() + ChatColor.GREEN + " now is a " + rankName);
                    }
                } else if (player != null) {
                    player.sendMessage(ChatColor.RED + "That player is not online.");
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "That player is not online.");
                }
            }  else if (argument.equals("remove")) {
                String playerName = args[1].toLowerCase();
                if (Bukkit.getServer().getPlayer(playerName) != null) {
                    Player playerToRemoveRank = Bukkit.getServer().getPlayer(playerName);
                    new RankMethods().setRank(playerToRemoveRank, "non");
                    new RankMethods().updateVisualRank(playerToRemoveRank);
                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + playerToRemoveRank.getDisplayName() + ChatColor.GREEN + " now has no rank.");
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + playerToRemoveRank.getDisplayName() + ChatColor.GREEN + " now has no rank.");
                    }
                } else if (player != null) {
                    player.sendMessage(ChatColor.RED + "That player is not online.");
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "That player is not online.");
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
