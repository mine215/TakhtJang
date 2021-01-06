package me.mine215.takhtjang.commands;

import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.data.Party;
import me.mine215.takhtjang.data.PartyData;
import me.mine215.takhtjang.data.PlayerInfo;
import me.mine215.takhtjang.types.Team;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Play implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }

        Player player = (Player) sender;

        GameData gameData = new GameData();
        gameData.initTeams();
        Team openTeam = gameData.getOpenTeam();

        Party party = new PartyData().getPartyFromMember(player);
        if (party == null) {
            PlayerInfo info = gameData.getPlayer(player);
            if (info != null) {
                info.joinGame(openTeam);
            } else {
                gameData.addPlayer(player, openTeam);
                gameData.getPlayer(player).joinGame(openTeam);
            }
        } else {
            if (party.owner.equals(player)) {
                for (Player playerInParty : party.getMembers()) {
                    List<Team> openTeams = gameData.getOpenTeams();
                    if (openTeams != null) {
                        PlayerInfo info = gameData.getPlayer(playerInParty);
                        if (info != null) {
                            info.joinGame(openTeam);
                        } else {
                            gameData.addPlayer(playerInParty, openTeams.get(0));
                            gameData.getPlayer(playerInParty).joinGame(openTeams.get(0));
                        }
                    }
                }
            } else {
                player.sendMessage(ChatColor.RED + "Only the party leader can warp into games.");
            }
        }

        try {
            gameData.getPlayer(player).joinGame(openTeam);
        } catch (NullPointerException e) {
            gameData.addPlayer(player, openTeam);
            gameData.getPlayer(player).joinGame(openTeam);
        }

        return true;
    }
}
