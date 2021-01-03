package me.mine215.takhtjang.methods;

import com.mongodb.*;
import me.mine215.takhtjang.data.PlayerStats;
import me.mine215.takhtjang.types.Stats;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.net.UnknownHostException;

public class MDBMethods {
    public int getStatInt(Player player, Stats statType) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");
                if (col != null) {
                    DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                    DBCursor cursor = col.find(query);
                    DBObject playerStatsFromMDB = cursor.one();
                    if (playerStatsFromMDB != null) {
                        if (playerStatsFromMDB.get(statType.toString()) != null) {
                            return (int) playerStatsFromMDB.get(statType.toString());
                        }
                    }
                }
            }
        }
        return 0;
    }

    public boolean updateStatsInt(Player player, Stats statType, int statToAdd) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                PlayerStats playerStats = new PlayerStats(player.getUniqueId(), player.getName());
                DBObject data = null;

                int statBase;
                if (playerStatsFromMDB != null) {
                    if (playerStatsFromMDB.get(statType.toString()) == null) {
                        statBase = 0;
                    } else {
                        statBase = (int) playerStatsFromMDB.get(statType.toString());
                    }

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", playerStats.uuid);

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put(statType.toString(), statBase + statToAdd);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    statBase = 0;

                    data = new BasicDBObject("_id", playerStats.uuid)
                            .append(statType.toString(), statBase + statToAdd);
                    col.insert(data);
                }
                return true;
            }
        }
        return false;
    }

    public boolean updateStatsDouble(Player player, Stats statType, double statToAdd) {
        MongoClient mongoClient = null;
        try {
            mongoClient = new MongoClient(new MongoClientURI("mongodb://localhost:27017"));
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        if (mongoClient != null) {
            DB statsDB = mongoClient.getDB("StatsDatabase");
            if (statsDB != null) {
                DBCollection col = statsDB.getCollection("PlayerStats");

                DBObject query = new BasicDBObject("_id", player.getUniqueId().toString());
                DBCursor cursor = col.find(query);
                DBObject playerStatsFromMDB = cursor.one();

                PlayerStats playerStats = new PlayerStats(player.getUniqueId(), player.getName());
                DBObject data = null;

                double statBase;
                if (playerStatsFromMDB != null) {
                    if (playerStatsFromMDB.get(statType.toString()) == null) {
                        statBase = 0;
                    } else {
                        statBase = (double) playerStatsFromMDB.get(statType.toString());
                    }

                    BasicDBObject queryData = new BasicDBObject();
                    queryData.put("_id", playerStats.uuid);

                    BasicDBObject newDocument = new BasicDBObject();
                    newDocument.put(statType.toString(), statBase + statToAdd);

                    BasicDBObject updateObject = new BasicDBObject();
                    updateObject.put("$set", newDocument);

                    col.update(queryData, updateObject);
                } else {
                    statBase = 0;

                    data = new BasicDBObject("_id", playerStats.uuid)
                            .append(statType.toString(), statBase + statToAdd);
                    col.insert(data);
                }
                return true;
            }
        }
        return false;
    }
}
