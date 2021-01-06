package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.mine215.takhtjang.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InventoryClick implements Listener {

    static TakhtJang main;
    final FileConfiguration config;

    public InventoryClick(FileConfiguration config, TakhtJang main) {
        InventoryClick.main = main;
        this.config = config;
    }

    @EventHandler()
    public void shopClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        Player player = Bukkit.getPlayer(event.getWhoClicked().getName());
        int slotClicked = event.getSlot();
        Inventory invClicked = event.getInventory();
        ItemStack itemClicked = invClicked.getItem(slotClicked);
        if (itemClicked != null && invClicked.getName().equals(ChatColor.BLACK + "Shop")) {
            event.setCancelled(true);
            String itemName = itemClicked.getItemMeta().getDisplayName();
            switch (itemName) {
                case "Wood Pickaxe (5 Iron)":
                    if (new PlayerMethods().checkInventory(player, "iron", 5)) {
                        player.getInventory().addItem(new ItemStack(Material.WOOD_PICKAXE));
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 5));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough iron for that.");
                    }
                    break;
                case "Iron Pickaxe (20 Iron)":
                    if (new PlayerMethods().checkInventory(player, "iron", 20)) {
                        player.getInventory().addItem(new ItemStack(Material.IRON_PICKAXE));
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 20));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough iron for that.");
                    }
                    break;
                case "Gold Pickaxe (10 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 10)) {
                        player.getInventory().addItem(new ItemStack(Material.GOLD_PICKAXE));
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 10));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
                case "Wool (4 Iron)":
                    if (new PlayerMethods().checkInventory(player,"iron", 4)) {
                        Team team = new GameData().getPlayer(player).team;
                        switch (team.toString()) {
                            case "BLUE":
                                player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 11));
                                break;
                            case "RED":
                                player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 14));
                                break;
                            case "GREEN":
                                player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 5));
                                break;
                            case "YELLOW":
                                player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 4));
                                break;
                        }
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 4));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough iron for that.");
                    }
                    break;
                case "Endstone (5 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 5)) {
                        player.getInventory().addItem(new ItemStack(Material.ENDER_STONE, 8));
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 5));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
                case "Wood Sword (10 Iron)":
                    if (new PlayerMethods().checkInventory(player, "iron", 10)) {
                        player.getInventory().addItem(new ItemStack(Material.WOOD_SWORD));
                        player.getInventory().removeItem(new ItemStack(Material.IRON_INGOT, 10));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough iron for that.");
                    }
                    break;
                case "Stone Sword (10 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 10)) {
                        player.getInventory().addItem(new ItemStack(Material.STONE_SWORD));
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 10));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
                case "Golden Apple (3 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 3)) {
                        player.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 3));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
                case "Bow (15 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 15)) {
                        player.getInventory().addItem(new ItemStack(Material.BOW));
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 15));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
                case "Arrows (2 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 2)) {
                        player.getInventory().addItem(new ItemStack(Material.ARROW, 8));
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 2));
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
                case "Iron Armor (15 Gold)":
                    if (new PlayerMethods().checkInventory(player, "gold", 15)) {
                        player.getInventory().removeItem(new ItemStack(Material.GOLD_INGOT, 15));
                        new PlayerMethods().ironKit(player);
                        new GameData().getPlayer(player).ironKit = true;
                    } else {
                        player.sendMessage(ChatColor.RED + "You don't have enough gold for that.");
                    }
                    break;
            }
        }
    }

    @EventHandler()
    public void statsClick(org.bukkit.event.inventory.InventoryClickEvent event) {
        Player player = Bukkit.getPlayer(event.getWhoClicked().getName());
        int slotClicked = event.getSlot();
        Inventory invClicked = event.getInventory();
        ItemStack itemClicked = invClicked.getItem(slotClicked);
        if (itemClicked != null && invClicked.getName().equals(ChatColor.BLACK + "Your Menu"))
            event.setCancelled(true);
    }
}
