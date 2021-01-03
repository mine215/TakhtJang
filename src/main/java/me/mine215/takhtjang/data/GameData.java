package me.mine215.takhtjang.data;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.types.Team;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class GameData {
    public static List<TeamInfo> teams = new ArrayList<>();

    public void initTeams() {
        teams.add(new TeamInfo(Team.RED));
        teams.add(new TeamInfo(Team.BLUE));
        teams.add(new TeamInfo(Team.GREEN));
        teams.add(new TeamInfo(Team.YELLOW));
    }

    public void addPlayer(Player player, Team team) {
        TeamInfo teamInfo = getTeam(team);
        if (teamInfo != null)
            teamInfo.addPlayer(new PlayerInfo(player, team));
    }

    public PlayerInfo getPlayer(Player player) {
        for (TeamInfo info : teams) {
            for (PlayerInfo playerInfo : info.players) {
                if (playerInfo.player == player) {
                    return playerInfo;
                }
            }
        }
        return null;
    }

    public TeamInfo getTeam(Team team) {
        for (TeamInfo info : teams) {
            if (info.team == team) {
                return info;
            }
        }
        return null;
    }

    public boolean isPlayerBlock(Block block) {
        for (TeamInfo teamInfo : teams) {
            for (PlayerInfo playerInfo : teamInfo.players) {
                for (Block blockInfo : playerInfo.blocksPlaced) {
                    if (blockInfo.equals(block)) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public List<Block> getPlayerBlocks() {
        List<Block> playerBlocks = new ArrayList<>();
        for (TeamInfo teamInfo : teams) {
            for (PlayerInfo playerInfo : teamInfo.players) {
                for (Block blockInfo : playerInfo.blocksPlaced) {
                    playerBlocks.add(blockInfo);
                }
            }
        }
        return playerBlocks;
    }

    public Team getOpenTeam() {
        for (TeamInfo teamInfo : teams) {
            if (teamInfo.players.size() == 0) {
                return teamInfo.team;
            }
        }
        for (TeamInfo teamInfo : teams) {
            if (teamInfo.players.size() <= 3) {
                return teamInfo.team;
            }
        }
        return Team.BLUE;
    }

    public List<Team> getOpenTeams() {
        List<Team> openTeams = new ArrayList<>();
        for (TeamInfo teamInfo : teams) {
            if (teamInfo.players.size() == 0) {
                openTeams.add(teamInfo.team);
            }
        }
        return openTeams;
    }

    public void resetGame(Player player) {
        for (TeamInfo teamInfo : teams) {
            for (PlayerInfo playerInfo : teamInfo.players) {
                for (Block block : playerInfo.blocksPlaced) {
                    block.setType(Material.AIR);
                }
                playerInfo.team = null;
                playerInfo.kill();
            }
        }
        teams = new ArrayList<>();
        for (Entity entity : player.getWorld().getEntities()) {
            if (entity instanceof Item) {
                entity.remove();
            }
        }
        TakhtJang.shouldSpawnRes = false;
    }

    public PlayerInfo getLastPlayer() {
        List<PlayerInfo> notEliminatedPlayersList = new ArrayList<>();
        for (TeamInfo teamInfo : teams) {
            for (PlayerInfo playerInfo : teamInfo.players) {
                if (!playerInfo.isEliminated) {
                    notEliminatedPlayersList.add(playerInfo);
                }
            }
        }
        if (notEliminatedPlayersList.size() == 1) {
            return notEliminatedPlayersList.get(0);
        } else {
            return null;
        }
    }
}
