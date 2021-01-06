package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import me.mine215.takhtjang.data.GameData;
import me.mine215.takhtjang.methods.PlayerMethods;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PlayerMove implements Listener {

    static TakhtJang main;
    final FileConfiguration config;

    public static final List<Player> playersOnPortals = new ArrayList<>();

    public PlayerMove(FileConfiguration config, TakhtJang main) {
        me.mine215.takhtjang.events.PlayerMove.main = main;
        this.config = config;
    }

    @EventHandler
    public void voidDeath(org.bukkit.event.player.PlayerMoveEvent event) {
        Player player = event.getPlayer();

        int voidHeight = TakhtJang.getInstance().getConfig().getInt("void");
        if (player.getLocation().getBlockY() <= voidHeight && !player.getGameMode().equals(GameMode.SPECTATOR) && !player.getGameMode().equals(GameMode.CREATIVE)) {
            if(new GameData().getPlayer(player) != null) {
                new GameData().getPlayer(player).kill();
            } else {
                new PlayerMethods().hub(player, config);
            }
        }
    }


    @EventHandler
    public void teleportPads(org.bukkit.event.player.PlayerMoveEvent event) {

        Player player = event.getPlayer();
        Location locationUnder = player.getLocation();
        locationUnder.setY(player.getLocation().getY() - 1);
        Block blockUnderPlayer = player.getWorld().getBlockAt(locationUnder);

        if (blockUnderPlayer.getType() == Material.ENDER_PORTAL_FRAME) {
            if (TakhtJang.getInstance().getConfig().getBoolean("doPortals")) {
                // Active teleport pads
                if (!playersOnPortals.contains(player)) {
                    List<Location> padLocations = new ArrayList<>();

                    double padAX = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padA.x");
                    double padAY = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padA.y");
                    double padAZ = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padA.z");

                    double padBX = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padB.x");
                    double padBY = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padB.y");
                    double padBZ = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padB.z");

                    double padCX = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padC.x");
                    double padCY = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padC.y");
                    double padCZ = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padC.z");

                    double padDX = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padD.x");
                    double padDY = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padD.y");
                    double padDZ = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padD.z");

                    double padEX = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padE.x");
                    double padEY = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padE.Y");
                    double padEZ = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padE.Z");

                    double padFX = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padF.x");
                    double padFY = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padF.y");
                    double padFZ = TakhtJang.getInstance().getConfig().getDouble("worldData.pads.padF.z");

                    padLocations.add(new Location(player.getWorld(), padAX, padAY, padAZ));
                    padLocations.add(new Location(player.getWorld(), padBX, padBY, padBZ));
                    padLocations.add(new Location(player.getWorld(), padCX, padCY, padCZ));
                    padLocations.add(new Location(player.getWorld(), padDX, padDY, padDZ));
                    padLocations.add(new Location(player.getWorld(), padEX, padEY, padEZ));
                    padLocations.add(new Location(player.getWorld(), padFX, padFY, padFZ));

                    Random rand = new Random();
                    int padToTeleportToIndex = rand.nextInt(padLocations.size());

                    Location padToTeleportToLocation = padLocations.get(padToTeleportToIndex);
                    player.teleport(padToTeleportToLocation);
                    playersOnPortals.add(player);
                } else {
                    playersOnPortals.remove(player);
                }
            }
        }
    }
}