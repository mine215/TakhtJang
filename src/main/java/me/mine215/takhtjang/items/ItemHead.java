package me.mine215.takhtjang.items;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;


public class ItemHead {
    public static ItemStack createHead(Integer quantity, String playerName) {
        ItemStack head = new ItemStack(Material.SKULL_ITEM, quantity, (short) 3);
        SkullMeta headMeta = (SkullMeta) head.getItemMeta();
        headMeta.setDisplayName(playerName);
        headMeta.setOwner(playerName);
        head.setItemMeta(headMeta);
        return head;
    }
}
