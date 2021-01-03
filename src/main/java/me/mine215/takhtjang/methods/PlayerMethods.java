package me.mine215.takhtjang.methods;

import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.items.ItemHead;
import me.mine215.takhtjang.types.Team;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class PlayerMethods {
    public void hub(Player player, FileConfiguration config) {
        player.setGameMode(GameMode.SURVIVAL);
        kit(player, null);
        ItemStack head = new ItemHead().createHead(1, player.getName());
        ItemMeta headMeta = head.getItemMeta();
        headMeta.setDisplayName(ChatColor.GOLD + "Your Menu");
        head.setItemMeta(headMeta);
        player.getInventory().setItem(0, head);
        double x = config.getDouble("worldData.hub.x");
        double y = config.getDouble("worldData.hub.y");
        double z = config.getDouble("worldData.hub.z");
        player.teleport(new Location(player.getWorld(), x, y, z));
        //player.teleport(new Location(player.getWorld(), 175.5, 5.0, 175.5));
        GameData gameData = new GameData();
        try {
            gameData.getTeam(gameData.getPlayer(player).team).removePlayer(player);
        } catch (NullPointerException e) {
            gameData.addPlayer(player, null);
        }
    }

    public double distanceFromCoord(Location base, Location target) {
        double deltaX = Math.abs(base.getX() - target.getX());
        double deltaZ = Math.abs(base.getZ() - target.getZ());
        return Math.sqrt(deltaX * deltaX + deltaZ * deltaZ);
    }

    public void shop(Player player) {
        Inventory inv = Bukkit.getServer().createInventory(null, 27, ChatColor.BLACK + "Shop");

        ItemStack woodPickaxe = new ItemStack(Material.WOOD_PICKAXE);
        ItemMeta woodMeta = woodPickaxe.getItemMeta();
        woodMeta.setDisplayName("Wood Pickaxe (5 Iron)");
        woodPickaxe.setItemMeta(woodMeta);

        ItemStack ironPickaxe = new ItemStack(Material.IRON_PICKAXE);
        ItemMeta ironMeta = ironPickaxe.getItemMeta();
        ironMeta.setDisplayName("Iron Pickaxe (20 Iron)");
        ironPickaxe.setItemMeta(ironMeta);

        ItemStack goldPickaxe = new ItemStack(Material.GOLD_PICKAXE);
        ItemMeta goldMeta = ironPickaxe.getItemMeta();
        goldMeta.setDisplayName("Gold Pickaxe (10 Gold)");
        goldPickaxe.setItemMeta(goldMeta);

        ItemStack woolBlock = new ItemStack(Material.WOOL, 16);
        ItemMeta woolMeta = woolBlock.getItemMeta();
        woolMeta.setDisplayName("Wool (4 Iron)");
        woolBlock.setItemMeta(woolMeta);

        ItemStack endstoneBlock = new ItemStack(Material.ENDER_STONE, 8);
        ItemMeta endstoneBlockMeta = endstoneBlock.getItemMeta();
        endstoneBlockMeta.setDisplayName("Endstone (5 Gold)");
        endstoneBlock.setItemMeta(endstoneBlockMeta);

        ItemStack woodSword = new ItemStack(Material.WOOD_SWORD);
        ItemMeta woodSwordMeta = woodSword.getItemMeta();
        woodSwordMeta.setDisplayName("Wood Sword (10 Iron)");
        woodSword.setItemMeta(woodSwordMeta);

        ItemStack stoneSword = new ItemStack(Material.STONE_SWORD);
        ItemMeta stoneSwordMeta = stoneSword.getItemMeta();
        stoneSwordMeta.setDisplayName("Stone Sword (10 Gold)");
        stoneSword.setItemMeta(stoneSwordMeta);

        ItemStack gapple = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta gappleMeta = gapple.getItemMeta();
        gappleMeta.setDisplayName("Golden Apple (3 Gold)");
        gapple.setItemMeta(gappleMeta);

        ItemStack bow = new ItemStack(Material.BOW);
        ItemMeta bowMeta = bow.getItemMeta();
        bowMeta.setDisplayName("Bow (15 Gold)");
        bow.setItemMeta(bowMeta);

        ItemStack arrows = new ItemStack(Material.ARROW, 8);
        ItemMeta arrowMeta = arrows.getItemMeta();
        arrowMeta.setDisplayName("Arrows (2 Gold)");
        arrows.setItemMeta(arrowMeta);

        ItemStack chestplate = new ItemStack(Material.IRON_CHESTPLATE);
        ItemMeta chestplateMeta = chestplate.getItemMeta();
        chestplateMeta.setDisplayName("Iron Armor (15 Gold)");
        chestplate.setItemMeta(chestplateMeta);

        inv.setItem(0, chestplate);
        inv.setItem(3, woodPickaxe);
        inv.setItem(4, ironPickaxe);
        inv.setItem(5, goldPickaxe);
        inv.setItem(12, woolBlock);
        inv.setItem(13, endstoneBlock);
        inv.setItem(21, woodSword);
        inv.setItem(22, stoneSword);
        inv.setItem(23, gapple);
        inv.setItem(8, bow);
        inv.setItem(17, arrows);
        player.openInventory(inv);
    }

    public boolean checkInventory(Player player, String item, int quantity) {
        int ironQuantity = 0;
        int goldQuantity = 0;
        for (ItemStack itemStack : player.getInventory().getContents()) {
            if (itemStack != null) {
                if (itemStack.getType() == Material.GOLD_INGOT)
                    goldQuantity += itemStack.getAmount();
                if (itemStack.getType() == Material.IRON_INGOT)
                    ironQuantity += itemStack.getAmount();
            }
        }
        switch (item.toLowerCase()) {
            case "iron":
                if (ironQuantity >= quantity)
                    return true;
                break;
            case "gold":
                if (goldQuantity >= quantity)
                    return true;
                break;
        }
        return false;
    }

    public void kit(Player player, Team team) {

        ItemStack[] armor;
        ItemStack pickaxe;
        LeatherArmorMeta armorMeta;

        player.getInventory().clear();
        armor = new ItemStack[4];
        player.getInventory().setArmorContents(armor);
        if (team != null) {
            switch (team.toString()) {
                case "BLUE":
                    armor = new ItemStack[4];
                    armor[0] = new ItemStack(Material.LEATHER_BOOTS);
                    armorMeta = (LeatherArmorMeta) armor[0].getItemMeta();
                    armorMeta.setColor(Color.fromRGB(0, 0, 255));
                    armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
                    armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
                    armor[3] = new ItemStack(Material.LEATHER_HELMET);
                    armor[0].addEnchantment(Enchantment.PROTECTION_FALL, 1);
                    armor[3].addEnchantment(Enchantment.WATER_WORKER, 1);

                    armor[0].setItemMeta(armorMeta);
                    armor[1].setItemMeta(armorMeta);
                    armor[2].setItemMeta(armorMeta);
                    armor[3].setItemMeta(armorMeta);

                    pickaxe = new ItemStack(Material.WOOD_PICKAXE);
                    player.getInventory().setItem(0, pickaxe);
                    player.getInventory().setArmorContents(armor);
                    //player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 11));
                    break;
                case "RED":
                    armor = new ItemStack[4];
                    armor[0] = new ItemStack(Material.LEATHER_BOOTS);
                    armorMeta = (LeatherArmorMeta) armor[0].getItemMeta();
                    armorMeta.setColor(Color.fromRGB(255, 0, 0));
                    armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
                    armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
                    armor[3] = new ItemStack(Material.LEATHER_HELMET);
                    armor[0].addEnchantment(Enchantment.PROTECTION_FALL, 1);
                    armor[3].addEnchantment(Enchantment.WATER_WORKER, 1);

                    armor[0].setItemMeta(armorMeta);
                    armor[1].setItemMeta(armorMeta);
                    armor[2].setItemMeta(armorMeta);
                    armor[3].setItemMeta(armorMeta);

                    pickaxe = new ItemStack(Material.WOOD_PICKAXE);
                    player.getInventory().setItem(0, pickaxe);
                    player.getInventory().setArmorContents(armor);
                    //player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 14));
                    break;
                case "GREEN":
                    armor = new ItemStack[4];
                    armor[0] = new ItemStack(Material.LEATHER_BOOTS);
                    armorMeta = (LeatherArmorMeta) armor[0].getItemMeta();
                    armorMeta.setColor(Color.fromRGB(0, 255, 0));
                    armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
                    armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
                    armor[3] = new ItemStack(Material.LEATHER_HELMET);
                    armor[0].addEnchantment(Enchantment.PROTECTION_FALL, 1);
                    armor[3].addEnchantment(Enchantment.WATER_WORKER, 1);

                    armor[0].setItemMeta(armorMeta);
                    armor[1].setItemMeta(armorMeta);
                    armor[2].setItemMeta(armorMeta);
                    armor[3].setItemMeta(armorMeta);

                    pickaxe = new ItemStack(Material.WOOD_PICKAXE);
                    player.getInventory().setItem(0, pickaxe);
                    player.getInventory().setArmorContents(armor);
                    //player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 5));
                    break;
                case "YELLOW":
                    armor = new ItemStack[4];
                    armor[0] = new ItemStack(Material.LEATHER_BOOTS);
                    armorMeta = (LeatherArmorMeta) armor[0].getItemMeta();
                    armorMeta.setColor(Color.fromRGB(255, 255, 0));
                    armor[1] = new ItemStack(Material.LEATHER_LEGGINGS);
                    armor[2] = new ItemStack(Material.LEATHER_CHESTPLATE);
                    armor[3] = new ItemStack(Material.LEATHER_HELMET);
                    armor[0].addEnchantment(Enchantment.PROTECTION_FALL, 1);
                    armor[3].addEnchantment(Enchantment.WATER_WORKER, 1);

                    armor[0].setItemMeta(armorMeta);
                    armor[1].setItemMeta(armorMeta);
                    armor[2].setItemMeta(armorMeta);
                    armor[3].setItemMeta(armorMeta);

                    pickaxe = new ItemStack(Material.WOOD_PICKAXE);
                    player.getInventory().setItem(0, pickaxe);
                    player.getInventory().setArmorContents(armor);
                    //player.getInventory().addItem(new ItemStack(Material.WOOL, 16, (byte) 4));
                    break;
                default:
                    player.getInventory().clear();
                    armor = new ItemStack[4];
                    player.getInventory().setArmorContents(armor);
                    break;
            }
        } else {
            player.getInventory().clear();
            armor = new ItemStack[4];
            player.getInventory().setArmorContents(armor);
        }
    }

    public void ironKit(Player player) {

        ItemStack[] armor;

        armor = new ItemStack[4];
        armor[0] = new ItemStack(Material.IRON_BOOTS);
        armor[1] = new ItemStack(Material.IRON_LEGGINGS);
        armor[2] = player.getInventory().getChestplate();
        armor[3] = player.getInventory().getHelmet();
        armor[0].addEnchantment(Enchantment.PROTECTION_FALL, 1);
        armor[3].addEnchantment(Enchantment.WATER_WORKER, 1);

        player.getInventory().setArmorContents(armor);
    }
}
