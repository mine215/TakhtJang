package me.mine215.takhtjang.data;

import java.util.UUID;

public class PlayerStats {
    public final String uuid;
    public final String name;
    public int finalKills;
    public int finalDeaths;
    public int wins;
    public int losses;
    public int spawnsBroken;
    public int spawnsLost;
    public int spawnsPlaced;
    public int levels;

    public PlayerStats(UUID uuidIn, String nameIn) {
        uuid = uuidIn.toString();
        name = nameIn;
    }
}
