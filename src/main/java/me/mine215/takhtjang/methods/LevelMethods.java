package me.mine215.takhtjang.methods;

import me.mine215.takhtjang.types.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

public class LevelMethods {
    public String getFormattedLevel(Player player) {
        int level = (int) new MDBMethods().getStatDouble(player, Stats.LEVELS);
        String levelString = "" + level;

        if (level >= 0 && level < 100) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "α" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 100 && level < 200) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "β" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 200 && level < 300) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "γ" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 300 && level < 400) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "δ" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 400 && level < 500) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "ε" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 500 && level < 600) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "ζ" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 600 && level < 700) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "η" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 800 && level < 900) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "θ" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 900 && level < 1000) {
            levelString = ChatColor.DARK_PURPLE + "" + ChatColor.BOLD + "λ" + ChatColor.RESET + ChatColor.DARK_PURPLE + level;
        } else if (level >= 1000 && level < 1100) {
            levelString = ChatColor.DARK_RED + "" + ChatColor.BOLD + "μ" + ChatColor.RESET + ChatColor.DARK_RED + level;
        } else if (level >= 1100 && level < 1200) {
            levelString = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Σ" + ChatColor.RESET + ChatColor.DARK_RED + level;
        } else if (level >= 1200 && level < 1300) {
            levelString = ChatColor.DARK_RED + "" + ChatColor.BOLD + "φ" + ChatColor.RESET + ChatColor.DARK_RED + level;
        } else if (level >= 1300 && level < 1400) {
            levelString = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ψ" + ChatColor.RESET + ChatColor.DARK_RED + level;
        } else if (level >= 1400 && level < 1500) {
            levelString = ChatColor.DARK_RED + "" + ChatColor.BOLD + "Ω" + ChatColor.RESET + ChatColor.DARK_RED + level;
        }

        return ChatColor.BLACK + "[" + levelString + ChatColor.BLACK + "]";
    }
}
