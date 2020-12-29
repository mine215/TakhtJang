package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.methods.PlayerMethods;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ScaffoldCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        new GameData().getPlayer(((Player) sender).getPlayer()).hasScaffold = !(new GameData().getPlayer(((Player) sender).getPlayer()).hasScaffold);

        return true;
    }
}