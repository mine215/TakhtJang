package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.data.PlayerInfo;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;

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