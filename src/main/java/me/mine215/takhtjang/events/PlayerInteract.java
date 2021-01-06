package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.methods.LevelMethods;
import me.mine215.takhtjang.methods.MDBMethods;
import me.mine215.takhtjang.types.Stats;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerInteract implements Listener {

    static TakhtJang main;
    final FileConfiguration config;

    public static List<Player> playersOnPortals = new ArrayList<>();

    public PlayerInteract(FileConfiguration config, TakhtJang main) {
        me.mine215.takhtjang.events.PlayerMove.main = main;
        this.config = config;
    }

    @EventHandler()
    public void openStatsMenu(org.bukkit.event.player.PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getItemInHand() != null) {
            if (player.getItemInHand().getItemMeta() != null) {
                if (player.getItemInHand().getItemMeta().getDisplayName() != null) {
                    if (player.getItemInHand().getItemMeta().getDisplayName().equals(ChatColor.GOLD + "Your Menu")) {
                        Inventory inv = Bukkit.getServer().createInventory(null, 27, ChatColor.BLACK + "Your Menu");

                        ItemStack block = new ItemStack(Material.MOSSY_COBBLESTONE);
                        ItemMeta blockMeta = block.getItemMeta();
                        blockMeta.setDisplayName(ChatColor.GOLD + "Your Stats");
                        List<String> lore = new ArrayList<>();
                        MDBMethods mdbMethods = new MDBMethods();

                        String levelStringToSplit = String.valueOf(mdbMethods.getStatDouble(player, Stats.LEVELS));
                        String decimalOfLevel = levelStringToSplit.substring(levelStringToSplit.indexOf(".")).replace("0", "").replace(".", "");
                        int percentToNextLevel = 0;
                        if (!decimalOfLevel.equals("")) {
                            percentToNextLevel = Integer.parseInt(decimalOfLevel) * 10;
                        }
                        String levelString = new LevelMethods().getFormattedLevel(player) + ChatColor.GOLD + " (" + percentToNextLevel + "% to next level" + ")";

                        lore.add(ChatColor.GOLD + "Level: " + levelString);
                        lore.add(ChatColor.GRAY + "Final Kills: " + ChatColor.GREEN + mdbMethods.getStatInt(player, Stats.FINAL_KILLS));
                        lore.add(ChatColor.GRAY + "Final Deaths: " + ChatColor.RED + mdbMethods.getStatInt(player, Stats.FINAL_DEATHS));
                        lore.add(ChatColor.GRAY + "Kills: " + ChatColor.GREEN + mdbMethods.getStatInt(player, Stats.KILLS));
                        lore.add(ChatColor.GRAY + "Deaths: " + ChatColor.RED + mdbMethods.getStatInt(player, Stats.DEATHS));
                        lore.add(ChatColor.GRAY + "Wins: " + ChatColor.GREEN + mdbMethods.getStatInt(player, Stats.WINS));
                        lore.add(ChatColor.GRAY + "Losses: " + ChatColor.RED + mdbMethods.getStatInt(player, Stats.LOSSES));
                        lore.add(ChatColor.GRAY + "Spawns Placed: " + ChatColor.GREEN + mdbMethods.getStatInt(player, Stats.SPAWN_PLACES));
                        lore.add(ChatColor.GRAY + "Spawns Broken: " + ChatColor.GREEN + mdbMethods.getStatInt(player, Stats.SPAWN_BREAKS));
                        lore.add(ChatColor.GRAY + "Spawns Lost: " + ChatColor.RED + mdbMethods.getStatInt(player, Stats.SPAWN_LOSSES));
                        blockMeta.setLore(lore);
                        block.setItemMeta(blockMeta);

                        inv.setItem(13, block);
                        player.openInventory(inv);
                    }
                }
            }
        }
    }
}
