package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.PlayerMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Hub implements CommandExecutor {

    static TakhtJang main;
    final FileConfiguration config;

    public Hub(FileConfiguration config, TakhtJang main) {
        Hub.main = main;
        this.config = config;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        new PlayerMethods().hub(player, config);

        return true;
    }
}