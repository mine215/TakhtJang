package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.methods.PlayerMethods;
import me.rayzr522.jsonmessage.JSONMessage;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerMoveEvent implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public static List<Player> playersOnPortals = new ArrayList<>();

    public PlayerMoveEvent(FileConfiguration config, TakhtJang main) {
        PlayerMoveEvent.main = main;
        this.config = config;
    }

    @EventHandler
    public void voidDeath(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();

        if (player.getLocation().getBlockY() <= 0 && !player.getGameMode().equals(GameMode.SPECTATOR) && !player.getGameMode().equals(GameMode.CREATIVE)) {
            new GameData().getPlayer(player).kill();
        }
    }


    @EventHandler
    public void teleportPads(org.bukkit.event.player.PlayerMoveEvent event) {

        Player player = event.getPlayer();
        Location locationUnder = player.getLocation();
        locationUnder.setY(player.getLocation().getY() - 1);
        Block blockUnderPlayer = player.getWorld().getBlockAt(locationUnder);

        if (blockUnderPlayer.getType() == Material.ENDER_PORTAL_FRAME) {
        // Active teleport pads
        if (!playersOnPortals.contains(player)) {
            List<Location> padLocations = new ArrayList<>();
            padLocations.add(new Location(player.getWorld(), -10.5, 6, -9.5));
            padLocations.add(new Location(player.getWorld(), -25.5, 6, 16.5));
            padLocations.add(new Location(player.getWorld(), 14.5, 13, 15.5));
            padLocations.add(new Location(player.getWorld(), 9.5, 6, 13.5));
            padLocations.add(new Location(player.getWorld(), 26.5, 1, 0.5));
            padLocations.add(new Location(player.getWorld(), 0.5, 13, 0.5));

            Random rand = new Random();
            int padToTeleportToIndex = rand.nextInt(padLocations.size());

            Location padToTeleportToLocation = padLocations.get(padToTeleportToIndex);
            player.teleport(padToTeleportToLocation);
            playersOnPortals.add(player);
        }
        } else {
            playersOnPortals.remove(player);
        }
    }
}