package me.mine215.takhtjang;

import jdk.jfr.internal.tool.Main;
import me.mine215.takhtjang.commands.*;
import me.mine215.takhtjang.events.*;
import me.mine215.takhtjang.types.Team;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public final class TakhtJang extends JavaPlugin {

    public static TakhtJang instance;

    public void onLoad(){
        instance = this;
    }

    public static boolean shouldSpawnRes = true;

    @Override
    public void onEnable() {

        FileConfiguration config = this.getConfig();

        if (!config.getBoolean("isSetup")) {
            this.saveDefaultConfig();
        }

        getCommand("kit").setExecutor(new KitCommand());
        getCommand("hub").setExecutor(new HubCommand(config, this));
        getCommand("shop").setExecutor(new ShopCommand());
        getCommand("join").setExecutor(new JoinCommand());
        getCommand("reset").setExecutor(new ResetCommand());
        getCommand("togglegen").setExecutor(new ToggleResCommand());
        getCommand("play").setExecutor(new PlayCommand());
        getCommand("party").setExecutor(new PartyCommand(config, this));
        getCommand("p").setExecutor(new PartyCommand(config, this));

        getServer().getPluginManager().registerEvents(new PlayerMoveEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new InventoryClickEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new PickupItemEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new WeatherModifyEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new DamageTakenEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new PlaceBlockEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new BreakBlockEvent(config, this), this);
        getServer().getPluginManager().registerEvents(new PlayerSleepEvent(config, this), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[TakhtJang]: Plugin is enabled!");

        double genMult = config.getDouble("genMultiplier");

        schedule(() -> {
            if (shouldSpawnRes) {
                Location spawnLoc;
                List<Team> teamList = new ArrayList();
                teamList.add(Team.RED);
                teamList.add(Team.BLUE);
                teamList.add(Team.GREEN);
                teamList.add(Team.YELLOW);
                for (Team team : teamList) {
                    double x = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".x");
                    double y = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".y");
                    double z = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".z");
                    String worldName = config.getString("worldData.worldName");
                    spawnLoc = new Location(Bukkit.getServer().getWorld(worldName), x, y, z);
                    Bukkit.getServer().getWorld(worldName).dropItem(new Location(Bukkit.getServer().getWorld(worldName), x, y, z), new ItemStack(Material.IRON_INGOT));
                }
            }
            }, (int) (80 / genMult));
        schedule(() -> {
            if (shouldSpawnRes) {
                Location spawnLoc;
                List<Team> teamList = new ArrayList();
                teamList.add(Team.RED);
                teamList.add(Team.BLUE);
                teamList.add(Team.GREEN);
                teamList.add(Team.YELLOW);
                for (Team team : teamList) {
                    double x = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".x");
                    double y = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".y");
                    double z = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".z");
                    String worldName = config.getString("worldData.worldName");
                    spawnLoc = new Location(Bukkit.getServer().getWorld(worldName), x, y, z);
                    Bukkit.getServer().getWorld(worldName).dropItem(new Location(Bukkit.getServer().getWorld(worldName), x, y, z), new ItemStack(Material.GOLD_INGOT));
                }
            }
            }, (int) (200 / genMult));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[TakhtJang]: Plugin is disabled!");
    }

    public static int scheduleSyncDelayedTask(Runnable runnable, int delay){
        return Bukkit.getScheduler().scheduleSyncDelayedTask(instance, runnable, delay);
    }

    public void schedule(Runnable runnable, int ticks) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, runnable, 0L, ticks);
    }

    public static TakhtJang getInstance() {
        return instance;
    }
}