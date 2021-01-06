package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.TakhtJang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ToggleGenerator implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        TakhtJang.shouldSpawnRes = !TakhtJang.shouldSpawnRes;

        return true;
    }
}