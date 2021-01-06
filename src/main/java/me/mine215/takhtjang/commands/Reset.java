package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.data.GameData;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Reset implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        new GameData().resetGame(((Player) sender).getPlayer());

        return true;
    }
}