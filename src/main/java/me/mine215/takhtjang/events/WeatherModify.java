package me.mine215.takhtjang.events;

import me.mine215.takhtjang.TakhtJang;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.List;

public class WeatherModify implements Listener {

    static TakhtJang main;
    FileConfiguration config;

    public WeatherModify(FileConfiguration config, TakhtJang main) {
        this.main = main;
        this.config = config;
    }

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        event.setCancelled(event.toWeatherState());
    }
}
