package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.MDBMethods;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

public class Permission implements CommandExecutor {

    static TakhtJang main;
    final FileConfiguration config;

    public Permission(FileConfiguration config, TakhtJang main) {
        Permission.main = main;
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

        if (args.length >= 3) {
            String argument = args[0].toLowerCase();

            MDBMethods mdbMethods = new MDBMethods();

            if (argument.equals("give")) {
                String playerName = args[1].toLowerCase();
                String permissionName = args[2].toLowerCase();
                if (Bukkit.getServer().getPlayer(playerName) != null) {
                    Player playerToGivePermission = Bukkit.getServer().getPlayer(playerName);

                    PermissionAttachment attachment = playerToGivePermission.addAttachment(main);
                    mdbMethods.addManagedPlayer(playerToGivePermission);
                    mdbMethods.addManagedPlayerPermission(playerToGivePermission, permissionName);
                    attachment.setPermission(permissionName, true);

                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + playerToGivePermission.getDisplayName() + ChatColor.GREEN + " now has the permission " + ChatColor.GOLD + permissionName);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + playerToGivePermission.getDisplayName() + ChatColor.GREEN + " now has the permission " + ChatColor.GOLD + permissionName);
                    }
                } else if (player != null) {
                    player.sendMessage(ChatColor.RED + "That player is not online.");
                } else {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.RED + "That player is not online.");
                }
            }  else if (argument.equals("remove")) {
                String playerName = args[1].toLowerCase();
                String permissionName = args[2].toLowerCase();
                if (Bukkit.getServer().getPlayer(playerName) != null) {
                    Player playerToRemovePermission = Bukkit.getServer().getPlayer(playerName);
                    PermissionAttachment attachment = playerToRemovePermission.addAttachment(main);
                    if (mdbMethods.isPlayerManaged(playerToRemovePermission)) {
                        mdbMethods.removeManagedPlayerPermission(playerToRemovePermission, permissionName);
                        attachment.setPermission(permissionName, false);
                    } else {
                        mdbMethods.addManagedPlayer(playerToRemovePermission);
                    }

                    if (player != null) {
                        player.sendMessage(ChatColor.GREEN + playerToRemovePermission.getDisplayName() + ChatColor.GREEN + " no longer has the permission " + ChatColor.RED + permissionName);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.GREEN + playerToRemovePermission.getDisplayName() + ChatColor.GREEN + " no longer has the permission " + ChatColor.RED + permissionName);
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
