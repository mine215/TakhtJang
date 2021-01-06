package me.mine215.takhtjang.methods;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class RankMethods {
    public String getFormattedName(Player player, String rank) {
        String name = player.getName();

        if (rank.equalsIgnoreCase("owner")) {
            name = ChatColor.RED + "[OWNER] " + player.getName();
        } else if (rank.equalsIgnoreCase("admin")) {
            name = ChatColor.RED + "[ADMIN] " + player.getName();
        } else if (rank.equalsIgnoreCase("content")) {
            name = ChatColor.AQUA + "[CONTENT] " + player.getName();
        } else if (rank.equalsIgnoreCase("legend")) {
            name = ChatColor.GOLD + "[LEGEND] " + player.getName();
        }

        return name;
    }

    public void setRank(Player player, String rankName) {
        MDBMethods mdbMethods = new MDBMethods();
        switch (rankName.toLowerCase()) {
            case "owner":
                mdbMethods.updateRank(player, "owner");
                break;
            case "admin":
                mdbMethods.updateRank(player, "admin");
                break;
            case "content":
                mdbMethods.updateRank(player, "content");
                break;
            case "legend":
                mdbMethods.updateRank(player, "legend");
                break;
            default:
                mdbMethods.updateRank(player, "non");
                break;
        }
    }

    public void updateVisualRank(Player player) {
        String name = new RankMethods().getFormattedName(player, new MDBMethods().getRankName(player));
        player.setDisplayName(name);
        player.setPlayerListName(name);
    }
}
