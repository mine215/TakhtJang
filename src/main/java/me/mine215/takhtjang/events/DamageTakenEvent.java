package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.methods.PlayerMethods;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;

public class DamageTakenEvent implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public DamageTakenEvent(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player player = (Player) event.getEntity();
            if (player.getHealth() - event.getDamage() < 1) {
                event.setCancelled(true);
                if (new GameData().getPlayer(player) != null) {
                    new GameData().getPlayer(player).kill();
                } else {
                    new PlayerMethods().hub(player, config);
                }
            }
        }
    }

    @EventHandler
    public void antiDeath(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player player = (Player) event.getEntity();
                Player damager = (Player) event.getDamager();
                if (new GameData().getPlayer(player) != null) {
                    new GameData().getPlayer(player).hitPlayer(damager);
                    if (player.getHealth() - event.getDamage() < 1) {
                        event.setCancelled(true);
                        new GameData().getPlayer(player).kill();
                    }
                }
            }
        }
    }

    @EventHandler
    public void antiPVPInHub(EntityDamageByEntityEvent event) {
        if (event.getEntity() instanceof Player) {
            if (event.getDamager() instanceof Player) {
                Player damager = (Player) event.getDamager();
                if (new GameData().getPlayer(damager) == null) {
                    event.setCancelled(true);
                }
            }
        }
    }
}