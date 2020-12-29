package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        GameData gameData = new GameData();
        gameData.initTeams();
        Team openTeam = gameData.getOpenTeam();

        try {
            gameData.getPlayer(player).joinGame(openTeam);
        } catch (NullPointerException e) {
            gameData.addPlayer(player, openTeam);
            gameData.getPlayer(player).joinGame(openTeam);
        }

        return true;
    }
}
