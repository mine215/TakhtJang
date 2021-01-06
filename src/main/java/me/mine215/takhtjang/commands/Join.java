package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.data.PlayerInfo;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Join implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            String kitName = args[0].toLowerCase();

            GameData gameData = new GameData();
            gameData.initTeams();

            if (!gameData.getPlayer(player).isEliminated) {

                switch (kitName) {
                    case "blue":
                        gameData.getPlayer(player).joinGame(Team.BLUE);
                        break;
                    case "green":
                        gameData.getPlayer(player).joinGame(Team.GREEN);
                        break;
                    case "red":
                        gameData.getPlayer(player).joinGame(Team.RED);
                        break;
                    case "yellow":
                        gameData.getPlayer(player).joinGame(Team.YELLOW);
                        break;
                }
            } else {
                player.sendMessage(ChatColor.RED + "You have been eliminated, please join after this game is over.");
            }
            return true;
        } else {
            return false;
        }
    }
}
