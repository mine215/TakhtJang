package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.PlayerMethods;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Shop implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;
        double shopAX = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopA.x");
        double shopAY = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopA.y");
        double shopAZ = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopA.z");

        double shopBX = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopB.x");
        double shopBY = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopB.y");
        double shopBZ = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopB.z");

        double shopCX = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopC.x");
        double shopCY = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopC.y");
        double shopCZ = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopC.z");

        double shopDX = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopD.x");
        double shopDY = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopD.y");
        double shopDZ = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.shopD.z");

        // if (isNearShop(player, 0.5, 4, -12.5) || isNearShop(player, 0.5, 4, 13.5) || isNearShop(player, -12.5, 4, 0.5) || isNearShop(player, 13.5, 4, 0.5)) {
        if (isNearShop(player, shopAX, shopAY, shopAZ) || isNearShop(player, shopBX, shopBY, shopBZ) || isNearShop(player, shopCX, shopCY, shopCZ) || isNearShop(player, shopDX, shopDY, shopDZ)) {
            new PlayerMethods().shop(player);
        } else {
            double validShopRadius = TakhtJang.instance.getConfig().getDouble("worldData.shops.validRadius");
            player.sendMessage(ChatColor.RED + "Please get within " + Double.toString(validShopRadius) + " blocks of a generator.");
        }

        return true;
    }

    public boolean isNearShop(Player player, double x, double y, double z) {
        double validShopRadius = TakhtJang.getInstance().getConfig().getDouble("worldData.shops.validRadius");
        // 8
        return new PlayerMethods().distanceFromCoord(new Location(player.getWorld(), x, y, z), player.getLocation()) <= validShopRadius;
    }
}