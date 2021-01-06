package me.mine215.takhtjang;

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

        getCommand("kit").setExecutor(new Kit());
        getCommand("hub").setExecutor(new Hub(config, this));
        getCommand("shop").setExecutor(new Shop());
        getCommand("join").setExecutor(new Join());
        getCommand("reset").setExecutor(new Reset());
        getCommand("togglegen").setExecutor(new ToggleGenerator());
        getCommand("play").setExecutor(new Play());
        getCommand("party").setExecutor(new PartyCommand(config, this));
        getCommand("p").setExecutor(new PartyCommand(config, this));
        getCommand("rank").setExecutor(new Rank(config, this));
        getCommand("permission").setExecutor(new Permission(config, this));

        getServer().getPluginManager().registerEvents(new PlayerMove(config, this), this);
        getServer().getPluginManager().registerEvents(new InventoryClick(config, this), this);
        getServer().getPluginManager().registerEvents(new WeatherModify(config, this), this);
        getServer().getPluginManager().registerEvents(new DamageTaken(config, this), this);
        getServer().getPluginManager().registerEvents(new PlaceBlock(config, this), this);
        getServer().getPluginManager().registerEvents(new BreakBlock(config, this), this);
        getServer().getPluginManager().registerEvents(new PlayerSleep(config, this), this);
        getServer().getPluginManager().registerEvents(new PlayerInteract(config, this), this);
        getServer().getPluginManager().registerEvents(new PlayerChat(config, this), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(config, this), this);
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[TakhtJang]: Plugin is enabled!");

        double genMult = config.getDouble("genMultiplier");

        schedule(() -> {
            if (shouldSpawnRes) {
                List<Team> teamList = new ArrayList<>();
                teamList.add(Team.RED);
                teamList.add(Team.BLUE);
                teamList.add(Team.GREEN);
                teamList.add(Team.YELLOW);
                for (Team team : teamList) {
                    double x = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".x");
                    double y = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".y");
                    double z = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".z");
                    String worldName = config.getString("worldData.worldName");
                    Bukkit.getServer().getWorld(worldName).dropItem(new Location(Bukkit.getServer().getWorld(worldName), x, y, z), new ItemStack(Material.IRON_INGOT));
                }
            }
            }, (int) (80 / genMult));
        schedule(() -> {
            if (shouldSpawnRes) {
                List<Team> teamList = new ArrayList<>();
                teamList.add(Team.RED);
                teamList.add(Team.BLUE);
                teamList.add(Team.GREEN);
                teamList.add(Team.YELLOW);
                for (Team team : teamList) {
                    double x = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".x");
                    double y = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".y");
                    double z = TakhtJang.getInstance().getConfig().getDouble("worldData.bases." + team.toString().toLowerCase() + ".z");
                    String worldName = config.getString("worldData.worldName");
                    Bukkit.getServer().getWorld(worldName).dropItem(new Location(Bukkit.getServer().getWorld(worldName), x, y, z), new ItemStack(Material.GOLD_INGOT));
                }
            }
            }, (int) (200 / genMult));
    }

    @Override
    public void onDisable() {
        getServer().getConsoleSender().sendMessage(ChatColor.GREEN + "[TakhtJang]: Plugin is disabled!");
    }

    public static void scheduleSyncDelayedTask(Runnable runnable, int delay){
        Bukkit.getScheduler().scheduleSyncDelayedTask(instance, runnable, delay);
    }

    public void schedule(Runnable runnable, int ticks) {
        int taskID = Bukkit.getScheduler().scheduleSyncRepeatingTask(instance, runnable, 0L, ticks);
    }

    public static TakhtJang getInstance() {
        return instance;
    }
}