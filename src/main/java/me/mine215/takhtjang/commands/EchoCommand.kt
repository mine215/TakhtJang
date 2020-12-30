package me.mine215.takhtjang.commands

import org.bukkit.Bukkit
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player

class EchoCommand : CommandExecutor {
    override fun onCommand(sender: CommandSender?, command: Command?, string: String?, args: Array<out String>?): Boolean {
        if (sender !is Player) {
            return true
        }

        val player = sender as Player

        if (args != null) {
            if (args.size >= 1) {
                var messageToEcho: String = ""
                for (arg in args) {
                    messageToEcho += " $arg";
                }
                Bukkit.getServer().broadcastMessage(messageToEcho);
            }
        }
        return true;
    }
}