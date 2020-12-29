package me.mine215.takhtjang.data;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.types.Team;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.ArrayList;
import java.util.List;

public class TeamInfo {
    public List<PlayerInfo> players = new ArrayList<>();
    public Team team;
    public int rAnchors = 0;
    public Block rAnchor;
    public boolean wasRAnchorGiven = false;

    public TeamInfo(Team teamIn) {
        team = teamIn;
    }

    public void addRAnchor(Block block) {
        block.setMetadata("team", new FixedMetadataValue(TakhtJang.getInstance(), team));
        rAnchors += 1;
        rAnchor = block;
    }

    public void removeRAnchor() {
        rAnchors -= 1;
        rAnchor = null;
    }

    public void removePlayer(Player player) {
        players.removeIf(playerFromList -> playerFromList.player == player);
    }

    public void addPlayer(PlayerInfo playerInfo) {
        players.add(playerInfo);
    }
}
