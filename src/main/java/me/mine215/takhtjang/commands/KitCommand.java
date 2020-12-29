package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class KitCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (args.length >= 1) {
            String kitName = args[0].toLowerCase();

            PlayerMethods playerMethods = new PlayerMethods();

            switch (kitName) {
                case "blue":
                    playerMethods.kit(player, Team.BLUE);
                    break;
                case "green":
                    playerMethods.kit(player, Team.GREEN);
                    break;
                case "red":
                    playerMethods.kit(player, Team.RED);
                    break;
                case "yellow":
                    playerMethods.kit(player, Team.YELLOW);
                    break;
                default:
                    playerMethods.kit(player, null);
                    break;
            }
            return true;
        } else {
            return false;
        }
    }
}
