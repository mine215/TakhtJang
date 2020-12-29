package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.methods.PlayerMethods;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ShopCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        if (isNearShop(player, 0.5, 4, -12.5) || isNearShop(player, 0.5, 4, 13.5) || isNearShop(player, -12.5, 4, 0.5) || isNearShop(player, 13.5, 4, 0.5)) {
            new PlayerMethods().shop(player);
        } else {
            player.sendMessage(ChatColor.RED + "Please get within 8 blocks of a generator.");
        }

        return true;
    }

    public boolean isNearShop(Player player, double x, double y, double z) {
        return new PlayerMethods().distanceFromCoord(new Location(player.getWorld(), x, y, z), player.getLocation()) <= 8.0;
    }
}